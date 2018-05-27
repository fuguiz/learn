package QQ_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import С����.MessageTran;

public class Process implements Runnable{
	Socket client= null; //����ͻ���Socket����
	Process(Socket client)
	{
		this.client=client;
	}
	public void run()
	{
		try {  
            System.out.println("hi");  
            //�õ�һ�����롢���������  
            OutputStream  out=client.getOutputStream();  
            InputStream ins=client.getInputStream();   // //����������װΪDataInputStream�����ȡԭʼ���͵���  
           
            DataInputStream dins=new DataInputStream(ins);
            DataOutputStream douts=new DataOutputStream(out);
            
            String res=Packet_analy.packet_analy(dins,client);
            if(res==null)
            {
            	System.out.println("���Ϊ��");
            	byte cmd=0x09;
            	douts.write(cmd);
                douts.flush();
            }
            else
            {
            	System.out.println("�����ʼ����"+res);
            	byte cmd=0x08;
            	douts.write(cmd);
                douts.flush();
                
                douts.write(res.getBytes());
                 
                douts.flush();  
            	/*byte[] dataTran=sendMessage(Packet_analy.packet_analy(dins,client));	//��������
            	douts.write(dataTran);
                douts.flush();*/
            }
            
            
        } catch (IOException e) {   
            e.printStackTrace();          
        }  
		/*finally{
			if(douts!=null){
				douts.close();
			}*/
	}
	
	private static byte[] sendMessage(String data) {
		byte cmd=0x08;
		MessageTran m=new MessageTran(cmd,data.getBytes());	
		
		return m.getDataTran();
	}

}
