package QQ_client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import С����.MD5;
import С����.MessageTran;
import С����.get_time;

public class logon {
	private static String name=null;
	private static String password=null;
	private static String sex=null;
	private static String birthday=null;
	private static String mail=null;
	private static String school=null;
	private static String constellation=null;
	private static String date=get_time.getDate();
	
	public static String LOGIN_ID;
	
	private DataOutputStream out = null;//����������
	private DataInputStream in= null;
	
	private static Socket client_message;
	private static Socket client_picture;
	
	public logon (String name,String password,String sex,String birthday,String mail,String school,String constellation)
	{
		logon.name=name;
		logon.password=MD5.getMD5(password);
		logon.sex=sex;
		logon.birthday=birthday;
		logon.mail=mail;
		logon.school=school;
		logon.constellation=constellation;
	}
	
	public void Logon_message(String ip,int port) throws IOException
	{
		try {  
		 
			 client_message = new Socket(ip, port); 
			
		    // �õ��������������  
		    in=new DataInputStream(client_message.getInputStream());
		    out=new DataOutputStream(client_message.getOutputStream());
		    /*
			 * ����ע����Ϣ����
			 */
		    String user=name+" "+sex+" "+birthday+" "+mail+" "+school+" "+constellation+" "+date+" "+password;
			
		    byte[] dataTran=sendMessage(user);	
			out.write(dataTran);
			out.flush();
		
			/*
			 * ����ע�����
			 */
			System.out.println("ע��ɹ�");
			packet_message(in,client_message);		
		    
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
				if(client_message!=null){
					client_message.close();
				}
		}
	}
	/*
	 * �����յ��ı�������
	 */
	public void Logon_picture(String ip,int port,int ID,String path) throws IOException
	{
		try {   
			 client_picture = new Socket(ip, port);	
		    // �õ��������������  
		    in=new DataInputStream(client_picture.getInputStream());
		    out=new DataOutputStream(client_picture.getOutputStream());
		    /*
			 * ����ͷ��·��
			 */ 	
		    String send=Integer.toString(ID)+" "+path;
		    byte[] send_byte=send.getBytes();//�õ���Ϣ���ֽ���
		    out.write(send_byte);
		    out.flush();
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
				if(client_picture!=null){
					client_picture.close();
				}
		}
    }
	public static void packet_message(DataInputStream dins,Socket client) throws IOException
	{
		
		byte[] buffer=new byte[1024];
        int len=0;
		while((len=dins.read(buffer))!=-1)
		{
			byte[] dataTran=Arrays.copyOfRange(buffer, 0, len);
			if(dataTran[0]==0x01)
			{
				byte[] data=Arrays.copyOfRange(dataTran, 2, dataTran.length);
				String s=new String(data);
				//System.out.println("ע��ɹ�"+s);
				String[] Str=s.split(" ");
					
				for(int i=0;i<Str.length;i++)
				{
					LOGIN_ID=Str[i];
					//System.out.println("ע��õ���ID����"+Str[i]);
				}
				System.out.println("ע��õ���ID����"+LOGIN_ID);
			}
			else
			{
				System.out.println("û�з�͵����ݰ�");
				client.close();
			}
			break;
		}
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
	
	
	 public static byte[] intToByteArray(int i) {     
         byte[] result = new byte[4];    
         //�ɸ�λ����λ  
         result[0] = (byte)((i >> 24) & 0xFF);  
         result[1] = (byte)((i >> 16) & 0xFF);  
         result[2] = (byte)((i >> 8) & 0xFF);  
         result[3] = (byte)(i & 0xFF);  
         return result;  
   }  
 
	public static void main(String[] args) throws IOException {
		String name="�Ÿ���";
		String password="hfhgfhgghfhgfhgfh";
		String sex="��";
		String birthday="1996.05.10";
		String mail="fuguiz@qq.com";
		String school="�й����ʴ�ѧ���人��";
		String constellation="ˮƿ��";
		
		logon zc=new logon(name,password,sex,birthday,mail,school,constellation);
		String path="C:\\\\Users\\\\sunlight\\\\Pictures\\\\��ֽ\\\\��ֽ2.jpg";
		zc.Logon_message("192.168.1.105",6666);
		//zc.Logon_picture("localhost",6667,10000,path);
	}
  
}
