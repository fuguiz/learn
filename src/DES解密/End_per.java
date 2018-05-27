package DES解密;

public class End_per {
	int end_IP[]=new int [64];
	int end2_IP[]=new int [64];
	public String end_per()
	{
		//String ret2="";
		StringBuffer ret2=new StringBuffer();
		//Encryption encry=new Encryption();
		for(int i=0;i<32;i++)
		{
			end_IP[i]=Encryption.new_rightText[i];
			end_IP[i+32]=Encryption.new_leftText[i];
		}
		//System.out.println("破解的明文是");
		for(int i=0;i<64;i++)
		{
			end2_IP[i]=end_IP[data.IP_1[i]-1];
		}
		for(int i=0;i<64;i++)
		{
			ret2.append(Integer.toString(end2_IP[i]));
			//ret2=ret2+Integer.toString(end2_IP[i]);
		}
		
		return ret2.toString();
	}
}
