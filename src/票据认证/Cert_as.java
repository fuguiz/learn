package 票据认证;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import DES解密.DesDecry;
import 小方法.MD5;
import 小方法.MessageTran;

public class Cert_as {

	private static String IP;
	private static int port;
	private static String key_c;
	private static int id_tgs ;
	
	private DataOutputStream out = null;//输入流对象
	private DataInputStream in= null;
	
	private static Socket client;
	
	public Cert_as(String IP,int port)
	{
		this.IP=IP;
		this.port=port;
	}
	public boolean login_as(int id_c,int id_t,String time_c,String key) throws IOException
	{
		this.key_c=key;
		this.id_tgs=id_t;
		String send=Integer.toString(id_c)+" "+Integer.toString(id_t)+" "+time_c;
		
		return cert_as(send);
	}
	public boolean cert_as(String send) throws IOException
	{
		 boolean result=false;
		try { 
				client = new Socket(IP, port); 
			    // 得到输入输出流对象  
			    in=new DataInputStream(client.getInputStream());  //输入
			    out=new DataOutputStream(client.getOutputStream()); //输出
			    
			    byte[] buffer=new byte[1024];
		        int len=0;
			   
			    byte[] dataTran=sendMessage(send);	//发送数据
				out.write(dataTran);
				out.flush();
				/*
				 * 接收结果
				 */
				byte cmd=in.readByte();
				long fileLength = in.readLong();
				System.out.println(fileLength);
				if(cmd==0x02)
				{
					//System.out.println("as初始认证成功");
					FileOutputStream fos = null;
					try {  
			            File file = new File("./dataFile/client_receive_as/key.txt");
			            fos = new FileOutputStream(file);
			            byte[] bytes = new byte[1024];  
		                int length = 0;  
		                while((length = in.read(bytes, 0, bytes.length)) != -1) {  
		                    fos.write(bytes, 0, length);  
		                    fos.flush();  
		                }  
			            
					}catch (Exception e) {  
		                e.printStackTrace();  
		            } finally {
		            	if(fos != null)  
	                        fos.close();
		            }
					
					result=analy();
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
				if(in!=null){
					in.close();
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
	private static byte[] sendMessage(String data) {
		byte cmd=0x01;
		if(data.getBytes().length>256)
		{
			return null;
		}
		MessageTran m=new MessageTran(cmd,data.getBytes());	
		
		return m.getDataTran();
	}
	public static boolean  analy() throws IOException  //解析接收的文件并判断认证是否成功
	{
		boolean result=false;
		 try{
			 String data=DesDecry.desDecry(read_file(), key_c);
				
				String[] Str=data.split(",");
				
				if(id_tgs==Integer.parseInt(Str[1]))
				{
					save_certificate(Str[0],Str[1],Str[2],Str[3],Str[4]);
					result=true;
				}
				else
				{
					result=false;
				}
				return result;
		 }catch (Exception e){
	            return false;
		 }
		
		
	}
	public static String read_file() throws IOException    //获取文件中的数据；
	{
		StringBuffer buffer = new StringBuffer();
        BufferedReader bf= new BufferedReader(new FileReader("./dataFile/client_receive_as/key.txt"));
        String s = null;
        while((s = bf.readLine())!=null){//使用readLine方法，一次读一行
            buffer.append(s.trim());
        }

        return  buffer.toString();
	}
	/*
	 * 保存票据
	 */
	public static void save_certificate(String key,String ID_tgs,String time,String lifetime,String Ticket) throws IOException
	{
		FileWriter fw = new FileWriter("./dataFile/client_certificate_as/certificate.txt");
		String a="目的方是： "+ID_tgs+"\n";
	    fw.write(a,0,a.length());   
	    String b="发布时间是： " +time+"\n";  
	    fw.write(b,0,b.length());
	    String c="票据是： "+Ticket+"\n";
	    fw.write(c,0,c.length());
	    String d="秘钥是： "+key+"\n";
	    fw.write(d,0,d.length());
	    String e="有效期是： "+lifetime+" 毫秒";
	    fw.write(e,0,e.length());
	    fw.flush();
	}
	/*public static void main(String[] args) {
		Cert_as cert=new Cert_as("localhost",6668);
		//cert.login_as(1002, id_t, time_c)

	}*/

}
