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
		setLocationRelativeTo(null);//��ʼλ��
		setResizable(false);//���ܵ������ڴ�С
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
				replace(myNotePad);  //�滻
			}
		});
		button.setBounds(403, 48, 104, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u5168\u90E8\u66FF\u6362");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replaceAll(myNotePad);   //�滻ȫ��
			}
		});
		button_1.setBounds(403, 81, 104, 23);
		contentPane.add(button_1);
		
		//ȡ����ť
		JButton button_2 = new JButton("\u53D6\u6D88");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();//�˳�
			}
		});
		button_2.setBounds(403, 114, 104, 23);
		contentPane.add(button_2);
		
		setVisible(true);
	}

	//�滻
	public void replace(MyNotePad myNotePad) {
		//���ı���ѡ��ʱ
		if(myNotePad.log.getSelectedText() == null) {
			myNotePad.log.setCaretPosition(0); 	// �����ŵ��༭����ͷ  
			find(myNotePad);
		}
		//���ı���ѡ��
		else {
			//�滻
			if (textField_1.getText().length() == 0 && myNotePad.log.getSelectedText() != null)  
				myNotePad.log.replaceSelection("");  
	        if (textField_1.getText().length() > 0 && myNotePad.log.getSelectedText() != null)  
	        	myNotePad.log.replaceSelection(textField_1.getText()); 
	        //������һ��
	        find(myNotePad);
		}
	}
	
	//�滻ȫ��
	public void replaceAll(MyNotePad myNotePad) {
		myNotePad.log.setCaretPosition(0); 	//�����ŵ��༭����ͷ  
		int n = 0 ; int replaceCount = 0;

		//�޲�������
        if (textField.getText().length() == 0) {  
            JOptionPane.showMessageDialog(null, "����д��������!", "��ʾ", JOptionPane.WARNING_MESSAGE);  
            textField.requestFocus(true);  
            return;  
        }
    
        //ѭ���滻
        while(n>-1) {  
            int FindStartPos = myNotePad.log.getCaretPosition();  
            String s = myNotePad.log.getText();	//�ı�����
            String a = textField.getText();		//���ҵ�����
    		String b = textField_1.getText();	//�滻������

    		//�����ִ�Сд
            if(!chckbxNewCheckBox.isSelected()) {
            	s = s.toLowerCase(); //תΪСд
            	a = a.toLowerCase(); //תΪСд
            }
            
            //���������ҵ����ҵ�λ��
      		n = s.indexOf(a, FindStartPos); 
      		
			//����,ѡ��
      		if (n > -1) {  
      			myNotePad.log.setCaretPosition(n); 
                myNotePad.log.select(n, n + a.length());  
            } else {  
                if (replaceCount == 0) {  
                    JOptionPane.showMessageDialog(null, "�Ҳ��������ҵ�����!", "���±�", JOptionPane.INFORMATION_MESSAGE); 
                    break;
                } else {  
                    JOptionPane.showMessageDialog(null, "�ɹ��滻" + replaceCount + "��ƥ������!", "�滻�ɹ�", JOptionPane.INFORMATION_MESSAGE); 
                    break;
                }  
            } 
        	
      		//�滻
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
	
	//������һ��
	public void find(MyNotePad myNotePad) {
		String s = myNotePad.log.getText();	//�ı�����
		String a = textField.getText();		//���ҵ�����
		int FindStartPos = myNotePad.log.getCaretPosition(); //��ʼ������,����λ��
		int n = 0;
		
		//�����ִ�Сд
		if(!chckbxNewCheckBox.isSelected()) {
			s = s.toLowerCase(); //תΪСд
			a = a.toLowerCase(); //תΪСд
		}
		
		//���������ҵ����ҵ�λ��
		n = s.indexOf(a, FindStartPos); 
		
		//�����ҵ�����ѡ��
		if(n>-1) {
			myNotePad.log.setCaretPosition(n); //����
			myNotePad.log.select(n, n+a.length());
		}
		else {
			JOptionPane.showMessageDialog(null, "�Ҳ��������ҵ�����!", "���±�", JOptionPane.INFORMATION_MESSAGE); 
		}
	}
	
}














