package test_1;

import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

//�̳г�����MouseAdapter
public class MyMouseAdapter extends MouseAdapter{
	private Graphics g;
	StringBuilder  num=new StringBuilder("");
	private String command;
	private int x1,x2,y1,y2;
	private int flag = 0;	
	DrawingFrame df;	
	ArrayList a=new ArrayList();
	ArrayList b=new ArrayList();
	//���췽��������DrawingFrame����
	
	public MyMouseAdapter(DrawingFrame df){
		this.df = df; 				   
		//��������
		g = df.getGraphics();
		//df.setSize(100,100);
	}

	//��갴��
	public void mousePressed(MouseEvent e) {
		//�õ���갴��ʱ������
		x1 = e.getX();
		y1 = e.getY();
		flag = 0;			//ʵ��Ǧ��ʹ�õı�־λ	
	}
	//����ͷ�
	public void mouseReleased(MouseEvent e) {
		
		//�õ�����ͷ�ʱ������		
		x2 = e.getX();
		y2 = e.getY();	
		flag = 0;			//ʵ��Ǧ��ʹ�õı�־λ	
		//��ȡ��ťֵ
		this.command = df.command;	
	}			
	//�����ק����갴�²��ƶ�ʱ�����ϴ�����ֱ������ͷţ�
	public void mouseDragged(MouseEvent e){
		//��ȡ��ťֵ
		
		this.command = df.command;
		if(flag == 0){			
			x1 = e.getX();
			y1 = e.getY();
			a.add(x1);
			b.add(500-y1);
			num.append(x1+","+(500-y1)+"\n");
			//fileWriter.write("("+x1+","+y1+")");
			//bufw.write("("+x1+","+y1+")");
			//bufw.newLine();
		}else{
			//��������
			x2 = e.getX();
			y2 = e.getY();
			a.add(x2);
			b.add(500-y2);
			num.append(x2+","+(500-y2)+"\n");
			
			g.drawLine(x1, y1, x2, y2);	
			
			x1 = x2;
			y1 = y2;
			
		}
		flag = 1;	 //�ı��־λ���´δ���ʱ��ʾ�϶������ǵ�� 	  
		File file=new File("text.txt");  
		  //д  
        try {  
        
        	FileWriter out= new FileWriter(file);
        
        String s1=num.toString();
        	out.write(s1);
        	out.close();
			//bufw.close();  
           // fw.close();  
            //fos.close();  
        } catch (FileNotFoundException ee) {  
            // TODO Auto-generated catch block  
            ee.printStackTrace();  
        } catch (UnsupportedEncodingException ee) {  
            // TODO Auto-generated catch block  
            ee.printStackTrace();  
        } catch (IOException ee) {  
            // TODO Auto-generated catch block  
            ee.printStackTrace();  
        } 

	  
	  for (int i=0; i<a.size(); i++) {  
		     System.out.print("("+a.get(i)+","+b.get(i)+")"); 
		     if(i%15==0)
		    	 {System.out.println();
		    	 }
		 }
	  System.out.println("*********************************");
 
	}	
}