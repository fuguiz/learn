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

//继承抽象类MouseAdapter
public class MyMouseAdapter extends MouseAdapter{
	private Graphics g;
	StringBuilder  num=new StringBuilder("");
	private String command;
	private int x1,x2,y1,y2;
	private int flag = 0;	
	DrawingFrame df;	
	ArrayList a=new ArrayList();
	ArrayList b=new ArrayList();
	//构造方法，接收DrawingFrame对象
	
	public MyMouseAdapter(DrawingFrame df){
		this.df = df; 				   
		//创建画布
		g = df.getGraphics();
		//df.setSize(100,100);
	}

	//鼠标按下
	public void mousePressed(MouseEvent e) {
		//得到鼠标按下时的坐标
		x1 = e.getX();
		y1 = e.getY();
		flag = 0;			//实现铅笔使用的标志位	
	}
	//鼠标释放
	public void mouseReleased(MouseEvent e) {
		
		//得到鼠标释放时的坐标		
		x2 = e.getX();
		y2 = e.getY();	
		flag = 0;			//实现铅笔使用的标志位	
		//获取按钮值
		this.command = df.command;	
	}			
	//鼠标拖拽（鼠标按下并移动时，不断触发，直到鼠标释放）
	public void mouseDragged(MouseEvent e){
		//获取按钮值
		
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
			//交换坐标
			x2 = e.getX();
			y2 = e.getY();
			a.add(x2);
			b.add(500-y2);
			num.append(x2+","+(500-y2)+"\n");
			
			g.drawLine(x1, y1, x2, y2);	
			
			x1 = x2;
			y1 = y2;
			
		}
		flag = 1;	 //改变标志位，下次触发时表示拖动而不是点击 	  
		File file=new File("text.txt");  
		  //写  
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