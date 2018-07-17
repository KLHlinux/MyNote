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
	//确认按钮
	private JButton btnNewButton = new JButton("确认");
	//取消按钮
	private JButton button = new JButton("取消");
	//所有字体  
    private String [] fontArray = null;  
    //所有样式  
    private String [] styleArray = {"常规", "粗体", "斜体", "粗斜体"};  
    //所有预设字体大小  
    private String [] sizeArray = {"8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28",
    		"36", "48", "初号", "小初", "一号", "小一", "二号", "小二", "三号", "小三", "四号", "小四", "五号", "小五", "六号", "小六", "七号", "八号"};  
    //上面数组中对应的字体大小  
    private int [] sizeIntArray = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 
    		36, 48, 42, 36, 26, 24, 22, 18, 16, 15, 14, 12, 10, 9, 8, 7, 6, 5};  
    //预设字体，也是将来要返回的字体  
    private Font font = null; 
    //示例
    JLabel lblAabbcc = new JLabel("AaBbCc");
    //选择取消按钮的返回值  
    public static final int CANCEL_OPTION = 0;  
    //选择确定按钮的返回值  
    public static final int APPROVE_OPTION = 1;  
    // 返回的数值，默认取消  
    private int returnValue = CANCEL_OPTION;  
    
    public FontDialog(){  
    	this(new Font("宋体", Font.PLAIN , 12));  //默认字体
    }
    
	public FontDialog(Font font){
		setTitle("字体选择器");  
        this.font = font; 
        //初始化UI组件 
		init();
		//添加监听器
		addListener();
		//按照预设字体显示 
		setup();
		//对话框窗口设置
		setModal(true);  //此方法指定对话框是否是模式对话框。模式对话框包含发送到父框架的所有用户输入内容。
	}

	private void init(){
		setTitle("\u5B57\u4F53");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 435, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);//初始位置
		setResizable(false);//不能调整窗口大小
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
		
		// 获得系统字体  
        GraphicsEnvironment eq = GraphicsEnvironment.getLocalGraphicsEnvironment();  
        fontArray = eq.getAvailableFontFamilyNames();  
		
		list = new JList(fontArray);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//单选
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//取消横的滚动条
		scrollPane.setBounds(10, 65, 120, 231);
		contentPane.add(scrollPane);
		
		list_1 = new JList(styleArray);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//单选
		JScrollPane scrollPane_1 = new JScrollPane(list_1);
		scrollPane_1.setBounds(146, 65, 120, 231);
		contentPane.add(scrollPane_1);
		
		list_2 = new JList(sizeArray);
		list_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//单选
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
		
		//setVisible(true);//可见
	}
	
	private void setup(){  
        String fontName = font.getFamily(); 
        int fontStyle = font.getStyle(); 
        int fontSize = font.getSize();
        
        //如果预设的文字大小在选择列表中，则通过选择该列表中的某项进行设值，否则直接将预设文字大小写入文本框  
        boolean b = false;  
        for (int i = 0; i < sizeArray.length; i++) {  
            if (sizeArray[i].equals(String.valueOf(fontSize))) {  
                b = true;  
                break;  
            }  
        }  
        if(b){  
        	list_2.setSelectedValue(String.valueOf(fontSize), true);//选择文字大小列表中的某项   
        }else{  
        	textField_2.setText(String.valueOf(fontSize));  
        }
        //选择字体列表中的某项
        list.setSelectedValue(fontName, true);  
        //选择样式列表中的某项 
        list_1.setSelectedIndex(fontStyle);   
        //显示预览  
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
        
        //如果没有输入  
        if(sizeStr.length() == 0){  
            JOptionPane.showMessageDialog(this, "字体（大小）必须是有效数值！", "错误", JOptionPane.ERROR_MESSAGE);  
            return null;  
        }
        int fontSize = 0;  
        //通过循环对比文字大小输入是否在现有列表内  
        for (int i = 0; i < sizeArray.length; i++){  
            if(sizeStr.equals(sizeArray[i])){  
                fontSize = sizeIntArray[i]; //在列表获得字体大小的值，并返回  
                break;  
            }  
        }  
        //没有在列表内  
        if (fontSize == 0){
            try{  
                fontSize = Integer.parseInt(sizeStr);  
                if(fontSize < 1){  
                	JOptionPane.showMessageDialog(this,"字体（大小）必须是有效数值！", "错误", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }catch (NumberFormatException nfe){  
            	JOptionPane.showMessageDialog(this,"字体（大小）必须是有效数值！", "错误", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return new Font(fontName, fontStyle, fontSize);
	}
	
	private void addListener() {  
		
		//文本的获得焦点事件的监听器
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
        
		// 字体列表发生选择事件的监听器  
        list.addListSelectionListener(new ListSelectionListener() {  
            public void valueChanged(ListSelectionEvent e) {  
                if (!e.getValueIsAdjusting()) {  
                	textField.setText(String.valueOf(list.getSelectedValue()));  
                    // 设置预览  
                    setPreview();  
                }  
            }  
        });  
        list_1.addListSelectionListener(new ListSelectionListener() {  
            public void valueChanged(ListSelectionEvent e) {  
                if (!e.getValueIsAdjusting()) {  
                	textField_1.setText(String.valueOf(list_1.getSelectedValue()));  
                    // 设置预览  
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
                    // 设置预览  
                    setPreview();  
                }  
            }  
        });  
        
        // 确定按钮的事件监听  
        btnNewButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e) {  
                font = groupFont();//获取组合字体
                returnValue = APPROVE_OPTION;// 设置返回值 
                disposeDialog();// 关闭窗口 
            }  
        });
        // 取消按钮事件监听
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














