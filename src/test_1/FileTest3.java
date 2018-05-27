package test_1;

import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.io.UnsupportedEncodingException;  
  
public class FileTest3 {  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
        //读  
        File file=new File("text.txt");//位于工程目录下  
        if(file.exists()){  
            System.err.println("exist");  
            try {  
                FileInputStream fis=new FileInputStream(file);//获取文件的输入流(字节流)  
                InputStreamReader isr=new InputStreamReader(fis,"utf-8");//（字符流）  
                BufferedReader br=new BufferedReader(isr);//带有缓冲的  
                  
                String line;  
                while((line=br.readLine())!=null){  
                    System.out.println(line);  
                }  
                br.close();  
                isr.close();  
                fis.close();  
            } catch (FileNotFoundException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (UnsupportedEncodingException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
          
        //写  
        try {  
              
            File newfile=new File("next.txt");  
            FileOutputStream fos=new FileOutputStream(newfile);  
            OutputStreamWriter osw=new OutputStreamWriter(fos,"utf-8");  
            BufferedWriter bw=new BufferedWriter(osw);  
              
            //覆盖写入  
              
            bw.write("jingchenyong1\r\n");  
            bw.write("jingchenyong2\r\n");  
            bw.write("jingchenyong3\r\n");  
            bw.write("jingchenyong4\r\n");  
            bw.write("jingchenyong5\r\n");  
              
            bw.close();  
            osw.close();  
            fos.close();  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
  
}