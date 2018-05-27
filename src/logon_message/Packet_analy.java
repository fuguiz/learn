package logon_message;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

import ���ݿ�����.Mysql_Driver;

/*
 * �������ݰ����ݣ���������Ӧ�Ĵ���
 */
public class Packet_analy {
	 static int ID;
	 static String name=null;
	 static String password=null;
	 static String sex=null;
	 static String birthday=null;
	 static String mail=null;
	 static String school=null;
	 static String constellation=null;;
	 static String date=null;
	 static String picture="./ͷ��.jpg";
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
				String[] Str=s.split(" ");
					
				name=Str[0];
				sex=Str[1];
				birthday=Str[2];
				mail=Str[3];
				school=Str[4];
				constellation=Str[5];
				date=Str[6];
				password=Str[7];
				
				for(int i=0;i<Str.length;i++)
				{
					System.out.println(Str[i]);
				}
			
				 int id=ID_made();
				 ID=id;
				System.out.println(id);
			save_key(id); //����key��AS���ݿ�
			save_mysql(id);    //�������ݵ����ݿ�
			
			break;
				
			}
			else
			{
				System.out.println("û�з�͵����ݰ�");
				client.close();
			}
		}
	}
	public static void save_key(int id)
	{
		try
		{
			Connection conn=Mysql_Driver.link();
			
			String sql="insert into as_data(ID,k_c_as) values("+ id +",'"+password+"')";
	 		
	 		//String sql="insert into as_data (ID,key)"+ " values("+ id +",'"+ password +"')";
	 		Statement stmt = conn.createStatement();
			   // statement����ִ��SQL���
		 	  stmt.executeUpdate(sql);
	        System.out.println("�������ݳɹ�3");
	        
	        stmt.close();
	        conn.close();
	 	}catch (Exception e) {  
	 		System.out.print("���ݻ�ȡ����3!");   
	          e.printStackTrace(); }
	}
	public static void save_mysql(int id)
	{
		
		try
		{
			Connection conn=Mysql_Driver.link();
			
	 		//String sql="insert into user_data(ID,name,sex,birthday,mail,school,constellation,date) values('id','name','sex','birthday','mail','school','constellation','date')";;
	 		String sql="insert into user_data(ID,name,sex,birthday,mail,school,constellation,date,picture) values("+id+",'"+ name +"','"+ sex +"','"+ birthday +"','"+ mail +"','"+ school +"','"+ constellation +"','"+ date +"','"+ picture +"')";
	 		
	 		Statement stmt = conn.createStatement();
			   // statement����ִ��SQL���
		 	  stmt.executeUpdate(sql);
	        System.out.println("�������ݳɹ�2");
	        
	        stmt.close();
	        conn.close();
	 	}catch (Exception e) {  
	 		System.out.print("���ݻ�ȡ����2!");   
	          e.printStackTrace(); }
	}
	public static int ID_made()
	{
		int start=10000;
		PreparedStatement pstmt = null;  
        ResultSet rs = null;  
        int count =0;  
		try
		{
			Connection conn=Mysql_Driver.link();
			Statement stmt=conn.createStatement();//����һ��Statement����
			String sql = "select count(*) as result from user_data";
			
			pstmt = conn.prepareStatement(sql);  
            rs = pstmt.executeQuery();  
            while(rs.next())  
            {  
                count = rs.getInt("result"); // count = rs.getInt("result");  
            }  
            rs.close();
	        stmt.close();
	        conn.close();
	 	}catch (Exception e) {  
	 		System.out.print("���ݻ�ȡ����1!");   
	          e.printStackTrace(); }
		return (start+count);
	}

}
