package ���ݿ�����;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql_Driver {
	public static Connection link() throws SQLException
	{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("�ɹ���������!");
		}catch (Exception e) {  
	          System.out.print("�������ش���!");  
	          e.printStackTrace();
	          }
		String url = "jdbc:mysql://localhost:3306/network security course?useSSL=true" + "&useUnicode=true&characterEncoding=GBK";
	 	String user = "root" ;
	 	String password = "123";
	 	Connection connect = DriverManager.getConnection(url, user, password);
	 		//System.out.println("���ݿ����ӳɹ�!");
	 	//Statement stmt = connect.createStatement(); 
	 	return connect;		 	
	}

}
