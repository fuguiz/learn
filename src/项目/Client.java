package 项目;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client {
	static Socket server;

	public static void main(String[] args) throws Exception {
		//server = new Socket("192.168.1.104",5677);
		server = new Socket("192.168.1.105",5678);
		
		PrintWriter out = new PrintWriter(server.getOutputStream());

		Scanner sb=new Scanner(System.in);
		String str;
		char buf[];
		
		while (true) {
			
			str = sb.next();
			buf=str.toCharArray();
			out.write(buf);
			out.flush();
			if(str.equals("1245"))
			{
				InputStream input = server.getInputStream(); 
				BufferedInputStream bin = new BufferedInputStream(input);
				FileOutputStream fos = new FileOutputStream("/home/sunlight/桌面/sdf.jpg");
				
				byte[] buff_picture = new byte[200];  
		        int len; 
  
		        long startTime = System.currentTimeMillis();
		        long endTime;
				while ((len = bin.read(buff_picture)) != -1)  
		        {  		
		            fos.write(buff_picture,0,len);
		            System.out.println(len);
		            endTime=System.currentTimeMillis();
		           if(len<200)
		            	break;
		        }
			
			System.out.println("图片接收完成");
				fos.close();
				//input.close();
			}
			if (str.equals("end")) {
				break;
			}
		}
		server.close();
	}
}
