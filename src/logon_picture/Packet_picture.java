package logon_picture;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

import 数据库连接.Mysql_Driver;

public class Packet_picture {
	private static int ID;
	private static String path;
	public static void packet_picture(DataInputStream dins,Socket client) throws IOException
	{
		byte[] buffer=new byte[1024];
        int len=0;
        if((len=dins.read(buffer))!=-1)
        {
        	String s=new String(buffer);
			String[] str=s.split(" ");
			ID=Integer.parseInt(str[0]);
			path=str[1];
			System.out.println(ID);
			System.out.println(path);
			sava_picture();
        }
	}

	
	public static void sava_picture() throws IOException
	{
		
		try
		{
			Connection conn=Mysql_Driver.link();
			Statement stmt=conn.createStatement();//创建一个Statement对象
			
			String sql="update user_data set picture ='"+ path +"' where ID = " +ID;
		
			stmt.executeUpdate(sql);
			
			System.out.print("头像上传成功"); 
	        stmt.close();
	        conn.close();
	 	}catch (Exception e) {  
	 		System.out.print("数据获取出错1");   
	          e.printStackTrace(); }
	}
}
