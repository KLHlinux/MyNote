package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.UndoManager;

public class MyNotePad extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	//JMenuBar、JMenu、JMenuItem初始数据
	JMenuBar menuBar = new JMenuBar();
	
	//JMenu名称数组
	String[] mnames = {"文件(F)","编辑(E)","格式(T)","查看(V)","帮助"};
	char[] mkeys = {'F','E','T','V',0};//JMenu快捷键数组
	JMenu[] menus = new JMenu[mnames.length];
	
	//JMenuItem名称数组
	String[][] names = {{"新建","打开","保存","另存为","-","退出"},
			{"撤销","重做","-","复制","剪切","粘贴","-","全部选中","查找","替代","-","转到"},
			{"自动换行","字体","颜色"},
			{"状态栏","工具栏"},
			{"帮助主题","-","关于我的记事本"}};
	
	JMenuItem[][] items = new JMenuItem[names.length][names[1].length];
	
	//JMenuItem动作命令
	String[][] actions = {{"new","open","save","saveAs","-","exit"},
			{"undo","redo","-","copy","cut","paste","-","selectAll","find","replace","-","go"},
			{"autoWrap","font","color"},
			{"status","toolBar"},
			{"help","-","about"}};
	//JMenuItem图标
	String[][] icons = {{"create.gif","open.gif","save.gif",null,null,null},
			{null,null,null,"copy.gif","cut.gif","paste.gif",null,null,null,null,null,null},
			{null,null,null},
			{null,null},
			{null,null,null}};
	
	//JMenuItem快捷键数组
	char[][] keys = {{'N','O','S',0,0,'X'},
			{'Z','Y',0,'C','X','V',0,'A','F','R',0,0},
			{0,0,0},
			{0,0},
			{0,0,0}};
	//JMenuItem类型数组:	0-JMenuItem  	1-JCheckBoxMenuItem  	2-JRadioButtonMenuItem
	int[][] type = {{0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,0},
			{1,1},
			{0,0,0}};
	//工具栏
	JToolBar toolBar = new JToolBar();
	//工具名称数组
	String[] toolnames = {"新建","打开","保存","复制","剪切","粘贴"};
	//工具栏按钮 
	JButton[] toolitems = new JButton[toolnames.length];
	//工具栏按钮动作
	String[] toolactions = {"new","open","save","copy","cut","paste"};
	//JMenuItem图标
	String[] toolicons = {"create.gif","open.gif","save.gif","copy.gif","cut.gif","paste.gif"};
	
	//状态栏
	JLabel statusBar = new JLabel("状态栏");
	//文本田
	JTextArea log = new JTextArea();
	//撤销和恢复的管理器
	private UndoManager undo = new UndoManager();
	//文件选择器组件
	private JFileChooser fc = new JFileChooser();
	//判断文件是否有改动,如果有改动就变成true
	private boolean flag = false;  
	
	//初始化菜单栏
 	private void initMenu()
	{
		//初始化菜单栏
		for(int i=0 ; i<menus.length ; i++){
			menus[i] = new JMenu(mnames[i]); //JMenu初始化
			if(mkeys[i]!=0){
				menus[i].setMnemonic(mkeys[i]); //设置快捷键
			}
			menuBar.add(menus[i]); //菜单添加到菜单栏中
		}
		
		//初始化菜单
		for(int i=0 ; i<names.length ; i++) {
			for(int j=0 ; j<names[i].length ; j++) {
				if(!names[i][j].equals("-")) {
					//菜单项的初始化
					if(type[i][j]==0) {
						items[i][j] = new JMenuItem(names[i][j]);
					}
					if(type[i][j]==1) {
						items[i][j] = new JCheckBoxMenuItem(names[i][j],true);
					}
					if(type[i][j]==2) {
						items[i][j] = new JRadioButtonMenuItem(names[i][j],true);
					}
					//设置动作命令
					items[i][j].setActionCommand(actions[i][j]);
					//键盘快捷键ctrl+a
					if(keys[i][j]!=0) {
						items[i][j].setMnemonic(keys[i][j]);
						items[i][j].setAccelerator(KeyStroke.getKeyStroke(keys[i][j],InputEvent.CTRL_MASK));
					}
					//设置图标
					if(icons[i][j]!=null) {
						ImageIcon image = new ImageIcon(MyNotePad.class.getResource("resource/"+icons[i][j]));
						image.setImage(image.getImage().getScaledInstance(15, 15,Image.SCALE_DEFAULT ));  //设置图标大小
						items[i][j].setIcon(image);
					}
					items[i][j].addActionListener(this);//设置监听器
					menus[i].add(items[i][j]);//菜单项添加到菜单中
				}else {
					menus[i].addSeparator();  //分隔线
				}
			}
		}
		
		//工具栏配置
		for(int i=0 ; i<toolnames.length ; i++){
			toolitems[i] = new JButton(toolnames[i]); //定义按钮
			toolitems[i].setActionCommand(toolactions[i]);//设置动作
			toolitems[i].addActionListener(this);//设置监听器
			//设置图标
			ImageIcon image = new ImageIcon(MyNotePad.class.getResource("resource/"+toolicons[i]));
			image.setImage(image.getImage().getScaledInstance(15, 15,Image.SCALE_DEFAULT ));  //设置图标大小
			toolitems[i].setIcon(image);
			
			toolBar.add(toolitems[i]); //添加到工具栏
		}
	}

	public MyNotePad() {
		this.setTitle("无标题");
		
		//初始化菜单栏
		initMenu(); 
		
		//配置撤销和恢复的管理器
		log.getDocument().addUndoableEditListener(undo); 
		
		//判断文本内容是否发生改变
		log.getDocument().addDocumentListener(new DocumentListener(){
		   @Override
		   public void changedUpdate(DocumentEvent e) {
		    flag = true;
		   }
		   @Override
		   public void insertUpdate(DocumentEvent e) {
		    flag = true;
		   }
		   @Override
		   public void removeUpdate(DocumentEvent e) {
		    flag = true;
		   }
		  });
		
		log.setLineWrap(true);//自动换行
		this.setJMenuBar(menuBar);//面板添加菜单栏
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(toolBar,BorderLayout.PAGE_START);//添加工具栏
		getContentPane().add(new JScrollPane(log),BorderLayout.CENTER);//添加含滚动条的文本田
		getContentPane().add(statusBar,BorderLayout.SOUTH); //添加状态栏
		
		this.setSize(500,500);
		this.setLocationRelativeTo(null);
		//设置关闭时什么也不做
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		//监听关闭按钮的点击操作
		this.addWindowListener(new WindowAdapter(){
			//WindowAdapter是个适配器类//重写windowClosing方法
			public void windowClosing(WindowEvent e) {
				exit();
			}  
		});
		
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		String cmd = e.getActionCommand();
		
		//新建文件
		if("new".equals(cmd)) {
			NewFile();
		}
		//打开文件
		if("open".equals(cmd)) {
			//判断文件内容是否有改动
			if(flag == true) {    
				int n=JOptionPane.showConfirmDialog(null, "是否保存文件!","记事本",JOptionPane.YES_NO_CANCEL_OPTION);
		        if(n==0){  //是 返回0  否返回1 取消 返回2 
		       	 	Save(); //点是，则保存文件然后打开文件
		       	 	OpenFile();
		        }else if(n==1){ //返回否
		        	OpenFile();
		        }else if(n==2){  //返回取消
		            return ;
		        }
			}else {
				OpenFile();
			}
		}
		//保存文件
		if("save".equals(cmd)) {
			Save() ;
		}
		//另存为
		if("saveAs".equals(cmd)) {
			SaveAs();
		}
		//退出
		if("exit".equals(cmd)) {
			exit();
		}
		//撤销
		if("undo".equals(cmd)) {
			if(undo.canUndo()) {
				undo.undo();
			}
		}
		//恢复
		if("redo".equals(cmd)) {
			if(undo.canRedo()) {
				undo.redo();
			}
		}
		//复制
		if("copy".equals(cmd)) {
			log.copy(); //直接调用自带的函数
		}
		//剪切
		if("cut".equals(cmd)) {
			log.cut(); //直接调用自带的函数
		}
		//粘贴
		if("paste".equals(cmd)) {
			log.paste(); //直接调用自带的函数
		}
		//全选
		if("selectAll".equals(cmd)) {
			log.selectAll(); //直接调用自带的函数
		}
		//查找
		if("find".equals(cmd)) {
			new Find(this);//查找的对话框
		}
		//替代
		if("replace".equals(cmd)) {
			new Replace(this);//替代的对话框
		}
		//转到
		if("go".equals(cmd)) {
			if(log.getLineWrap()==true) {
				JOptionPane.showMessageDialog(null, "“转至”命令在启动自动换行时不可用", "提示", JOptionPane.WARNING_MESSAGE);
			}else {
				new Go(this);
			}
		}
		//控制自动换行
		if("autoWrap".equals(cmd)){
			log.setLineWrap(!log.getLineWrap());
		}
		//字体
		if("font".equals(cmd)){
			FontDialog fontChooser = new FontDialog(log.getFont());
            // 打开一个字体选择器窗口，参数为父级所有者窗体。返回一个整型，代表设置字体时按下了确定或是取消  
            int returnValue = fontChooser.showFontDialog(MyNotePad.this);
            // 如果按下的是确定按钮
            if (returnValue == FontDialog.APPROVE_OPTION) {
	            // 获取选择的字体  
	            Font font = fontChooser.getSelectFont();
	            // 将字体设置到JTextArea中  
	            log.setFont(font);
            }
		}
		//颜色
		if("color".equals(cmd)){
			Color();
		}
		//控制状态栏
		if("status".equals(cmd)){
			statusBar.setVisible(!statusBar.isVisible());
		}
		//控制工具栏
		if("toolBar".equals(cmd)) {
			toolBar.setVisible(!toolBar.isVisible());
		}
		//帮助
		if("help".equals(cmd)) {
			help();
		}
		//关于
		if("about".equals(cmd)) {
			new about();
		}
	}

	private void help() {
		try {
			File f = new File(MyNotePad.class.getResource("resource/记事本.chm").toURI());
			Desktop desktop = Desktop.getDesktop();
			desktop.open(f);
		}catch(IOException e1) {
			e1.printStackTrace();
		}catch(URISyntaxException e1) {
			e1.printStackTrace();
		}
		
	}

	private void Color() {
		JColorChooser chooser=new JColorChooser();    //实例化颜色选择器
        Color color=chooser.showDialog(this,"选取颜色",Color.lightGray );  //得到选择的颜色
        if (color==null) {  //如果未选取
          color=Color.white;  //则设置颜色为白色
        }
        log.setBackground(color);  //改变面板的背景色
	}

	public void OpenFile(){
		int n = fc.showOpenDialog(this);
		if(n==0) {
			File file = fc.getSelectedFile(); //选择文件
			
			try {
				FileReader fr = new FileReader(file); //文件读取
				int ch = 0;
				String str = ""; //存储数据
				while((ch=fr.read())!=-1) {
					str=str+(char)ch;
				}
				fr.close();
				this.setTitle(file.getName()); //获取记事本的标题
				log.setText(str);//将数据添加到记事本中
				flag = false ; //打开文件,标识重置，示意文本没有改动
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else {
			//log.append("你选择了取消!\n");
		}
		log.setCaretPosition(log.getDocument().getLength());
	}
	
	public void SaveAs() {
		int n = fc.showSaveDialog(this);
		if(n==0) {
			File file = fc.getSelectedFile();
			try {
				FileWriter fw = new FileWriter(file); //文件写入
				String str = log.getText();//获得文本田
				fw.write(str); //存储字符串
				fw.close();
				flag = false ; //保存文件,标识重置，示意文本没有改动
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else {
			//log.append("你选择了取消保存!\n");
		}
		log.setCaretPosition(log.getDocument().getLength());
	}
	
	public void Save() {
		File file = new File("C:\\Users\\KLH\\Desktop\\"+getTitle()+" "); //桌面的标题路径
		if(!file.exists()) {
			SaveAs();  //文件存在，调用另存为
		}else {
			try {
				FileWriter fw = new FileWriter(file); //文件写入
				String str = log.getText();//获得文本田
				fw.write(str); //存储字符串
				fw.close();
				flag = false ; //保存文件,标识重置，示意文本没有改动
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}	
	}
	
	public void NewFile() {
		//判断文件内容是否有改动
		if(flag == true) {    
			int n=JOptionPane.showConfirmDialog(null, "是否保存文件!","记事本",JOptionPane.YES_NO_CANCEL_OPTION);
	        if(n==0){  //是 返回0  否返回1 取消 返回2 
	       	 	Save(); //点是，则保存文件然后打开文件
	        }else if(n==1){ //返回否
	        	
	        }else if(n==2){  //返回取消
	            return ;
	        }
	        this.setTitle("无标题");//标题清空
	        log.setText("");//文本清空
	        flag = false ; //标识改为文本没改动(标识重置)
		}else {
			this.setTitle("无标题");//标题清空
	        log.setText("");//文本清空
	        flag = false ; //标识改为文本没改动(标识重置)
		}
	}

	public void exit() {
		//判断文件内容是否有改动
		if(flag == true) {    
			int n=JOptionPane.showConfirmDialog(null, "是否保存文件!","记事本",JOptionPane.YES_NO_CANCEL_OPTION);
	        if(n==0){  //是 返回0  否返回1 取消 返回2 
	       	 	Save(); //点是，则保存文件然后打开文件
	        }else if(n==1){ //返回否
	        	System.exit(0);
	        }else if(n==2){  //返回取消
	            return ;
	        }
		}else {
			System.exit(0);
		}
	}
	
	//入口
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MyNotePad();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
	
}















