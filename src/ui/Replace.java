package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Replace extends JDialog {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	JCheckBox chckbxNewCheckBox ;
	JCheckBox chckbxNewCheckBox_1;


	public Replace(MyNotePad myNotePad) {
		setTitle("\u66FF\u6362");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 568, 225);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);//初始位置
		setResizable(false);//不能调整窗口大小
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u67E5\u627E\u5185\u5BB9:");
		lblNewLabel.setBounds(24, 10, 57, 26);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(91, 13, 289, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("\u66FF\u6362\u4E3A:");
		label.setBounds(24, 46, 57, 26);
		contentPane.add(label);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(91, 49, 289, 21);
		contentPane.add(textField_1);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u9009\u9879", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(24, 96, 364, 76);
		contentPane.add(panel);
		
		chckbxNewCheckBox = new JCheckBox("\u533A\u5206\u5927\u5C0F\u5199");
		panel.add(chckbxNewCheckBox);
		
		chckbxNewCheckBox_1 = new JCheckBox("\u6B63\u5219\u8868\u8FBE\u5F0F");
		panel.add(chckbxNewCheckBox_1);
		
		JButton btnNewButton = new JButton("\u67E5\u627E\u4E0B\u4E00\u4E2A");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				find(myNotePad);
			}
		});
		btnNewButton.setBounds(403, 12, 104, 23);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("\u66FF\u6362");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replace(myNotePad);  //替换
			}
		});
		button.setBounds(403, 48, 104, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u5168\u90E8\u66FF\u6362");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replaceAll(myNotePad);   //替换全部
			}
		});
		button_1.setBounds(403, 81, 104, 23);
		contentPane.add(button_1);
		
		//取消按钮
		JButton button_2 = new JButton("\u53D6\u6D88");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();//退出
			}
		});
		button_2.setBounds(403, 114, 104, 23);
		contentPane.add(button_2);
		
		setVisible(true);
	}

	//替换
	public void replace(MyNotePad myNotePad) {
		//无文本被选中时
		if(myNotePad.log.getSelectedText() == null) {
			myNotePad.log.setCaretPosition(0); 	// 将光标放到编辑区开头  
			find(myNotePad);
		}
		//有文本被选中
		else {
			//替换
			if (textField_1.getText().length() == 0 && myNotePad.log.getSelectedText() != null)  
				myNotePad.log.replaceSelection("");  
	        if (textField_1.getText().length() > 0 && myNotePad.log.getSelectedText() != null)  
	        	myNotePad.log.replaceSelection(textField_1.getText()); 
	        //查找下一个
	        find(myNotePad);
		}
	}
	
	//替换全部
	public void replaceAll(MyNotePad myNotePad) {
		myNotePad.log.setCaretPosition(0); 	//将光标放到编辑区开头  
		int n = 0 ; int replaceCount = 0;

		//无查找内容
        if (textField.getText().length() == 0) {  
            JOptionPane.showMessageDialog(null, "请填写查找内容!", "提示", JOptionPane.WARNING_MESSAGE);  
            textField.requestFocus(true);  
            return;  
        }
    
        //循环替换
        while(n>-1) {  
            int FindStartPos = myNotePad.log.getCaretPosition();  
            String s = myNotePad.log.getText();	//文本内容
            String a = textField.getText();		//查找的内容
    		String b = textField_1.getText();	//替换的内容

    		//不区分大小写
            if(!chckbxNewCheckBox.isSelected()) {
            	s = s.toLowerCase(); //转为小写
            	a = a.toLowerCase(); //转为小写
            }
            
            //根据索引找到查找的位置
      		n = s.indexOf(a, FindStartPos); 
      		
			//查找,选中
      		if (n > -1) {  
      			myNotePad.log.setCaretPosition(n); 
                myNotePad.log.select(n, n + a.length());  
            } else {  
                if (replaceCount == 0) {  
                    JOptionPane.showMessageDialog(null, "找不到您查找的内容!", "记事本", JOptionPane.INFORMATION_MESSAGE); 
                    break;
                } else {  
                    JOptionPane.showMessageDialog(null, "成功替换" + replaceCount + "个匹配内容!", "替换成功", JOptionPane.INFORMATION_MESSAGE); 
                    break;
                }  
            } 
        	
      		//替换
            if (b.length() == 0 && myNotePad.log.getSelectedText() != null) {  
            	myNotePad.log.replaceSelection("");
            	replaceCount++;
            }  
            if (b.length() > 0 && myNotePad.log.getSelectedText() != null) {
            	myNotePad.log.replaceSelection(b);
            	replaceCount++;
            } 
            
        }
       
	}
	
	//查找下一个
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
		
		//根据索引找到查找的位置
		n = s.indexOf(a, FindStartPos); 
		
		//将查找的内容选中
		if(n>-1) {
			myNotePad.log.setCaretPosition(n); //索引
			myNotePad.log.select(n, n+a.length());
		}
		else {
			JOptionPane.showMessageDialog(null, "找不到您查找的内容!", "记事本", JOptionPane.INFORMATION_MESSAGE); 
		}
	}
	
}














