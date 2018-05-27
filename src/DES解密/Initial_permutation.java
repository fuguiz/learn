package DES解密;
/*
 * 用来将明文转换成64位二进制，进行初始置换，并划分成左右两部分
 */
public class Initial_permutation {
	
	String str=null;
	
	int realText[]=new int [64];  
	int leftText[]=new int [32];
	int rightText[]=new int [32];
	Initial_permutation(String s)
	{
		str=s;
	}
	public void permutation()
	{
		
		//String text=ToBinary.all_ToBinary(str);
		String text=str;
		//System.out.println("初始明文");
		//System.out.println(text);
		
		for(int i=0;i<64;i++)  //将IP进行初始置换
		{	
			realText[i]=text.charAt(data.IP[i]-1)-'0';
		}
		
		for(int i=0;i<32;i++)
		{
			leftText[i]=realText[i];
			rightText[i]=realText[i+32];
		}
		
	}

}
