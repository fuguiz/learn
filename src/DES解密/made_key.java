package DES解密;
/*
 * 文件用来生成16个子秘钥
 */
public class made_key {
	int realKey[]=new int [56];  
	int leftKey[]=new int [28];
	int rightKey[]=new int [28];
	int compressKey_Buff[]=new int [56];
	static int compressKey[][]=new int [16][48];  //保存16个秘钥
	
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
		//System.out.println("初始秘钥为");
		//System.out.println(key);
		for(int i=0;i<56;i++)  //将秘钥进行64转58
		{
			
			realKey[i]=key.charAt(data.PC_1[i]-1)-'0';
		}
		for(int i=0;i<28;i++)   //将秘钥进行前28后28进行划分
		{
			leftKey[i]=realKey[i];
			rightKey[i]=realKey[i+28];
		}
		for(int rou=0;rou<16;rou++)
		{	
			
			//移位
			leftShit(leftKey,data.shiftBits[rou]);
			leftShit(rightKey,data.shiftBits[rou]);
			for(int i=0;i<28;i++)
			{
				compressKey_Buff[i]=leftKey[i];
				compressKey_Buff[i+28]=rightKey[i];
			}
			
			for(int i=0;i<48;i++)  //将秘钥进行58转为48位
			{
				compressKey[rou][i]=compressKey_Buff[data.PC_2[i]-1];
			}	

		}
	}
	
}