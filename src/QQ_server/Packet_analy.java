package QQ_server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import DES����.DesEncry;
import DES����.DesDecry;
import С����.date_long;
import ���ݿ�����.Mysql_Driver;

/*
 * �������ݰ����ݣ���������Ӧ�Ĵ���
 */
public class Packet_analy {
	private static final int ID_V=8888;
	private static final int ID_TGS=7777;
	static final String Key_tgs="abcdefgh";
	static final String Key_v="ijklmopq";
	
	static int id_c;    //client��ID��
	static String AD_C=null;  //client��IP��ַ
	public static String packet_analy(DataInputStream in,Socket client) throws IOException
	{
		AD_C=client.getInetAddress().getHostAddress();
		byte cmd=in.readByte();
		long fileLength = in.readLong();
		System.out.println(fileLength);
		if(cmd==0x04)
		{
			System.out.println("�յ�client���ݳɹ�");
			FileOutputStream fos = null;
			try {  
	            File file = new File("./dataFile/ser_receive_client/data.txt");
	            fos = new FileOutputStream(file);
	            byte[] bytes = new byte[2048];  
                int length = 0;  
                while((length = in.read(bytes, 0, bytes.length)) != -1) {  
                    fos.write(bytes, 0, length);  
                    fos.flush();  
                    break;
                }  
	            
			}catch (Exception e) {  
                e.printStackTrace();  
            } finally {
            	if(fos != null)  
                    fos.close();
            }
			/*
			 * ����analyִ�к������ã�Ȼ�󽫷��ص����ݼ��ܣ�
			 */
			if(analy()!=null)
			{
				return DesEncry.desEncey(analy(),get_key_c_ser(id_c).substring(2,10));
			}
		}
		return null;
	}
	public static String read_file() throws IOException    //��ȡ�ļ��е����ݣ�
	{
		StringBuffer buffer = new StringBuffer();
        BufferedReader bf= new BufferedReader(new FileReader("./dataFile/ser_receive_client/data.txt"));
        String s = null;
        while((s = bf.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
            buffer.append(s.trim());
        }

        return  buffer.toString();
	}
	
	public static String analy() throws IOException
	{
		String result="";
		String[] Str=read_file().split(",");  //ͨ����ȡ�ļ��е����ݲ�ͨ���������ָȻ���ȡ��Ӧ������
		String ticket=Str[0];
		String authen=Str[1];
		//System.out.println("ticket=="+ticket);
		//System.out.println("authen=="+authen);
		/*
		 * ���� ticket����
		 */
		String ticket_clear=DesDecry.desDecry(ticket,Key_v);
		//System.out.println("ticket_clear=="+ticket_clear);
		String[] Str_tick=ticket_clear.split(",");
		
	    id_c=Integer.parseInt(Str_tick[1]); //���浱ǰclient��ID��
	    
		for(int i=0;i<Str_tick.length;i++)
		{
			System.out.println(Str_tick[i]);
		}
		/*
		 * ����auther����
		 */
		String auther_clear=DesDecry.desDecry(authen,get_key_c_ser(id_c).substring(2,10));  //h��ȡclient��ID�ŵõ���Ӧ��key_c_tgs����Կ��Ȼ�����
		String[] Str_auth=auther_clear.split(",");
		for(int i=0;i<Str_auth.length;i++)
		{
			System.out.println(Str_auth[i]);
		}
		/*
		 * �ж�client��as��ȡ��Ʊ���Ƿ����
		 */
		
		if((Long.parseLong(Str_auth[2])-date_long.convertTimeToLong(Str_tick[4]))<=Integer.parseInt(Str_tick[5]))
		{
				
			result=Long.toString(Long.parseLong(Str_auth[2])+1);    //��client���͹�����ʱ���1��������֤
		}
		else
			result=null;
			
		return result;
	}
	public static String get_key_c_ser(int id_c)  //ͨ��client��ID�ŵõ�tgs��server�������Կ
	{
		String key = null;
		try
		{
			Connection conn=Mysql_Driver.link();
			Statement stmt = conn.createStatement();
			String sql="select ID,K_tgs_v from tgs_data where ID = "+ id_c +";";
		 	ResultSet rs = stmt.executeQuery(sql);  	
		 	while(rs.next()){  
		 	  key=rs.getString("K_tgs_v");
		 	  }
	        //System.out.println("�������ݳɹ�3");
	        
	        stmt.close();
	        conn.close();
	 	}catch (Exception e) {  
	 		System.out.print("���ݻ�ȡ����3!");   
	          e.printStackTrace(); }
		return key;
	}
	

}
