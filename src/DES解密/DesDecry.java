package DES����;
/*
 * ������ܵĺ��  ������
 */
public class DesDecry {

	public static String desDecry(String Text,String key)
	{
		StringBuffer ciphertext=new StringBuffer();
		made_key made=new made_key(key);
		made.get_compressKey(); //��Կ����
		
		for(int i=0;i<Text.length();i=i+64)
		{
			String text="";
			for(int j=i;j<i+64;j++)
			//for(int j=0;j<Text.length();j++)
			{
				text=text+Text.charAt(j);
			}
			Decrypt dec=new Decrypt(text);  //�����Ľ��н���
			dec.decrypt();
			
			End_per end=new End_per();
			ciphertext.append(end.end_per());
		}
		String str=ciphertext.toString();   //Ϊ�����Ƹ�ʽ
		
		//System.out.println(str);
		
		StringBuffer sb=new StringBuffer();
		
		for(int i=0;i<str.length();i=i+8)
		{
			String st="";
			for(int j=0;j<8;j++)
			{
				st=st+str.charAt(i+j);			
			}
			sb.append((char)Integer.parseInt(st,2));
		}
		return sb.toString();
		
	}
	/*public static void main(String[] args) {
		DesDecry des=new DesDecry();
		String ss=des.desDecry("000111111010001100000010000100011110010011000001011000011010001100110111000010100110111011100101111011100100010001001010011111101000101001100011010100000100010000111111111110100011011100101011","abcdefgh");
		
		System.out.print(ss);

	}*/

}
