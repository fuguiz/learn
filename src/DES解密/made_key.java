package DES����;
/*
 * �ļ���������16������Կ
 */
public class made_key {
	int realKey[]=new int [56];  
	int leftKey[]=new int [28];
	int rightKey[]=new int [28];
	int compressKey_Buff[]=new int [56];
	static int compressKey[][]=new int [16][48];  //����16����Կ
	
	public String Key=null;
	made_key(String key)
	{
		Key=key;
	}

	public void  leftShit(int array[],int shift)
	{
		int tmp[]=new int [28];
		for(int i=0;i<28;i++)
			tmp[i]=array[i];
		for(int i=0;i<28;i++)
		{
			if(i-shift<0)
				array[i]=tmp[i-shift+28];
			else
				array[i]=tmp[i-shift];
		}
	}
	public void get_compressKey()
	{
		String key=ToBinary.all_ToBinary(Key);
		//System.out.println("��ʼ��ԿΪ");
		//System.out.println(key);
		for(int i=0;i<56;i++)  //����Կ����64ת58
		{
			
			realKey[i]=key.charAt(data.PC_1[i]-1)-'0';
		}
		for(int i=0;i<28;i++)   //����Կ����ǰ28��28���л���
		{
			leftKey[i]=realKey[i];
			rightKey[i]=realKey[i+28];
		}
		for(int rou=0;rou<16;rou++)
		{	
			
			//��λ
			leftShit(leftKey,data.shiftBits[rou]);
			leftShit(rightKey,data.shiftBits[rou]);
			for(int i=0;i<28;i++)
			{
				compressKey_Buff[i]=leftKey[i];
				compressKey_Buff[i+28]=rightKey[i];
			}
			
			for(int i=0;i<48;i++)  //����Կ����58תΪ48λ
			{
				compressKey[rou][i]=compressKey_Buff[data.PC_2[i]-1];
			}	

		}
	}
	
}