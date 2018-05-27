package DES加密;
/*
 * 用来循环16次加密，首先将开始的一次加密单独出来，然后将其余15次通过循环加密
 */
public class Encrypt {
	String str=null;
	Encrypt(String ss)
	{
		str=ss;
	}
	public void encrypt()
	{
		int times=0;
		Initial_permutation initial=new Initial_permutation(str);
		initial.permutation();   //对IP
		Encryption encry=new Encryption();
		//首先进行第一次加密
		encry.transpose_IP(initial.leftText,initial.rightText,times);
		//进行剩余15次的加密
		for(times=1;times<16;times++)
		{
			encry.transpose_IP(encry.new_leftText,encry.new_rightText,times);
		}
		
	}
	
	

}
