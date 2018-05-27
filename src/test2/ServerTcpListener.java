package test2;

import java.net.*;
import java.io.*;

public class ServerTcpListener implements Runnable {
    public static void main(String[] args) {
        try {
            final ServerSocket server = new ServerSocket(6666);
            Thread th = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            System.out.println("��ʼ����...");
                            Socket socket = server.accept();
                            System.out.println("������");
                            receiveFile(socket);
                        } catch (Exception e) {
                        }
                    }
                }
            });

            th.run(); //�����߳�����
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
    }

    public static void receiveFile(Socket socket) {
        byte[] inputByte = null;
        int length = 0;
        DataInputStream dis = null;
        FileOutputStream fos = null;
        try {
            try {
                dis = new DataInputStream(socket.getInputStream());
                fos = new FileOutputStream(new File("./cc.jpg"));
                inputByte = new byte[1024];
                System.out.println("��ʼ��������...");
                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
                    System.out.println(length);
                    fos.write(inputByte, 0, length);
                    fos.flush();
                }
                System.out.println("��ɽ���");
            } finally {
                if (fos != null)
                    fos.close();
                if (dis != null)
                    dis.close();
                if (socket != null)
                    socket.close();
            }
        } catch (Exception e) {
        }
    }
}
