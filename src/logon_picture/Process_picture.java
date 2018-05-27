package logon_picture;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Process_picture implements Runnable {
	static Socket client= null; //保存客户端Socket对象
	public Process_picture(Socket client)
	{
		Process_picture.client=client;
	}
	public void run()
	{
		try {  
            //System.out.println("hi");  
            //得到一个输入、输出流对象  
            OutputStream  out=client.getOutputStream();  
            InputStream ins=client.getInputStream();  
            
            DataInputStream dins=new DataInputStream(ins);  //进行报数据的解析，并处理
            Packet_picture.packet_picture(dins, client);
     
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
	}

}
