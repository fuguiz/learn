package QQ_AS;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;



public class Process implements Runnable{
	static Socket client= null; //保存客户端Socket对象
	public Process(Socket client)
	{
		Process.client=client;
	}
	public void run()
	{
		try {  
           
            OutputStream out=client.getOutputStream();  //输出
            InputStream in=client.getInputStream();   //输入
            
            DataInputStream dins=new DataInputStream(in); 
            DataOutputStream douts=new DataOutputStream(out);
            
            Packet_analy.packet_analy(dins, client);    //进行报数据的解析，并处理
            send_file(douts);
            //deleteFile("./dataFile/as_send_client/key.txt");
           
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
	}
	public static void send_file(DataOutputStream dos) throws IOException
	{
		FileInputStream fis = null;
		try {  
            File file = new File("./dataFile/as_send_client/key.txt");  
            if(file.exists()) {  
            	fis= new FileInputStream(file); 
                // 文件名和长度 
            	byte cmd=0x02;
                dos.write(cmd);  
                dos.flush();  
                dos.writeLong(file.length());  
                dos.flush();  
  
                // 开始传输文件  
                System.out.println("======== 开始传输文件 ========");  
                byte[] bytes = new byte[1024];  
                int length = 0;  
                long progress = 0;  
                while((length = fis.read(bytes, 0, bytes.length)) != -1) {  
                    dos.write(bytes, 0, length);  
                    dos.flush();  
                    progress += length;  
                    System.out.print("| " + (100*progress/file.length()) + "% |");  
                }  
                System.out.println();  
                System.out.println("======== 文件传输成功 ========");  
            }
            else
            {
            	byte cmd=0x03;
                dos.write(cmd);
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if(fis != null)  
                fis.close();  
            if(dos != null)  
                dos.close();    
        }  
    }
	 public static boolean deleteFile(String fileName) {
	        File file = new File(fileName);
	        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
	        if (file.exists() && file.isFile()) {
	            if (file.delete()) {
	                System.out.println("删除单个文件" + fileName + "成功！");
	                return true;
	            } else {
	                System.out.println("删除单个文件" + fileName + "失败！");
	                return false;
	            }
	        } else {
	            System.out.println("删除单个文件失败：" + fileName + "不存在！");
	            return false;
	        }
	    }

}

