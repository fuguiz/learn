package test_1;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class DrawingFrame extends JFrame{
	
	//默认画笔为"铅笔"
	public String command = "铅笔";
	//创建窗口
	public void showUI(){
		
		this.setLayout(new FlowLayout(0));
	
		//按钮侦听（内部类）
		ActionListener action_listener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
						command = e.getActionCommand();					
			}			
		};			
	
		//设置窗口
		this.setSize(500, 500);
		this.setTitle("画图板");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(3);
		//设置窗口可见
		this.setVisible(true);
		this.setResizable(false);
		
		//鼠标动作（侦听 & 拖拽）
		MouseAdapter ma = new MyMouseAdapter(this);
		this.addMouseListener(ma);
		this.addMouseMotionListener(ma);
	
		
							
	}		
}

