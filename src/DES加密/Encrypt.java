package DES����;
/*
 * ����ѭ��16�μ��ܣ����Ƚ���ʼ��һ�μ��ܵ���������Ȼ������15��ͨ��ѭ������
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
		initial.permutation();   //��IP
		Encryption encry=new Encryption();
		//���Ƚ��е�һ�μ���
		encry.transpose_IP(initial.leftText,initial.rightText,times);
		//����ʣ��15�εļ���
		for(times=1;times<16;times++)
		{
			encry.transpose_IP(encry.new_leftText,encry.new_rightText,times);
		}
		
	}
	
	

}
