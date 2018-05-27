package QQ_AS;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

import DES加密.DesEncry;
import 小方法.MD5;
import 小方法.get_new_key;
import 小方法.get_time;
import 数据库连接.Mysql_Driver;

/*
 * 解析数据包数据，并进行相应的处理
 */
public class Packet_analy {
	public static int lifetime=60000;  //产生的票据的有效期是 1分钟；
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
					
					String key=Judge(id_c,id_tgs,time_c); //得到AS与client的共享秘钥
					System.out.println(key);
					
					if(key!=null)
					{
						String new_key_tgs=new_key(id_c);
						String new_time=get_time.getDate();
						System.out.println(AD);
						String ticket=new_key_tgs+","+Integer.toString(id_c)+","+AD+","+Integer.toString(id_tgs)+","+new_time+","+Integer.toString(lifetime)+",";
						
						String tick=DesEncry.desEncey(ticket,Key_tgs);

						String clear_res=new_key_tgs+","+Integer.toString(id_tgs)+","+new_time+","+Integer.toString(lifetime)+","+tick+",";
						saveAsFileWriter(DesEncry.desEncey(clear_res,key));  //将结果保存到文件中
						//System.out.println(result);
					}
				}
				else
				{
					System.out.println("收到未知数据包：");  
					    //client.close();  
				} 
				break;
			}
	}
	public static String get_key(int id_c)   //从数据库中获取key
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
	 		
	        System.out.println("插入数据成功3");
	        
	        stmt.close();
	        conn.close();
	 	}catch (Exception e) {  
	 		System.out.print("数据获取出错3!");   
	          e.printStackTrace(); }
		return key;
	}
	public static String Judge(int id_c,int id_tgs,long time_c)   //进行条件判断，获取key；
	{
		String key=get_key(id_c);
		if(key==null||(Long.parseLong(get_time.getTime())-time_c)>100000)
			return null;
		return key.substring(2,10);	
	}
	
	public static String new_key(int id)
	{
		/*
		 * 首先产生新的秘钥，然后通过md5加密保存到数据库，
		 * 在使用的时候通过特定的函数解密，比如取其中的额几位作为秘钥
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
	 			System.out.println("记录不存在");
	 			stmt.executeUpdate(sql2);
	 		}
	 		else
	 		{
	 			System.out.println("记录存在"+K_tgs);
	 			stmt.executeUpdate(sql3);
	 		}
		 	
	        System.out.println("插入数据成功3");
	        
	        stmt.close();
	        conn.close();
	 	}catch (Exception e) {  
	 		System.out.print("数据获取出错3!");   
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
