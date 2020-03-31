package net.colors_wind.submitmanager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import lombok.NonNull;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;

public class OutputOptions extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8108291943205318602L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel;
	protected JTextField outputFile;
	protected JRadioButton radiobuttonKeepBig;
	protected JRadioButton radiobuttonKeepSmall;
	protected JRadioButton radiobuttonCombine;
	protected JRadioButton radiobuttonNotModify;
	protected ButtonGroup conflictStrategy;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JCheckBox checkboxCombine;
	private JCheckBox checkboxTryCombine;
	protected JCheckBox checkboxAddRawData;
	private JCheckBox moveInsteadCopy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			OutputOptions dialog = new OutputOptions();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public OutputOptions() {
		setTitle("输出选项");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setType(Type.POPUP);
		setBounds(100, 100, 410, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		{
			lblNewLabel = new JLabel("输出文件");
			lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 12));
		}
		
		outputFile = new JTextField();
		outputFile.setText("{7}-{8}.pdf");
		outputFile.setColumns(10);
		
		JButton btnNewButton = new JButton(".");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(8)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(outputFile, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 27, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(outputFile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton)
						.addComponent(lblNewLabel)))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("确定");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						OutputOptions.this.setVisible(false);
					}
				});
				okButton.setActionCommand("");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						OutputOptions.this.setVisible(false);
					}
				});
				cancelButton.setActionCommand("取消");
				buttonPane.add(cancelButton);
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(new FormLayout(new ColumnSpec[] {
					ColumnSpec.decode("center:193px"),
					ColumnSpec.decode("center:199px"),},
				new RowSpec[] {
					FormSpecs.LINE_GAP_ROWSPEC,
					RowSpec.decode("25px"),
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,
					FormSpecs.RELATED_GAP_ROWSPEC,
					FormSpecs.DEFAULT_ROWSPEC,}));
			{
				lblNewLabel_2 = new JLabel("转换选项");
				panel.add(lblNewLabel_2, "1, 2");
			}
			{
				lblNewLabel_1 = new JLabel("文件名冲突处理");
				panel.add(lblNewLabel_1, "2, 2");
			}
			{
				checkboxCombine = new JCheckBox("尝试合并多个文件");
				checkboxCombine.setEnabled(false);
				checkboxCombine.setSelected(true);
				panel.add(checkboxCombine, "1, 4");
			}
			{
				radiobuttonKeepSmall = new JRadioButton("保留序号较小的文件");
				panel.add(radiobuttonKeepSmall, "2, 4");
			}
			{
				checkboxTryCombine = new JCheckBox("尝试转换图片文件");
				checkboxTryCombine.setEnabled(false);
				checkboxTryCombine.setSelected(true);
				panel.add(checkboxTryCombine, "1, 6");
			}
			{
				radiobuttonKeepBig = new JRadioButton("保留序号较大的文件");
				panel.add(radiobuttonKeepBig, "2, 6");
			}
			{
				checkboxAddRawData = new JCheckBox("添加文件原始信息");
				panel.add(checkboxAddRawData, "1, 8");
			}
			{
				radiobuttonCombine = new JRadioButton("按序号从小到大合并");
				radiobuttonCombine.setSelected(true);
				panel.add(radiobuttonCombine, "2, 8");
			}
			{
				moveInsteadCopy = new JCheckBox("移动文件代替拷贝");
				moveInsteadCopy.setEnabled(false);
				panel.add(moveInsteadCopy, "1, 10");
			}
			{
				radiobuttonNotModify = new JRadioButton("不处理保留原始文件");
				panel.add(radiobuttonNotModify, "2, 10");
			}
			conflictStrategy = new ButtonGroup();
			conflictStrategy.add(radiobuttonCombine);
			conflictStrategy.add(radiobuttonKeepBig);
			conflictStrategy.add(radiobuttonKeepSmall);
			conflictStrategy.add(radiobuttonNotModify);
		}
	}
	
	public ConflictStrategy getConflictStrategy() {
		if (radiobuttonCombine.isSelected()) {
			return ConflictStrategy.COMBINE_BY_ASCEND;
		} else if (radiobuttonKeepSmall.isSelected()) {
			return ConflictStrategy.KEEP_INDEX_SMALL;
		} else if (radiobuttonKeepBig.isSelected()) {
			return ConflictStrategy.KEEP_INDEX_BIG;
		} else if (radiobuttonNotModify.isSelected()) {
			return ConflictStrategy.DO_NOT_MODIFY;
		}
		//won't happen
		return ConflictStrategy.COMBINE_BY_ASCEND;
	}
	
	public void setConflictStrategy(@NonNull ConflictStrategy strategy) {
		switch(strategy) {
		case COMBINE_BY_ASCEND:
			this.radiobuttonCombine.setSelected(true);
			break;
		case DO_NOT_MODIFY:
			this.radiobuttonNotModify.setSelected(true);
			break;
		case KEEP_INDEX_BIG:
			this.radiobuttonKeepBig.setEnabled(true);
			break;
		case KEEP_INDEX_SMALL:
			this.radiobuttonKeepSmall.setEnabled(true);
			break;
		default:
			break;
		}
	}
}