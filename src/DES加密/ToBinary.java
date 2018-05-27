package DESº”√‹;

public class ToBinary {
	public static String one_ToBinary(char s)
	{
		String result1=null;
		
		result1=Integer.toBinaryString((int)s);
		while(result1.length()<8)
		{
			result1='0'+result1;
			//System.out.println(result1);
		}
		return result1;
	}
	
	public static String all_ToBinary(String str)
	{
		String result="";
		for(int i=0;i<str.length();i++)
		{
			char x=str.charAt(i);
			result=result+one_ToBinary(x);
		}
		
		
		return result;
	}

}
