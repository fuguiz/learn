package DES����;

/*
 * ��˳��ʹ����Կ�����н��ܣ�ͨ��End_per�����Ľ��б���
 */
public class Decrypt {
	String str=null;
	Decrypt(String ss)
	{
		str=ss;
	}
	public void decrypt()
	{
		int times=15;
		
		Initial_permutation initial=new Initial_permutation(str);
		initial.permutation();   //��IP
		
		Encryption encry=new Encryption();
		
		encry.transpose_IP(initial.leftText,initial.rightText,times);
		
		
		for(times=14;times>=0;times--)
		{
			encry.transpose_IP(encry.new_leftText,encry.new_rightText,times);
		}
		
	}

}
