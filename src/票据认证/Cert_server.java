package 票据认证;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import DES加密.DesEncry;
import DES解密.DesDecry;
import 小方法.get_time;

public class Cert_server {
	private static String IP;
	private static int port;
	private static DataOutputStream out = null;//输入流对象
	private static DataInputStream dins;
	
	private static Socket client;
	
	public Cert_server(String IP,int port)
	{
		Cert_server.IP=IP;
		Cert_server.port=port;
	}
	public static boolean login_ser(String ticket,String authen_clear,String k_c_ser) throws IOException
	{
		String auther=DesEncry.desEncey(authen_clear,k_c_ser);
		String send=ticket+","+auther+",";
		try{
			
			return socket_link(send);
			
		}catch (Exception e){
            return false;}
		
	}
	public static boolean socket_link(String send) throws IOException
	{
		 boolean result=false;
		try { 
				client = new Socket(IP, port); 
			    // 得到输入输出流对象 			    
			    out=new DataOutputStream(client.getOutputStream()); //输出
			    saveAsFileWriter(send);  //将数据打包成文件
			    send_file(out);    //发送数据
				/*
				 * 接收结果
				 */
			    dins=new DataInputStream(client.getInputStream());  //输入
			    
			    byte cmd=dins.readByte();
			    byte rec_buf[]=new byte[1024];
			    if(cmd==0x08)
			    {
			    	dins.read(rec_buf);
			    	System.out.println("获得server认证结果");
			    	result=true;
			    }
			    if(cmd==0x09)
			    {
			    	System.out.println("失败");
			    }
				
			   
						
				
		    }  
		    catch (IOException e) {    
		        e.printStackTrace();  
		    }
			finally{
				if(dins!=null){
					dins.close();
				}
				if(out!=null){
					out.close();
				}
				if(client!=null){
					client.close();
				}
			}
		return result;
	}

	public static void send_file(DataOutputStream dos) throws IOException
	{
		FileInputStream fis = null;
		try {  
            File file = new File("./dataFile/client_send_ser/data.txt");  
            if(file.exists()) {  
            	fis= new FileInputStream(file); 
                // 文件名和长度 
            	byte cmd=0x04;
                dos.write(cmd);  
                dos.flush();  
                
                dos.writeLong(file.length());  
                dos.flush();  
  
                // 开始传输文件  
                System.out.println("======== client向server开始传输文件 ========");  
                byte[] bytes = new byte[1024];  
                int length = 0;  
                long progress = 0;  
                while((length = fis.read(bytes, 0, bytes.length)) != -1) {  
                    dos.write(bytes, 0, length);  
                    dos.flush();  
                    progress += length;  
                    System.out.print("| " + (100*progress/file.length()) + "% |");  
                }  
                System.out.println();  
                System.out.println("======== client向server文件传输成功 ========");  
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally {  
            if(fis != null)  
                fis.close();
        }  
    }
	
	private static void saveAsFileWriter(String content) {  
		  
		 FileWriter fwriter = null;  
		 try {  
		  fwriter = new FileWriter("./dataFile/client_send_ser/data.txt");  
		  fwriter.write(content);  
		 } catch (IOException ex) {  
		  ex.printStackTrace();  
		 } finally {  
		  try {  
		   fwriter.flush();  
		   fwriter.close();  
		  } catch (IOException ex) {  
		   ex.printStackTrace();  
		  }  
		 }
	}

}

