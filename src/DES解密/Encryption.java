package DES解密;
/*
 * 用来将32的明文置换成48位，与秘钥进行异或运算，并通过沙盒进行减小为32位，
 * 再置换为32位，相当于函数f,然后将异或后的右部分与左部分进行异或，由于操作会改
 * 变原始右部分，需要备份，然后将左右两部分分别进行赋值给新的左右两部分
 */
public class Encryption {
	
	int en_rightText[]=new int [48];  //保存扩展的32-48
	int re_rightText[]=new int [48];   //保存于秘钥加密后的48
	int re_leftText[]=new int [32];   //保存新的左部分
	static int new_leftText[]=new int [32];
	static int new_rightText[]=new int [32];
	
	public void transpose_IP(int leftText[],int rightText[],int times)
	{
		int two_rightText[]=new int [32];
		int two_leftText[]=new int [32];
		for(int i=0;i<32;i++)   //对右边的数据进行备份
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
		for(int i=0;i<48;i=i+6)   //将48通过沙盒转换为32位的字符串
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
		for(int i=0;i<32;i++)   //将32位的字符串先进行转置，在转置的过程中在给为int型
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
