package С����;

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
			//System.out.println("������IP = " +ip);
		} catch (UnknownHostException e){ 
			e.printStackTrace();}
		return ip;
	}
	/*public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		System.out.println(IP());
	}*/

}
