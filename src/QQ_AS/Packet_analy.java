package QQ_AS;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

import DES����.DesEncry;
import С����.MD5;
import С����.get_new_key;
import С����.get_time;
import ���ݿ�����.Mysql_Driver;

/*
 * �������ݰ����ݣ���������Ӧ�Ĵ���
 */
public class Packet_analy {
	public static int lifetime=60000;  //������Ʊ�ݵ���Ч���� 1���ӣ�
	public static final String Key_tgs="abcdefgh";
	static final int ID_TGS=6666;
	public static void packet_analy(DataInputStream dins,Socket client) throws IOException
	{
		String AD=client.getInetAddress().getHostAddress();

		 byte[] buffer=new byte[1024];
         int len=0;
			while((len=dins.read(buffer))!=-1){
				byte[] dataTran=Arrays.copyOfRange(buffer, 0, len);
				if(dataTran[0]==0x01)
				{
					byte[] data=Arrays.copyOfRange(dataTran, 2, dataTran.length);
					String s=new String(data);
					String[] Str=s.split(" ");
					
					int id_c=Integer.parseInt(Str[0]);
					int id_tgs=Integer.parseInt(Str[1]);
					long time_c=Long.parseLong(Str[2]);
					System.out.println(id_c);
					System.out.println(id_tgs);
					System.out.println(time_c);
					
					String key=Judge(id_c,id_tgs,time_c); //�õ�AS��client�Ĺ�����Կ
					System.out.println(key);
					
					if(key!=null)
					{
						String new_key_tgs=new_key(id_c);
						String new_time=get_time.getDate();
						System.out.println(AD);
						String ticket=new_key_tgs+","+Integer.toString(id_c)+","+AD+","+Integer.toString(id_tgs)+","+new_time+","+Integer.toString(lifetime)+",";
						
						String tick=DesEncry.desEncey(ticket,Key_tgs);

						String clear_res=new_key_tgs+","+Integer.toString(id_tgs)+","+new_time+","+Integer.toString(lifetime)+","+tick+",";
						saveAsFileWriter(DesEncry.desEncey(clear_res,key));  //��������浽�ļ���
						//System.out.println(result);
					}
				}
				else
				{
					System.out.println("�յ�δ֪���ݰ���");  
					    //client.close();  
				} 
				break;
			}
	}
	public static String get_key(int id_c)   //�����ݿ��л�ȡkey
	{
		String key = null;
		try
		{
			Connection conn=Mysql_Driver.link();
			Statement stmt = conn.createStatement();
			String sql="select ID,K_c_as from as_data where ID = "+ id_c +";";
		 	 
		 	ResultSet rs = stmt.executeQuery(sql); 
		 	
		 	while(rs.next()){  
		 	  //name= rs.getString("username");
		 	  key=rs.getString("K_c_as");
		 	  }
	 		
	        System.out.println("�������ݳɹ�3");
	        
	        stmt.close();
	        conn.close();
	 	}catch (Exception e) {  
	 		System.out.print("���ݻ�ȡ����3!");   
	          e.printStackTrace(); }
		return key;
	}
	public static String Judge(int id_c,int id_tgs,long time_c)   //���������жϣ���ȡkey��
	{
		String key=get_key(id_c);
		if(key==null||(Long.parseLong(get_time.getTime())-time_c)>100000)
			return null;
		return key.substring(2,10);	
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
			
			String sql="select ID,K_c_tgs from tgs_data where ID = "+ id +";";
			String sql2="insert into tgs_data(ID,K_c_tgs) values("+ id +",'"+new_key_c_tgs+"')";
			String sql3="update tgs_data set K_c_tgs ='"+ new_key_c_tgs +"' where ID = " +id;
	 		
	 		Statement stmt = conn.createStatement();
	 		String K_tgs=null;
	 		ResultSet rs = stmt.executeQuery(sql); 
	 		while(rs.next()){  
	 			K_tgs=rs.getString("K_c_tgs");
			 	  }
	 		if(K_tgs==null)
	 		{
	 			System.out.println("��¼������");
	 			stmt.executeUpdate(sql2);
	 		}
	 		else
	 		{
	 			System.out.println("��¼����"+K_tgs);
	 			stmt.executeUpdate(sql3);
	 		}
		 	
	        System.out.println("�������ݳɹ�3");
	        
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
		  fwriter = new FileWriter("./dataFile/as_send_client/key.txt");  
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
