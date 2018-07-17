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
	
	//JMenuBar��JMenu��JMenuItem��ʼ����
	JMenuBar menuBar = new JMenuBar();
	
	//JMenu��������
	String[] mnames = {"�ļ�(F)","�༭(E)","��ʽ(T)","�鿴(V)","����"};
	char[] mkeys = {'F','E','T','V',0};//JMenu��ݼ�����
	JMenu[] menus = new JMenu[mnames.length];
	
	//JMenuItem��������
	String[][] names = {{"�½�","��","����","���Ϊ","-","�˳�"},
			{"����","����","-","����","����","ճ��","-","ȫ��ѡ��","����","���","-","ת��"},
			{"�Զ�����","����","��ɫ"},
			{"״̬��","������"},
			{"��������","-","�����ҵļ��±�"}};
	
	JMenuItem[][] items = new JMenuItem[names.length][names[1].length];
	
	//JMenuItem��������
	String[][] actions = {{"new","open","save","saveAs","-","exit"},
			{"undo","redo","-","copy","cut","paste","-","selectAll","find","replace","-","go"},
			{"autoWrap","font","color"},
			{"status","toolBar"},
			{"help","-","about"}};
	//JMenuItemͼ��
	String[][] icons = {{"create.gif","open.gif","save.gif",null,null,null},
			{null,null,null,"copy.gif","cut.gif","paste.gif",null,null,null,null,null,null},
			{null,null,null},
			{null,null},
			{null,null,null}};
	
	//JMenuItem��ݼ�����
	char[][] keys = {{'N','O','S',0,0,'X'},
			{'Z','Y',0,'C','X','V',0,'A','F','R',0,0},
			{0,0,0},
			{0,0},
			{0,0,0}};
	//JMenuItem��������:	0-JMenuItem  	1-JCheckBoxMenuItem  	2-JRadioButtonMenuItem
	int[][] type = {{0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,0},
			{1,1},
			{0,0,0}};
	//������
	JToolBar toolBar = new JToolBar();
	//������������
	String[] toolnames = {"�½�","��","����","����","����","ճ��"};
	//��������ť 
	JButton[] toolitems = new JButton[toolnames.length];
	//��������ť����
	String[] toolactions = {"new","open","save","copy","cut","paste"};
	//JMenuItemͼ��
	String[] toolicons = {"create.gif","open.gif","save.gif","copy.gif","cut.gif","paste.gif"};
	
	//״̬��
	JLabel statusBar = new JLabel("״̬��");
	//�ı���
	JTextArea log = new JTextArea();
	//�����ͻָ��Ĺ�����
	private UndoManager undo = new UndoManager();
	//�ļ�ѡ�������
	private JFileChooser fc = new JFileChooser();
	//�ж��ļ��Ƿ��иĶ�,����иĶ��ͱ��true
	private boolean flag = false;  
	
	//��ʼ���˵���
 	private void initMenu()
	{
		//��ʼ���˵���
		for(int i=0 ; i<menus.length ; i++){
			menus[i] = new JMenu(mnames[i]); //JMenu��ʼ��
			if(mkeys[i]!=0){
				menus[i].setMnemonic(mkeys[i]); //���ÿ�ݼ�
			}
			menuBar.add(menus[i]); //�˵���ӵ��˵�����
		}
		
		//��ʼ���˵�
		for(int i=0 ; i<names.length ; i++) {
			for(int j=0 ; j<names[i].length ; j++) {
				if(!names[i][j].equals("-")) {
					//�˵���ĳ�ʼ��
					if(type[i][j]==0) {
						items[i][j] = new JMenuItem(names[i][j]);
					}
					if(type[i][j]==1) {
						items[i][j] = new JCheckBoxMenuItem(names[i][j],true);
					}
					if(type[i][j]==2) {
						items[i][j] = new JRadioButtonMenuItem(names[i][j],true);
					}
					//���ö�������
					items[i][j].setActionCommand(actions[i][j]);
					//���̿�ݼ�ctrl+a
					if(keys[i][j]!=0) {
						items[i][j].setMnemonic(keys[i][j]);
						items[i][j].setAccelerator(KeyStroke.getKeyStroke(keys[i][j],InputEvent.CTRL_MASK));
					}
					//����ͼ��
					if(icons[i][j]!=null) {
						ImageIcon image = new ImageIcon(MyNotePad.class.getResource("resource/"+icons[i][j]));
						image.setImage(image.getImage().getScaledInstance(15, 15,Image.SCALE_DEFAULT ));  //����ͼ���С
						items[i][j].setIcon(image);
					}
					items[i][j].addActionListener(this);//���ü�����
					menus[i].add(items[i][j]);//�˵�����ӵ��˵���
				}else {
					menus[i].addSeparator();  //�ָ���
				}
			}
		}
		
		//����������
		for(int i=0 ; i<toolnames.length ; i++){
			toolitems[i] = new JButton(toolnames[i]); //���尴ť
			toolitems[i].setActionCommand(toolactions[i]);//���ö���
			toolitems[i].addActionListener(this);//���ü�����
			//����ͼ��
			ImageIcon image = new ImageIcon(MyNotePad.class.getResource("resource/"+toolicons[i]));
			image.setImage(image.getImage().getScaledInstance(15, 15,Image.SCALE_DEFAULT ));  //����ͼ���С
			toolitems[i].setIcon(image);
			
			toolBar.add(toolitems[i]); //��ӵ�������
		}
	}

	public MyNotePad() {
		this.setTitle("�ޱ���");
		
		//��ʼ���˵���
		initMenu(); 
		
		//���ó����ͻָ��Ĺ�����
		log.getDocument().addUndoableEditListener(undo); 
		
		//�ж��ı������Ƿ����ı�
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
		
		log.setLineWrap(true);//�Զ�����
		this.setJMenuBar(menuBar);//�����Ӳ˵���
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(toolBar,BorderLayout.PAGE_START);//��ӹ�����
		getContentPane().add(new JScrollPane(log),BorderLayout.CENTER);//��Ӻ����������ı���
		getContentPane().add(statusBar,BorderLayout.SOUTH); //���״̬��
		
		this.setSize(500,500);
		this.setLocationRelativeTo(null);
		//���ùر�ʱʲôҲ����
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		//�����رհ�ť�ĵ������
		this.addWindowListener(new WindowAdapter(){
			//WindowAdapter�Ǹ���������//��дwindowClosing����
			public void windowClosing(WindowEvent e) {
				exit();
			}  
		});
		
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		String cmd = e.getActionCommand();
		
		//�½��ļ�
		if("new".equals(cmd)) {
			NewFile();
		}
		//���ļ�
		if("open".equals(cmd)) {
			//�ж��ļ������Ƿ��иĶ�
			if(flag == true) {    
				int n=JOptionPane.showConfirmDialog(null, "�Ƿ񱣴��ļ�!","���±�",JOptionPane.YES_NO_CANCEL_OPTION);
		        if(n==0){  //�� ����0  �񷵻�1 ȡ�� ����2 
		       	 	Save(); //���ǣ��򱣴��ļ�Ȼ����ļ�
		       	 	OpenFile();
		        }else if(n==1){ //���ط�
		        	OpenFile();
		        }else if(n==2){  //����ȡ��
		            return ;
		        }
			}else {
				OpenFile();
			}
		}
		//�����ļ�
		if("save".equals(cmd)) {
			Save() ;
		}
		//���Ϊ
		if("saveAs".equals(cmd)) {
			SaveAs();
		}
		//�˳�
		if("exit".equals(cmd)) {
			exit();
		}
		//����
		if("undo".equals(cmd)) {
			if(undo.canUndo()) {
				undo.undo();
			}
		}
		//�ָ�
		if("redo".equals(cmd)) {
			if(undo.canRedo()) {
				undo.redo();
			}
		}
		//����
		if("copy".equals(cmd)) {
			log.copy(); //ֱ�ӵ����Դ��ĺ���
		}
		//����
		if("cut".equals(cmd)) {
			log.cut(); //ֱ�ӵ����Դ��ĺ���
		}
		//ճ��
		if("paste".equals(cmd)) {
			log.paste(); //ֱ�ӵ����Դ��ĺ���
		}
		//ȫѡ
		if("selectAll".equals(cmd)) {
			log.selectAll(); //ֱ�ӵ����Դ��ĺ���
		}
		//����
		if("find".equals(cmd)) {
			new Find(this);//���ҵĶԻ���
		}
		//���
		if("replace".equals(cmd)) {
			new Replace(this);//����ĶԻ���
		}
		//ת��
		if("go".equals(cmd)) {
			if(log.getLineWrap()==true) {
				JOptionPane.showMessageDialog(null, "��ת���������������Զ�����ʱ������", "��ʾ", JOptionPane.WARNING_MESSAGE);
			}else {
				new Go(this);
			}
		}
		//�����Զ�����
		if("autoWrap".equals(cmd)){
			log.setLineWrap(!log.getLineWrap());
		}
		//����
		if("font".equals(cmd)){
			FontDialog fontChooser = new FontDialog(log.getFont());
            // ��һ������ѡ�������ڣ�����Ϊ���������ߴ��塣����һ�����ͣ�������������ʱ������ȷ������ȡ��  
            int returnValue = fontChooser.showFontDialog(MyNotePad.this);
            // ������µ���ȷ����ť
            if (returnValue == FontDialog.APPROVE_OPTION) {
	            // ��ȡѡ�������  
	            Font font = fontChooser.getSelectFont();
	            // ���������õ�JTextArea��  
	            log.setFont(font);
            }
		}
		//��ɫ
		if("color".equals(cmd)){
			Color();
		}
		//����״̬��
		if("status".equals(cmd)){
			statusBar.setVisible(!statusBar.isVisible());
		}
		//���ƹ�����
		if("toolBar".equals(cmd)) {
			toolBar.setVisible(!toolBar.isVisible());
		}
		//����
		if("help".equals(cmd)) {
			help();
		}
		//����
		if("about".equals(cmd)) {
			new about();
		}
	}

	private void help() {
		try {
			File f = new File(MyNotePad.class.getResource("resource/���±�.chm").toURI());
			Desktop desktop = Desktop.getDesktop();
			desktop.open(f);
		}catch(IOException e1) {
			e1.printStackTrace();
		}catch(URISyntaxException e1) {
			e1.printStackTrace();
		}
		
	}

	private void Color() {
		JColorChooser chooser=new JColorChooser();    //ʵ������ɫѡ����
        Color color=chooser.showDialog(this,"ѡȡ��ɫ",Color.lightGray );  //�õ�ѡ�����ɫ
        if (color==null) {  //���δѡȡ
          color=Color.white;  //��������ɫΪ��ɫ
        }
        log.setBackground(color);  //�ı����ı���ɫ
	}

	public void OpenFile(){
		int n = fc.showOpenDialog(this);
		if(n==0) {
			File file = fc.getSelectedFile(); //ѡ���ļ�
			
			try {
				FileReader fr = new FileReader(file); //�ļ���ȡ
				int ch = 0;
				String str = ""; //�洢����
				while((ch=fr.read())!=-1) {
					str=str+(char)ch;
				}
				fr.close();
				this.setTitle(file.getName()); //��ȡ���±��ı���
				log.setText(str);//��������ӵ����±���
				flag = false ; //���ļ�,��ʶ���ã�ʾ���ı�û�иĶ�
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else {
			//log.append("��ѡ����ȡ��!\n");
		}
		log.setCaretPosition(log.getDocument().getLength());
	}
	
	public void SaveAs() {
		int n = fc.showSaveDialog(this);
		if(n==0) {
			File file = fc.getSelectedFile();
			try {
				FileWriter fw = new FileWriter(file); //�ļ�д��
				String str = log.getText();//����ı���
				fw.write(str); //�洢�ַ���
				fw.close();
				flag = false ; //�����ļ�,��ʶ���ã�ʾ���ı�û�иĶ�
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else {
			//log.append("��ѡ����ȡ������!\n");
		}
		log.setCaretPosition(log.getDocument().getLength());
	}
	
	public void Save() {
		File file = new File("C:\\Users\\KLH\\Desktop\\"+getTitle()+" "); //����ı���·��
		if(!file.exists()) {
			SaveAs();  //�ļ����ڣ��������Ϊ
		}else {
			try {
				FileWriter fw = new FileWriter(file); //�ļ�д��
				String str = log.getText();//����ı���
				fw.write(str); //�洢�ַ���
				fw.close();
				flag = false ; //�����ļ�,��ʶ���ã�ʾ���ı�û�иĶ�
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}	
	}
	
	public void NewFile() {
		//�ж��ļ������Ƿ��иĶ�
		if(flag == true) {    
			int n=JOptionPane.showConfirmDialog(null, "�Ƿ񱣴��ļ�!","���±�",JOptionPane.YES_NO_CANCEL_OPTION);
	        if(n==0){  //�� ����0  �񷵻�1 ȡ�� ����2 
	       	 	Save(); //���ǣ��򱣴��ļ�Ȼ����ļ�
	        }else if(n==1){ //���ط�
	        	
	        }else if(n==2){  //����ȡ��
	            return ;
	        }
	        this.setTitle("�ޱ���");//�������
	        log.setText("");//�ı����
	        flag = false ; //��ʶ��Ϊ�ı�û�Ķ�(��ʶ����)
		}else {
			this.setTitle("�ޱ���");//�������
	        log.setText("");//�ı����
	        flag = false ; //��ʶ��Ϊ�ı�û�Ķ�(��ʶ����)
		}
	}

	public void exit() {
		//�ж��ļ������Ƿ��иĶ�
		if(flag == true) {    
			int n=JOptionPane.showConfirmDialog(null, "�Ƿ񱣴��ļ�!","���±�",JOptionPane.YES_NO_CANCEL_OPTION);
	        if(n==0){  //�� ����0  �񷵻�1 ȡ�� ����2 
	       	 	Save(); //���ǣ��򱣴��ļ�Ȼ����ļ�
	        }else if(n==1){ //���ط�
	        	System.exit(0);
	        }else if(n==2){  //����ȡ��
	            return ;
	        }
		}else {
			System.exit(0);
		}
	}
	
	//���
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















