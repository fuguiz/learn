package 小方法;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class get_ip {

	public static String IP()
	{
		String ip=null;
		InetAddress ia=null;
		try
		{ 
			ia=InetAddress.getLocalHost();
			ip=ia.getHostAddress();
			//System.out.println("本机的IP = " +ip);
		} catch (UnknownHostException e){ 
			e.printStackTrace();}
		return ip;
	}
	/*public static void main(String[] args) {
		// TODO 自动生成的方法存根
		System.out.println(IP());
	}*/

}
