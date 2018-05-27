package Ʊ����֤;

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

import DES����.DesEncry;
import DES����.DesDecry;


public class Cert_tgs {
	private static String IP;
	private static int port;
	private static int ID_V;
	private static DataOutputStream out = null;//����������
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
			    // �õ�������������� 			    
			    out=new DataOutputStream(client.getOutputStream()); //���
			    saveAsFileWriter(send);  //�����ݴ�����ļ�
			    send_file(out);    //��������
				/*
				 * ���ս��
				 */
			    dins=new DataInputStream(client.getInputStream());  //����
			    byte cmd=dins.readByte();
				long fileLength = dins.readLong();
				System.out.println(fileLength);
				if(cmd==0x05)
				{
					System.out.println("TGS��֤�ɹ�");
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
					System.out.println("��֤ʧ��");
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
                // �ļ����ͳ��� 
            	byte cmd=0x04;
                dos.write(cmd);  
                dos.flush();  
                
                dos.writeLong(file.length());  
                dos.flush();  
  
                // ��ʼ�����ļ�  
                System.out.println("======== client��TGS��ʼ�����ļ� ========");  
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
                System.out.println("======== client��TGS�ļ�����ɹ� ========");  
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
	public static boolean  analy(String k_c_tgs) throws IOException  //�������յ��ļ����ж���֤�Ƿ�ɹ�
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
	public static String read_file() throws IOException    //��ȡ�ļ��е����ݣ�
	{
		StringBuffer buffer = new StringBuffer();
        BufferedReader bf= new BufferedReader(new FileReader("./dataFile/client_receive_tgs/key.txt"));
        String s = null;
        while((s = bf.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
            buffer.append(s.trim());
        }
        return  buffer.toString();
	}
	public static void save_certificate(String key,String ID_V,String time,String Ticket) throws IOException
	{
		FileWriter fw = new FileWriter("./dataFile/client_certificate_tgs/certificate.txt");
		String a="Ŀ�ķ��ǣ� "+ID_V+"\n";
	    fw.write(a,0,a.length());   
	    String b="����ʱ���ǣ� " +time+"\n";  
	    fw.write(b,0,b.length());
	    String c="Ʊ���ǣ� "+Ticket+"\n";
	    fw.write(c,0,c.length());
	    String d="��Կ�ǣ� "+key+"\n";
	    fw.write(d,0,d.length());
	    
	    fw.flush();
	}

}
