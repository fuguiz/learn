package test2;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientTcpSend {
    public static void main(String[] args) {
        int length = 0;
        byte[] sendBytes = null;
        Socket socket = null;
        DataOutputStream dos = null;
        FileInputStream fis = null;

        try {
            try {
                /*socket = new Socket();
                socket.connect(new InetSocketAddress("127.0.0.1", 6666),
                               10 * 1000);*/
            	socket = new Socket("127.0.0.1", 6666); 
                dos = new DataOutputStream(socket.getOutputStream());
                String path="C:\\Users\\sunlight\\Pictures\\±ÚÖ½\\±ÚÖ½1.jpg";
                File file = new File(path);
                fis = new FileInputStream(file);
                sendBytes = new byte[1024];
                while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
                    dos.write(sendBytes, 0, length);
                    dos.flush();
                }
            } finally {
                if (dos != null)
                    dos.close();
                if (fis != null)
                    fis.close();
                if (socket != null)
                    socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
