 package QQ_client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import DES加密.DesEncry;
import 小方法.MD5;
import 小方法.get_ip;
import 小方法.get_time;
import 票据认证.Cert_as;
import 票据认证.Cert_server;
import 票据认证.Cert_tgs;


public class login {
	private static String name=null;
	private static int id_c;
	private static final int id_as=6666;
	private static final int id_tgs=7777;
	private static int id_v=8888;
	private static String key=null;
	private static String key_c_tgs=null;   //由as生成，as与tgs共享
	private static String key_c_ser=null;  //由tgs生成，tgs与server共享
	private static String ticket_tgs=null;  // as产生向tgs发送的票据
	private static String ticket_ser=null;  //tgs产生，向server发送的票据
	//private static String time=get_time.getTime();
	private static String IP_address=get_ip.IP();
	

	public login (String name,String id,String password)
	{
		login.name=name;
		login.key=MD5.getMD5(password).substring(2,10);   //对密码进行hash处理
		login.id_c=Integer.parseInt(id);
	}
	
	//public void  Certification(String ip_as,String ip_tgs,String ip_server,int port_as,int port_tgs,int port_server) throws IOException
	public void  cert_as(String ip_as,int port_as) throws IOException
	{
		//向AS服务器请求
		Cert_as cert=new Cert_as(ip_as,port_as);
		String time_c=get_time.getTime();
		if(cert.login_as(id_c,id_as,time_c,key))
		{
			System.out.println("获取as票据成功");
			
			/*
			 * 解析as传过来的信息，获取key_c_tgs与ticket
			 */
			String get_ticket_tgs[]=analy_bill_as();
			for(int i=0;i<get_ticket_tgs.length;i++)
			{
				System.out.println(get_ticket_tgs[i]);
			}
			this.key_c_tgs=get_ticket_tgs[3].substring(2,10);  //将2~10 的key_c_tgs保存为秘钥；
			this.ticket_tgs=get_ticket_tgs[2];
			if(Cert_TGS(ip_as,6669))
			{
				System.out.println("获取tgs证书成功");
				/*
				 * 解析tgs传过来的信息，获取key_c_ser与ticket_ser
				 */
				String get_ticket_ser[]=analy_bill_tgs();
				for(int i=0;i<get_ticket_ser.length;i++)
				{
					System.out.println(get_ticket_ser[i]);
				}
				this.key_c_ser=get_ticket_ser[3].substring(2,10);  //将2~10 的key_c_ser保存为秘钥；
				this.ticket_ser=get_ticket_ser[2];
				
				if(Cert_Ser(ip_as,6670))
				{
					System.out.println("server认证通过");
				}
				else
				{
					System.out.println("Server认证未通过");
				}
			}
			else
			{
				System.out.println("获取tgs证书失败");
			}
			
		}
		else
		{
			System.out.println("获取as票据失败");
		}
	}
	public static boolean Cert_TGS(String ip_tgs,int port_tgs) throws IOException
	{
		//@SuppressWarnings("unused")
		Cert_tgs cert=new Cert_tgs(ip_tgs,port_tgs);
		String authen_tgs=make_authen();
		return cert.login_tgs(id_v, ticket_tgs, authen_tgs,key_c_tgs);
	}
	public static boolean Cert_Ser(String ip_ser,int port_ser) throws IOException
	{
		Cert_server cert=new Cert_server(ip_ser,port_ser);
		String authen_ser=make_authen();
		return cert.login_ser(ticket_ser, authen_ser,key_c_ser);
	}
	public static String make_authen()
	{
		String ret=null;
		String clear=Integer.toString(id_c)+","+get_ip.IP()+","+get_time.getTime()+",";
		//return ret=DesEncry.desEncey(clear,key_c_tgs);
		return clear;
	}
	
	public static String[]  analy_bill_as()  //解析AS发送过来的文件数据
	{
		String []get=new String[5];
		File file=new File("./dataFile/client_certificate_as/certificate.txt");  
        BufferedReader reader=null;  
        String temp=null;  
        int line=0;  
        try{  
                reader=new BufferedReader(new FileReader(file));  
                while((temp=reader.readLine())!=null){  
                	String[] Str=temp.split(" ");
                	get[line]=Str[1];
                    //System.out.println("line"+line+":"+temp);  
                    line++;  
                }  
        }  
        catch(Exception e){  
            e.printStackTrace();  
        }  
        finally{  
            if(reader!=null){  
                try{  
                    reader.close();  
                }  
                catch(Exception e){  
                    e.printStackTrace();  
                }  
            }  
        }  
        return get;
	}
	public static String[]  analy_bill_tgs()  //解析AS发送过来的文件数据
	{
		String []get=new String[4];
		File file=new File("./dataFile/client_certificate_tgs/certificate.txt");  
        BufferedReader reader=null;  
        String temp=null;  
        int line=0;  
        try{  
                reader=new BufferedReader(new FileReader(file));  
                while((temp=reader.readLine())!=null){  
                	String[] Str=temp.split(" ");
                	get[line]=Str[1];
                    //System.out.println("line"+line+":"+temp);  
                    line++;  
                }  
        }  
        catch(Exception e){  
            e.printStackTrace();  
        }  
        finally{  
            if(reader!=null){  
                try{  
                    reader.close();  
                }  
                catch(Exception e){  
                    e.printStackTrace();  
                }  
            }  
        }  
        return get;
	}
 
	public static void main(String[] args) throws IOException {
		String name="张富贵";
		String id="10015";
		String password="johnny0101";
		
		login zc=new login(name,id,password);
		zc.cert_as("localhost",6668);
		//String path="C:\\\\Users\\\\sunlight\\\\Pictures\\\\壁纸\\\\壁纸2.jpg";
		//zc.Logon_message("localhost",6666);
		
	}
  
}

