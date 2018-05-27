package QQ_TGS;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import DES����.DesEncry;
import DES����.DesDecry;
import С����.MD5;
import С����.date_long;
import С����.get_new_key;
import С����.get_time;
import ���ݿ�����.Mysql_Driver;

/*
 * �������ݰ����ݣ���������Ӧ�Ĵ���
 */
public class Packet_analy {
	private static final int ID_V=8888;
	private static final int ID_TGS=7777;
	private static final int lifetime=60000;
	static final String Key_tgs="abcdefgh";
	static final String Key_v="ijklmopq";
	
	static int id_c;
	static String AD_C=null;
	public static void packet_analy(DataInputStream in,Socket client) throws IOException
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
	            File file = new File("./dataFile/tgs_receive_client/data.txt");
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
				saveAsFileWriter(DesEncry.desEncey(analy(),get_key_c_tgs(id_c).substring(2,10)));
			}
		}
	}
	public static String read_file() throws IOException    //��ȡ�ļ��е����ݣ�
	{
		StringBuffer buffer = new StringBuffer();
        BufferedReader bf= new BufferedReader(new FileReader("./dataFile/tgs_receive_client/data.txt"));
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
		int id_v=Integer.parseInt(Str[0]);
		String ticket=Str[1];
		String auther=Str[2];
		
		if(id_v==ID_V)
		{
			/*
			 * ���� ticket����
			 */
			String ticket_clear=DesDecry.desDecry(ticket,Key_tgs);
			String[] Str_tick=ticket_clear.split(",");
			
		    id_c=Integer.parseInt(Str_tick[1]); //���浱ǰclient��ID��
		    
			for(int i=0;i<Str_tick.length;i++)
			{
				System.out.println(Str_tick[i]);
			}
			/*
			 * ����auther����
			 */
			String auther_clear=DesDecry.desDecry(auther,get_key_c_tgs(id_c).substring(2,10));  //h��ȡclient��ID�ŵõ���Ӧ��key_c_tgs����Կ��Ȼ�����
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
				String tick_v_clear=get_key_c_tgs(id_c)+","+Integer.toString(id_c)+","+AD_C+","+Integer.toString(ID_V)+","+get_time.getDate()+","+Integer.toString(lifetime)+",";
				String tick_v=DesEncry.desEncey(tick_v_clear,Key_v);
				System.out.println("ticket_v====="+tick_v);
				
				result=new_key(id_c)+","+Integer.toString(ID_V)+","+get_time.getDate()+","+tick_v;
			}
			else
				result=null;
		}
		return result;
	}
	public static String get_key_c_tgs(int id_c)
	{
		String key = null;
		try
		{
			Connection conn=Mysql_Driver.link();
			Statement stmt = conn.createStatement();
			String sql="select ID,K_c_tgs from tgs_data where ID = "+ id_c +";";
		 	ResultSet rs = stmt.executeQuery(sql);  	
		 	while(rs.next()){  
		 	  key=rs.getString("K_c_tgs");
		 	  }
	        //System.out.println("�������ݳɹ�3");
	        
	        stmt.close();
	        conn.close();
	 	}catch (Exception e) {  
	 		System.out.print("���ݻ�ȡ����3!");   
	          e.printStackTrace(); }
		return key;
	}
	public static String new_key(int id)
	{
		/*
		 * ���Ȳ����µ���Կ��Ȼ��ͨ��md5���ܱ��浽���ݿ⣬
		 * ��ʹ�õ�ʱ��ͨ���ض��ĺ������ܣ�����ȡ���еĶλ��Ϊ��Կ
		 */
		String new_key_c_tgs=MD5.getMD5(get_new_key.Key_Producer()); //
		try
		{
			Connection conn=Mysql_Driver.link();
			
			String sql3="update tgs_data set K_tgs_v ='"+ new_key_c_tgs +"' where ID = " +id;
	 		
	 		Statement stmt = conn.createStatement();
	 		stmt.executeUpdate(sql3);
	        //System.out.println("�������ݳɹ�3");
	        
	        stmt.close();
	        conn.close();
	 	}catch (Exception e) {  
	 		System.out.print("���ݻ�ȡ����3!");   
	          e.printStackTrace(); }
		return new_key_c_tgs;
	}
	private static void saveAsFileWriter(String content) {  
		  
		 FileWriter fwriter = null;  
		 try {  
		  fwriter = new FileWriter("./dataFile/tgs_send_client/key.txt");  
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
