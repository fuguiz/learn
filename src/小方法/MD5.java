package С����;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String getMD5(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");//��������ָ���㷨���Ƶ�ժҪ
            md.update(str.getBytes());                    //ʹ��ָ�����ֽ��������ժҪ
            byte mdBytes[] = md.digest();                 //���й�ϣ���㲢����һ���ֽ�����

            String hash = "";
            for(int i= 0;i<mdBytes.length;i++){           //ѭ���ֽ�����
                int temp;
                if(mdBytes[i]<0)                          //�����С��0���ֽ�,��ת��Ϊ����
                    temp =256+mdBytes[i];
                else
                    temp=mdBytes[i];
                if(temp<16)
                    hash+= "0";
                hash+=Integer.toString(temp,16);         //���ֽ�ת��Ϊ16���ƺ�ת��Ϊ�ַ���
            }
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
	

	/* public static void main(String[] args) {
		MD5 md5=new MD5();
		String str=md5.getMD5("fasdfgbjkhasdgbfjh");
		System.out.println(str);

	}*/

}
