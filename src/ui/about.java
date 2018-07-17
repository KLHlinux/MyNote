package ui;

import javax.swing.JFrame;
import java.awt.Window.Type;
import javax.swing.JLabel;

public class about extends JFrame{
	
	public about() {
		setType(Type.UTILITY);
		setResizable(false);
		setTitle("\u5173\u4E8E\"\u8BB0\u4E8B\u672C\"");
		setBounds(100, 100, 411, 268);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("作者      孔令亨");
		label.setBounds(136, 86, 132, 15);
		getContentPane().add(label);
		setLocationRelativeTo(null);
		setVisible(true);//可见
	}

	private static final long serialVersionUID = 1L;
}
