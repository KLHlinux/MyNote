package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Go extends JDialog {

	private JPanel contentPane;
	private JTextField textField;


	public Go(MyNotePad myNotePad) {
		setTitle("\u8F6C\u5230");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 481, 127);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);//初始位置
		setResizable(false);//不能调整窗口大小
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u8BF7\u8F93\u5165\u884C\u53F7\uFF1A");
		label.setBounds(24, 42, 93, 15);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setText("1");
		textField.selectAll();
		textField.setBounds(111, 39, 137, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("\u5B9A\u4F4D\u5230");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				go(myNotePad);
			}
		});
		btnNewButton.setBounds(277, 37, 86, 24);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("\u53D6\u6D88");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();//退出
			}
		});
		button.setBounds(373, 37, 92, 24);
		contentPane.add(button);
		
		setVisible(true);//可见
	}

	public void go(MyNotePad myNotePad) {
		int totalLine = myNotePad.log.getLineCount();  
        int[] lineNumber = new int[totalLine+1];  
        String s = myNotePad.log.getText();  
        int pos=0, t=0;  

        while (true) {  
            pos = s.indexOf("\n", pos);   //获取换行索引
            //System.out.println("引索pos:"+pos);  
            if (pos == -1)  
                break;  
            lineNumber[t++] = pos++;  //将换行的索引存到数组中 
        }  

        int gt = 1;  
        try {  
            gt = Integer.parseInt(textField.getText());  //获取行数
        } catch (NumberFormatException efe) {  
            JOptionPane.showMessageDialog(null, "请输入行数!", "提示", JOptionPane.WARNING_MESSAGE);  
            textField.requestFocus(true);  
            return;  
        }  

        if (gt>=1 && gt<=totalLine) {  
        	if (gt == 1)  
        		myNotePad.log.setCaretPosition(0);  //定位最前面
            else  
            	myNotePad.log.setCaretPosition(lineNumber[gt-2]+1); //定位到换行标志
        	dispose(); 
        } else { 
        	 JOptionPane.showMessageDialog(null, "行数超过了总行数", "记事本-跳行", JOptionPane.WARNING_MESSAGE);  //超出总行数
        }
	}


}
