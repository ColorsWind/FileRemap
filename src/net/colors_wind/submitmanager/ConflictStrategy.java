package net.colors_wind.submitmanager;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import javax.swing.JRadioButton;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public enum ConflictStrategy {
	KEEP_INDEX_SMALL((mainWindow, form) -> keepByTimeOrder(mainWindow, form, StudentInfo.ASCEND)),
	KEEP_INDEX_BIG((mainWindow, form) -> keepByTimeOrder(mainWindow, form, StudentInfo.DESCEND)),
	COMBINE_BY_ASCEND((mainWindow, form) -> combineByTimeOrder(mainWindow, form, StudentInfo.ASCEND)),
	ADD_PREFIX((mainWindow, form) -> addPrefix(form)),
	DO_NOT_MODIFY((mainWindow, form) -> doNotMove(mainWindow, form));

	private final BiFunction<MainWindow, FormMap, StudentInfo[]> func;
	@Getter
	@Setter(AccessLevel.PROTECTED)
	// 不需要 volatile, 因为只有 EDT 线程访问这个字段.
	private JRadioButton button;

	private ConflictStrategy(BiFunction<MainWindow, FormMap, StudentInfo[]> func) {
		this.func = func;
	}

	public static Optional<ConflictStrategy> getStrategy(String name) {
		try {
			return Optional.of(ConflictStrategy.valueOf(name));
		} catch (IllegalArgumentException e) {
		}
		return Optional.empty();
	}

	public static StudentInfo[] keepByTimeOrder(MainWindow mainWindow, FormMap form, Comparator<StudentInfo> comparator) {
		List<StudentInfo> studentsOrder = form.getStudentsOrder(comparator);
		LinkedHashMap<String, StudentInfo> studentsMap = new LinkedHashMap<>();
		for (StudentInfo studentInfo : studentsOrder) {
			StudentInfo override = studentsMap.put(studentInfo.getFileNameLowerCase(), studentInfo);
			if(override != null) {
				mainWindow.println(new StringBuilder("覆盖文件: ").append(override.getFileName()).append(" 序号: ").append(override.getIndex()).toString());
			}
		}
		return studentsMap.values().toArray(new StudentInfo[studentsMap.size()]);
	}

	public static StudentInfo[] combineByTimeOrder(MainWindow mainWindow, FormMap form, Comparator<StudentInfo> comparator) {
		List<StudentInfo> studentsOrder = form.getStudentsOrder(comparator);
		LinkedHashMap<String, StudentInfo> studentsMap = new LinkedHashMap<>();
		for (StudentInfo studentInfo : studentsOrder) {
			StudentInfo combine = studentsMap.putIfAbsent(studentInfo.getFileNameLowerCase(), studentInfo);
			if (combine != null) {
				combine.getFileMap().putAll(studentInfo.getFileMap());
				mainWindow.println(new StringBuilder("覆盖文件: ").append(combine.getFileName()).append(" 序号: ").append(combine.getIndex()).toString());
			}
		}
		return studentsMap.values().toArray(new StudentInfo[studentsMap.size()]);
	}

	public static StudentInfo[] addPrefix(FormMap form) {
		ConcurrentMap<String, List<StudentInfo>> collect = Arrays
				.stream(form.getStudents().toArray(new StudentInfo[form.getStudents().size()])).parallel()
				.collect(Collectors.groupingByConcurrent(StudentInfo::getFileNameLowerCase));
		return collect.values().parallelStream().flatMap(students -> {
			if (students.size() == 1) {
				return students.stream();
			} else {
				return students.stream().map(student -> {
					StudentInfo override = new StudentInfo(student.getIndex(),
							new StringBuilder(student.getFileName().substring(0, student.getFileName().length() - 4))
									.append("-").append(student.getIndex()).append(".pdf").toString());
					return override;
				});
			}
		}).toArray(StudentInfo[]::new);
	}
	
	public static StudentInfo[] doNotMove(MainWindow mainWindow, FormMap form) {
		ConcurrentMap<Integer, StudentInfo> origin = form.getData();
		ConcurrentMap<String, StudentInfo> map = new ConcurrentHashMap<>();
		Arrays.stream(origin.values().toArray(new StudentInfo[origin.size()])).parallel().forEach(studentInfo -> {
			StudentInfo override = map.put(studentInfo.getFileNameLowerCase(), studentInfo);
			if (override != null) {
				// 合并为一次输出
				StringBuilder sb = new StringBuilder();
				if(origin.remove(override.getIndex()) != null) {
					// 第一次重复
					sb.append("重复文件: ").append(override.getFileName()).append(" 序号: ").append(override.getIndex()).append("\n");
				}
				origin.remove(studentInfo.getIndex());
				sb.append("重复文件: ").append(studentInfo.getFileName()).append(" 序号: ").append(studentInfo.getIndex());
				mainWindow.println(sb.toString());
			}
		});
		return origin.values().toArray(new StudentInfo[origin.size()]);
	}
	
	public StudentInfo[] resoveConflict(MainWindow mainWindow, FormMap form) {
		return this.func.apply(mainWindow, form);
	}
	
	/**
	 * EDT Thread only
	 * @return
	 */
	public static ConflictStrategy getSelectConflictStrategy() {
		for(ConflictStrategy strategy : ConflictStrategy.values()) {
			if (strategy.isSelect()) {
				return strategy;
			}
		}
		return ADD_PREFIX;
		
	}

	/**
	 * EDT Thread only
	 * @return
	 */
	public boolean isSelect() {
		return button.isSelected();
	}
	
	/**
	 * EDT Thread only
	 * @param select
	 */
	public void setSelect(boolean select) {
		button.setSelected(select);
	}

}
