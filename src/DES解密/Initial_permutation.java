package DES����;
/*
 * ����������ת����64λ�����ƣ����г�ʼ�û��������ֳ�����������
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
		//System.out.println("��ʼ����");
		//System.out.println(text);
		
		for(int i=0;i<64;i++)  //��IP���г�ʼ�û�
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
