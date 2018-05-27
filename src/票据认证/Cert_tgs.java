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

import DES加密.DesEncry;
import DES解密.DesDecry;


public class Cert_tgs {
	private static String IP;
	private static int port;
	private static int ID_V;
	private static DataOutputStream out = null;//输入流对象
	private static DataInputStream dins;
	
	private static Socket client;
	
	public Cert_tgs(String IP,int port)
	{
		Cert_tgs.IP=IP;
		Cert_tgs.port=port;
	}
	public static boolean login_tgs(int id_v,String ticket,String auther_clear,String k_c_tgs) throws IOException
	{
		ID_V=id_v;
		String auther=DesEncry.desEncey(auther_clear,k_c_tgs);
		String send=Integer.toString(id_v)+","+ticket+","+auther+",";
		socket_link(send);
		return analy(k_c_tgs);
	}
	public static void socket_link(String send) throws IOException
	{
		 //boolean result=false;
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
				long fileLength = dins.readLong();
				System.out.println(fileLength);
				if(cmd==0x05)
				{
					System.out.println("TGS认证成功");
					FileOutputStream foos = null;
					try {  
			            File file = new File("./dataFile/client_receive_tgs/key.txt");
			            foos = new FileOutputStream(file);
			            byte[] bytes = new byte[1024];  
		                int length = 0;  
		                while((length = dins.read(bytes, 0, bytes.length)) != -1) {  
		                    foos.write(bytes, 0, length);  
		                    foos.flush();  
		                }  
			            
					}catch (Exception e) {  
		                e.printStackTrace();  
		            } finally {
		            	if(foos != null)  
	                        foos.close();
		            }	
				}
				else
				{
					System.out.println("认证失败");
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
		//return result;
	}

	public static void send_file(DataOutputStream dos) throws IOException
	{
		FileInputStream fis = null;
		try {  
            File file = new File("./dataFile/client_send_tgs/data.txt");  
            if(file.exists()) {  
            	fis= new FileInputStream(file); 
                // 文件名和长度 
            	byte cmd=0x04;
                dos.write(cmd);  
                dos.flush();  
                
                dos.writeLong(file.length());  
                dos.flush();  
  
                // 开始传输文件  
                System.out.println("======== client向TGS开始传输文件 ========");  
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
                System.out.println("======== client向TGS文件传输成功 ========");  
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
		  fwriter = new FileWriter("./dataFile/client_send_tgs/data.txt");  
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
	public static boolean  analy(String k_c_tgs) throws IOException  //解析接收的文件并判断认证是否成功
	{
		boolean result=false;
		try{
			System.out.println(k_c_tgs);
			
			String data=DesDecry.desDecry(read_file(),k_c_tgs);
			//String data=DesDecry.desDecry(read_file(), key_c);
			
			String[] Str=data.split(",");
			for(int i=0;i<Str.length-1;i++)
			{
				System.out.println(Str[i]);
			}
			if(ID_V==Integer.parseInt(Str[1]))
			{
				save_certificate(Str[0],Str[1],Str[2],Str[3]);
				result=true;
			}
			return result;
		}catch (Exception e){
            return false;}
		
	}
	public static String read_file() throws IOException    //获取文件中的数据；
	{
		StringBuffer buffer = new StringBuffer();
        BufferedReader bf= new BufferedReader(new FileReader("./dataFile/client_receive_tgs/key.txt"));
        String s = null;
        while((s = bf.readLine())!=null){//使用readLine方法，一次读一行
            buffer.append(s.trim());
        }
        return  buffer.toString();
	}
	public static void save_certificate(String key,String ID_V,String time,String Ticket) throws IOException
	{
		FileWriter fw = new FileWriter("./dataFile/client_certificate_tgs/certificate.txt");
		String a="目的方是： "+ID_V+"\n";
	    fw.write(a,0,a.length());   
	    String b="发布时间是： " +time+"\n";  
	    fw.write(b,0,b.length());
	    String c="票据是： "+Ticket+"\n";
	    fw.write(c,0,c.length());
	    String d="秘钥是： "+key+"\n";
	    fw.write(d,0,d.length());
	    
	    fw.flush();
	}

}
