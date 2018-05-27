package logon_picture;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_picture {
	public void server_p(int port)
	{
		try {  
            
            //1.建立绑定在指定端口上的服务器对象  
            @SuppressWarnings("resource")
			ServerSocket server=new ServerSocket(port);  
            System.out.println("服务器创建成功！"+port); 
            int i=0;
            //2.让服务器进入等待状态：阻塞状态  
            //3.当有客户机连接上来时，等待方法就会返回，返回一个代表与客户机连接的对象  
            while(true){//让服务器进入循环等待状态  
                Socket client=server.accept(); 
                i++;
                System.out.println("第" + i +"个客户端成功连接！");
                System.out.println("Incoming client"  
                        +client.getRemoteSocketAddress());    
                        //调用处理连接对象的方法去处理连接 
                
                Thread t =new Thread(new Process_picture(client)); //创建客户端处理线程
                t.start(); //启动线程

            }  
        } catch (IOException e) {    
            e.printStackTrace();  
        }  
	}
	public static void main(String[] args) {
		Server_picture SM=new Server_picture();
		SM.server_p(6667);
	}
}
