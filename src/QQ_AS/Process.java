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
	static Socket client= null; //����ͻ���Socket����
	public Process(Socket client)
	{
		Process.client=client;
	}
	public void run()
	{
		try {  
           
            OutputStream out=client.getOutputStream();  //���
            InputStream in=client.getInputStream();   //����
            
            DataInputStream dins=new DataInputStream(in); 
            DataOutputStream douts=new DataOutputStream(out);
            
            Packet_analy.packet_analy(dins, client);    //���б����ݵĽ�����������
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
                // �ļ����ͳ��� 
            	byte cmd=0x02;
                dos.write(cmd);  
                dos.flush();  
                dos.writeLong(file.length());  
                dos.flush();  
  
                // ��ʼ�����ļ�  
                System.out.println("======== ��ʼ�����ļ� ========");  
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
                System.out.println("======== �ļ�����ɹ� ========");  
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
	        // ����ļ�·������Ӧ���ļ����ڣ�������һ���ļ�����ֱ��ɾ��
	        if (file.exists() && file.isFile()) {
	            if (file.delete()) {
	                System.out.println("ɾ�������ļ�" + fileName + "�ɹ���");
	                return true;
	            } else {
	                System.out.println("ɾ�������ļ�" + fileName + "ʧ�ܣ�");
	                return false;
	            }
	        } else {
	            System.out.println("ɾ�������ļ�ʧ�ܣ�" + fileName + "�����ڣ�");
	            return false;
	        }
	    }

}

