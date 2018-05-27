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

import 数据库连接.Mysql_Driver;

/*
 * 解析数据包数据，并进行相应的处理
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
	 static String picture="./头像.jpg";
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
			save_key(id); //保存key到AS数据库
			save_mysql(id);    //保存数据到数据库
			
			break;
				
			}
			else
			{
				System.out.println("没有否和的数据包");
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
			   // statement用来执行SQL语句
		 	  stmt.executeUpdate(sql);
	        System.out.println("插入数据成功3");
	        
	        stmt.close();
	        conn.close();
	 	}catch (Exception e) {  
	 		System.out.print("数据获取出错3!");   
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
			   // statement用来执行SQL语句
		 	  stmt.executeUpdate(sql);
	        System.out.println("插入数据成功2");
	        
	        stmt.close();
	        conn.close();
	 	}catch (Exception e) {  
	 		System.out.print("数据获取出错2!");   
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
			Statement stmt=conn.createStatement();//创建一个Statement对象
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
	 		System.out.print("数据获取出错1!");   
	          e.printStackTrace(); }
		return (start+count);
	}

}
