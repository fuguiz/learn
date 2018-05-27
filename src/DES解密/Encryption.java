package DES����;
/*
 * ������32�������û���48λ������Կ����������㣬��ͨ��ɳ�н��м�СΪ32λ��
 * ���û�Ϊ32λ���൱�ں���f,Ȼ��������Ҳ������󲿷ֽ���������ڲ������
 * ��ԭʼ�Ҳ��֣���Ҫ���ݣ�Ȼ�����������ֱַ���и�ֵ���µ�����������
 */
public class Encryption {
	
	int en_rightText[]=new int [48];  //������չ��32-48
	int re_rightText[]=new int [48];   //��������Կ���ܺ��48
	int re_leftText[]=new int [32];   //�����µ��󲿷�
	static int new_leftText[]=new int [32];
	static int new_rightText[]=new int [32];
	
	public void transpose_IP(int leftText[],int rightText[],int times)
	{
		int two_rightText[]=new int [32];
		int two_leftText[]=new int [32];
		for(int i=0;i<32;i++)   //���ұߵ����ݽ��б���
		{
			two_leftText[i]=leftText[i];
			two_rightText[i]=rightText[i];
		}
		
		for(int i=0;i<48;i++)
		{
			en_rightText[i]=two_rightText[data.E[i]-1];
		}
		
		for(int i=0;i<48;i++)
		{
			re_rightText[i]=en_rightText[i]^made_key.compressKey[times][i];
		}
		String str_re_leftText="";
		for(int i=0;i<48;i=i+6)   //��48ͨ��ɳ��ת��Ϊ32λ���ַ���
		{
			
			int row=re_rightText[i]*2+re_rightText[i+5];
			int col=re_rightText[i+1]*8+re_rightText[i+2]*4+re_rightText[i+3]*2+re_rightText[i+4];
			int num=data.S_BOX[i/6][row][col];
			String str_num=Integer.toBinaryString(num);
			while(str_num.length()<4)
			{
				str_num='0'+str_num;
			}
			str_re_leftText=str_re_leftText+str_num;
		}
		for(int i=0;i<32;i++)   //��32λ���ַ����Ƚ���ת�ã���ת�õĹ������ڸ�Ϊint��
		{
			re_leftText[i]=str_re_leftText.charAt(data.P[i]-1)-'0';
		}
		
		for(int i=0;i<32;i++)
		{
			new_rightText[i]=re_leftText[i]^two_leftText[i];
		}
		for(int i=0;i<32;i++)
		{
			
			new_leftText[i]=two_rightText[i];
		}
	}
	

}
