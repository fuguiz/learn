package logon_message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_logon_m {

	public void server_m(int port)
	{
		try {  
            
            //1.��������ָ���˿��ϵķ���������  
           // @SuppressWarnings("resource")
			ServerSocket server=new ServerSocket(port);  
            System.out.println("�����������ɹ���"+port); 
            int i=0;
            //2.�÷���������ȴ�״̬������״̬  
            //3.���пͻ�����������ʱ���ȴ������ͻ᷵�أ�����һ��������ͻ������ӵĶ���  
            while(true){//�÷���������ѭ���ȴ�״̬  
                Socket client=server.accept(); 
                i++;
                System.out.println("��" + i +"���ͻ��˳ɹ����ӣ�");
                System.out.println("Incoming client"  
                        +client.getRemoteSocketAddress());    
                        //���ô������Ӷ���ķ���ȥ�������� 
                
                Thread t =new Thread(new Process(client)); //�����ͻ��˴����߳�
                t.start(); //�����߳�
            }  
        } catch (IOException e) {    
            e.printStackTrace();  
        }  
	}
	public static void main(String[] args) {
		Server_logon_m SM=new Server_logon_m();
		SM.server_m(6666);
	}
}
