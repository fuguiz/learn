package test_1;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class DrawingFrame extends JFrame{
	
	//Ĭ�ϻ���Ϊ"Ǧ��"
	public String command = "Ǧ��";
	//��������
	public void showUI(){
		
		this.setLayout(new FlowLayout(0));
	
		//��ť�������ڲ��ࣩ
		ActionListener action_listener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
						command = e.getActionCommand();					
			}			
		};			
	
		//���ô���
		this.setSize(500, 500);
		this.setTitle("��ͼ��");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(3);
		//���ô��ڿɼ�
		this.setVisible(true);
		this.setResizable(false);
		
		//��궯�������� & ��ק��
		MouseAdapter ma = new MyMouseAdapter(this);
		this.addMouseListener(ma);
		this.addMouseMotionListener(ma);
	
		
							
	}		
}

