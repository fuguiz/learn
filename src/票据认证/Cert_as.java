package Ʊ����֤;

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

import DES����.DesDecry;
import С����.MD5;
import С����.MessageTran;

public class Cert_as {

	private static String IP;
	private static int port;
	private static String key_c;
	private static int id_tgs ;
	
	private DataOutputStream out = null;//����������
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
			    // �õ��������������  
			    in=new DataInputStream(client.getInputStream());  //����
			    out=new DataOutputStream(client.getOutputStream()); //���
			    
			    byte[] buffer=new byte[1024];
		        int len=0;
			   
			    byte[] dataTran=sendMessage(send);	//��������
				out.write(dataTran);
				out.flush();
				/*
				 * ���ս��
				 */
				byte cmd=in.readByte();
				long fileLength = in.readLong();
				System.out.println(fileLength);
				if(cmd==0x02)
				{
					//System.out.println("as��ʼ��֤�ɹ�");
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
					System.out.println("��֤ʧ��");
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
	public static boolean  analy() throws IOException  //�������յ��ļ����ж���֤�Ƿ�ɹ�
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
	public static String read_file() throws IOException    //��ȡ�ļ��е����ݣ�
	{
		StringBuffer buffer = new StringBuffer();
        BufferedReader bf= new BufferedReader(new FileReader("./dataFile/client_receive_as/key.txt"));
        String s = null;
        while((s = bf.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
            buffer.append(s.trim());
        }

        return  buffer.toString();
	}
	/*
	 * ����Ʊ��
	 */
	public static void save_certificate(String key,String ID_tgs,String time,String lifetime,String Ticket) throws IOException
	{
		FileWriter fw = new FileWriter("./dataFile/client_certificate_as/certificate.txt");
		String a="Ŀ�ķ��ǣ� "+ID_tgs+"\n";
	    fw.write(a,0,a.length());   
	    String b="����ʱ���ǣ� " +time+"\n";  
	    fw.write(b,0,b.length());
	    String c="Ʊ���ǣ� "+Ticket+"\n";
	    fw.write(c,0,c.length());
	    String d="��Կ�ǣ� "+key+"\n";
	    fw.write(d,0,d.length());
	    String e="��Ч���ǣ� "+lifetime+" ����";
	    fw.write(e,0,e.length());
	    fw.flush();
	}
	/*public static void main(String[] args) {
		Cert_as cert=new Cert_as("localhost",6668);
		//cert.login_as(1002, id_t, time_c)

	}*/

}
