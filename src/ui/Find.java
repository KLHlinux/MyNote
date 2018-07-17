package ui;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Find extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	JRadioButton radioButton ;
	JRadioButton rdbtnNewRadioButton;
	JCheckBox chckbxNewCheckBox ;
	
	public Find(MyNotePad myNotePad) {
		setTitle("\u67E5\u627E");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 533, 201);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);//初始位置
		setResizable(false);//不能调整窗口大小
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u67E5\u627E\u5185\u5BB9:");
		lblNewLabel.setBounds(10, 32, 57, 26);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(77, 35, 289, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u9009\u9879", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 73, 166, 57);
		contentPane.add(panel);
		
		chckbxNewCheckBox = new JCheckBox("\u533A\u5206\u5927\u5C0F\u5199");
		chckbxNewCheckBox.setSelected(true);
		panel.add(chckbxNewCheckBox);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u65B9\u5411", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(195, 73, 171, 57);
		contentPane.add(panel_1);
		
		radioButton = new JRadioButton("\u5411\u4E0A");
		panel_1.add(radioButton);
		
		rdbtnNewRadioButton = new JRadioButton("\u5411\u4E0B");
		rdbtnNewRadioButton.setSelected(true);
		panel_1.add(rdbtnNewRadioButton);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnNewRadioButton);
		bg.add(radioButton);
		
		JButton btnNewButton = new JButton("\u67E5\u627E\u4E0B\u4E00\u4E2A");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				find(myNotePad); //查找事件
			}
		});
		btnNewButton.setBounds(391, 35, 103, 21);
		contentPane.add(btnNewButton);
		
		//取消按钮
		JButton button = new JButton("\u53D6\u6D88");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();  //退出
			}
		});
		button.setBounds(391, 86, 103, 21);
		contentPane.add(button);
		
		setVisible(true);
	}

	public void find(MyNotePad myNotePad) {
		String s = myNotePad.log.getText();	//文本内容
		String a = textField.getText();		//查找的内容
		int FindStartPos = myNotePad.log.getCaretPosition(); //开始的索引,光标的位置
		int n = 0;
		
		//不区分大小写
		if(!chckbxNewCheckBox.isSelected()) {
			s = s.toLowerCase(); //转为小写
			a = a.toLowerCase(); //转为小写
		}
		
		//System.out.println(FindStartPos);
		
		//根据索引找到查找的位置
		if (radioButton.isSelected()) {  
            if (myNotePad.log.getSelectedText() == null) {  
                n = s.lastIndexOf(a, FindStartPos);  
            } else {  
                n = s.lastIndexOf(a, FindStartPos - a.length()- 1);  
            }  
        } else if (rdbtnNewRadioButton.isSelected()) {  
            if (myNotePad.log.getSelectedText() == null) {  
                n = s.indexOf(a, FindStartPos);  
            } else {  
                n = s.indexOf(a, FindStartPos);  
            }  
        }
		
		//System.out.println(n);
		
		//将查找的内容选中
		if(n>-1) {
			if (radioButton.isSelected()) {  
				myNotePad.log.setCaretPosition(n);  
                myNotePad.log.select(n, n + a.length());   
            } else if (rdbtnNewRadioButton.isSelected()) {  
            	myNotePad.log.setCaretPosition(n);  
                myNotePad.log.select(n, n + a.length());  
            }  
		}
		else {
			JOptionPane.showMessageDialog(null, "找不到您查找的内容!", "记事本", JOptionPane.INFORMATION_MESSAGE); 
		}
	}

	
}









