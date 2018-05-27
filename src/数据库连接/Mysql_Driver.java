package 数据库连接;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql_Driver {
	public static Connection link() throws SQLException
	{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("成功加载驱动!");
		}catch (Exception e) {  
	          System.out.print("驱动加载错误!");  
	          e.printStackTrace();
	          }
		String url = "jdbc:mysql://localhost:3306/network security course?useSSL=true" + "&useUnicode=true&characterEncoding=GBK";
	 	String user = "root" ;
	 	String password = "123";
	 	Connection connect = DriverManager.getConnection(url, user, password);
	 		//System.out.println("数据库连接成功!");
	 	//Statement stmt = connect.createStatement(); 
	 	return connect;		 	
	}

}
