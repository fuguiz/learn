package logon_message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import 小方法.MessageTran;


public class Process implements Runnable{
	static Socket client= null; //保存客户端Socket对象
	public Process(Socket client)
	{
		Process.client=client;
	}
	public void run()
	{
		try {  
            //System.out.println("hi");  
            //得到一个输入、输出流对象  
            OutputStream  out=client.getOutputStream();  
            InputStream ins=client.getInputStream();  
            
            DataInputStream dins=new DataInputStream(ins);  //进行报数据的解析，并处理
            DataOutputStream outs=new DataOutputStream(out);
            //System.out.println("返回"+Packet_analy.ID);
            Packet_analy.packet_message(dins, client);
            
            System.out.println("返回"+Packet_analy.ID);
            
            byte[] dataTran=sendMessage(Integer.toString(Packet_analy.ID));	
			outs.write(dataTran);
			outs.flush();
     
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
	}
	private static byte[] sendMessage(String data) {
		// TODO Auto-generated method stub
		byte cmd=0x01;
		if(data.getBytes().length>256)
		{
			return null;
		}
		MessageTran m=new MessageTran(cmd,data.getBytes());	
		
		return m.getDataTran();
	}

}

