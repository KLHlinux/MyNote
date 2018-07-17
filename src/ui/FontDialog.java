package ui;

import java.awt.*;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class FontDialog extends JDialog{

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JList list;
	private JList list_1;
	private JList list_2;
	//ȷ�ϰ�ť
	private JButton btnNewButton = new JButton("ȷ��");
	//ȡ����ť
	private JButton button = new JButton("ȡ��");
	//��������  
    private String [] fontArray = null;  
    //������ʽ  
    private String [] styleArray = {"����", "����", "б��", "��б��"};  
    //����Ԥ�������С  
    private String [] sizeArray = {"8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28",
    		"36", "48", "����", "С��", "һ��", "Сһ", "����", "С��", "����", "С��", "�ĺ�", "С��", "���", "С��", "����", "С��", "�ߺ�", "�˺�"};  
    //���������ж�Ӧ�������С  
    private int [] sizeIntArray = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 
    		36, 48, 42, 36, 26, 24, 22, 18, 16, 15, 14, 12, 10, 9, 8, 7, 6, 5};  
    //Ԥ�����壬Ҳ�ǽ���Ҫ���ص�����  
    private Font font = null; 
    //ʾ��
    JLabel lblAabbcc = new JLabel("AaBbCc");
    //ѡ��ȡ����ť�ķ���ֵ  
    public static final int CANCEL_OPTION = 0;  
    //ѡ��ȷ����ť�ķ���ֵ  
    public static final int APPROVE_OPTION = 1;  
    // ���ص���ֵ��Ĭ��ȡ��  
    private int returnValue = CANCEL_OPTION;  
    
    public FontDialog(){  
    	this(new Font("����", Font.PLAIN , 12));  //Ĭ������
    }
    
	public FontDialog(Font font){
		setTitle("����ѡ����");  
        this.font = font; 
        //��ʼ��UI��� 
		init();
		//��Ӽ�����
		addListener();
		//����Ԥ��������ʾ 
		setup();
		//�Ի��򴰿�����
		setModal(true);  //�˷���ָ���Ի����Ƿ���ģʽ�Ի���ģʽ�Ի���������͵�����ܵ������û��������ݡ�
	}

	private void init(){
		setTitle("\u5B57\u4F53");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 435, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);//��ʼλ��
		setResizable(false);//���ܵ������ڴ�С
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u5B57\u4F53\uFF1A");
		label.setBounds(10, 10, 54, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u5B57\u5F62\uFF1A");
		label_1.setBounds(146, 10, 54, 15);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u5927\u5C0F\uFF1A");
		label_2.setBounds(289, 10, 54, 15);
		contentPane.add(label_2);
		
		textField = new JTextField();
		textField.setBounds(10, 34, 120, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(146, 35, 120, 21);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(289, 35, 120, 21);
		contentPane.add(textField_2);
		
		// ���ϵͳ����  
        GraphicsEnvironment eq = GraphicsEnvironment.getLocalGraphicsEnvironment();  
        fontArray = eq.getAvailableFontFamilyNames();  
		
		list = new JList(fontArray);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//��ѡ
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//ȡ����Ĺ�����
		scrollPane.setBounds(10, 65, 120, 231);
		contentPane.add(scrollPane);
		
		list_1 = new JList(styleArray);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//��ѡ
		JScrollPane scrollPane_1 = new JScrollPane(list_1);
		scrollPane_1.setBounds(146, 65, 120, 231);
		contentPane.add(scrollPane_1);
		
		list_2 = new JList(sizeArray);
		list_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//��ѡ
		JScrollPane scrollPane_2 = new JScrollPane(list_2);
		scrollPane_2.setBounds(289, 65, 120, 231);
		contentPane.add(scrollPane_2);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u793A\u4F8B", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(51, 306, 117, 56);
		contentPane.add(panel);
		
		panel.add(lblAabbcc);
		
		btnNewButton.setBounds(199, 321, 67, 35);
		contentPane.add(btnNewButton);

		button.setBounds(276, 321, 67, 35);
		contentPane.add(button);
		
		//setVisible(true);//�ɼ�
	}
	
	private void setup(){  
        String fontName = font.getFamily(); 
        int fontStyle = font.getStyle(); 
        int fontSize = font.getSize();
        
        //���Ԥ������ִ�С��ѡ���б��У���ͨ��ѡ����б��е�ĳ�������ֵ������ֱ�ӽ�Ԥ�����ִ�Сд���ı���  
        boolean b = false;  
        for (int i = 0; i < sizeArray.length; i++) {  
            if (sizeArray[i].equals(String.valueOf(fontSize))) {  
                b = true;  
                break;  
            }  
        }  
        if(b){  
        	list_2.setSelectedValue(String.valueOf(fontSize), true);//ѡ�����ִ�С�б��е�ĳ��   
        }else{  
        	textField_2.setText(String.valueOf(fontSize));  
        }
        //ѡ�������б��е�ĳ��
        list.setSelectedValue(fontName, true);  
        //ѡ����ʽ�б��е�ĳ�� 
        list_1.setSelectedIndex(fontStyle);   
        //��ʾԤ��  
        setPreview();  
    }

	private void setPreview(){
		Font f = groupFont();  
		lblAabbcc.setFont(f);
	}
	
	private Font groupFont(){
		String fontName = textField.getText();  
        int fontStyle = list_1.getSelectedIndex();  
        String sizeStr = textField_2.getText().trim(); 
        
        //���û������  
        if(sizeStr.length() == 0){  
            JOptionPane.showMessageDialog(this, "���壨��С����������Ч��ֵ��", "����", JOptionPane.ERROR_MESSAGE);  
            return null;  
        }
        int fontSize = 0;  
        //ͨ��ѭ���Ա����ִ�С�����Ƿ��������б���  
        for (int i = 0; i < sizeArray.length; i++){  
            if(sizeStr.equals(sizeArray[i])){  
                fontSize = sizeIntArray[i]; //���б��������С��ֵ��������  
                break;  
            }  
        }  
        //û�����б���  
        if (fontSize == 0){
            try{  
                fontSize = Integer.parseInt(sizeStr);  
                if(fontSize < 1){  
                	JOptionPane.showMessageDialog(this,"���壨��С����������Ч��ֵ��", "����", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }catch (NumberFormatException nfe){  
            	JOptionPane.showMessageDialog(this,"���壨��С����������Ч��ֵ��", "����", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return new Font(fontName, fontStyle, fontSize);
	}
	
	private void addListener() {  
		
		//�ı��Ļ�ý����¼��ļ�����
		textField.addFocusListener(new FocusListener() {  
            public void focusLost(FocusEvent e) {  
                setPreview();  
            }  
            public void focusGained(FocusEvent e) {  
            	textField.selectAll();  
            }  
        });  
		textField_1.addFocusListener(new FocusListener() {  
            public void focusLost(FocusEvent e) {  
                setPreview();  
            }  
            public void focusGained(FocusEvent e) {  
            	textField_1.selectAll();  
            }  
        });
		textField_2.addFocusListener(new FocusListener() {  
            public void focusLost(FocusEvent e) {  
                setPreview();  
            }  
            public void focusGained(FocusEvent e) {  
            	textField_2.selectAll();  
            }  
        });
        
		// �����б���ѡ���¼��ļ�����  
        list.addListSelectionListener(new ListSelectionListener() {  
            public void valueChanged(ListSelectionEvent e) {  
                if (!e.getValueIsAdjusting()) {  
                	textField.setText(String.valueOf(list.getSelectedValue()));  
                    // ����Ԥ��  
                    setPreview();  
                }  
            }  
        });  
        list_1.addListSelectionListener(new ListSelectionListener() {  
            public void valueChanged(ListSelectionEvent e) {  
                if (!e.getValueIsAdjusting()) {  
                	textField_1.setText(String.valueOf(list_1.getSelectedValue()));  
                    // ����Ԥ��  
                    setPreview();  
                }  
            }  
        });  
        list_2.addListSelectionListener(new ListSelectionListener() {  
            public void valueChanged(ListSelectionEvent e) {  
                if (!e.getValueIsAdjusting()) {  
                    if(!textField_2.isFocusOwner()){  
                    	textField_2.setText(String.valueOf(list_2.getSelectedValue()));  
                    }  
                    // ����Ԥ��  
                    setPreview();  
                }  
            }  
        });  
        
        // ȷ����ť���¼�����  
        btnNewButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {  
                font = groupFont();//��ȡ�������
                returnValue = APPROVE_OPTION;// ���÷���ֵ 
                disposeDialog();// �رմ��� 
            }  
        });
        // ȡ����ť�¼�����
        button.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {  
            	disposeDialog();  
            }  
        });  
    
	}  

	public final Font getSelectFont(){  
		return font; 
    }

	public final int showFontDialog(JFrame owner){
		setLocationRelativeTo(owner);
		setVisible(true);
		return returnValue;
	}  
	
	private void disposeDialog() {
		FontDialog.this.removeAll();
		FontDialog.this.dispose();
	}
	
}














