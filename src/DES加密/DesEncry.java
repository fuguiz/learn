package DES加密;

public class DesEncry {
	
	public  static String desEncey(String clearText,String key)
	{
		StringBuffer ciphertext=new StringBuffer();
		made_key made=new made_key(key);
		made.get_compressKey();
		if(clearText.length()%8!=0)
		{
			for(int i=8-clearText.length()%8;i>0;i--)
				clearText=clearText+" ";
		}
		for(int i=0;i<clearText.length();i=i+8)
		{
			String text="";
			for(int j=i;j<i+8;j++)
			{
				text=text+clearText.charAt(j);
			}
			Encrypt encrypt=new Encrypt(text);
			encrypt.encrypt();
			
			End_per end=new End_per();
			ciphertext.append(end.end_per());
		}
		
		String str=ciphertext.toString();   //为二进制格式
		return str;
		/*StringBuffer sb=new StringBuffer();
		
		System.out.println(str);
		
		for(int i=0;i<str.length();i=i+8)
		{
			String st="";
			for(int j=0;j<8;j++)
			{
				st=st+str.charAt(i+j);
				
			}
			//System.out.println(st);
			sb.append((char)Integer.parseInt(st,2));
		}
		return sb.toString();*/
	}
	/*public static void main(String[] args) {

		DesEncry des=new DesEncry();
		String ss=des.desEncey("01110110101011110111","abcdefgh");
		System.out.print(ss);

	}*/

}
