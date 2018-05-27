package DES加密;

public class End_per {
	int end_IP[]=new int [64];
	int end2_IP[]=new int [64];
	public String end_per()
	{
		//Print pr=new Print();
		String ret="";
		Encryption encry=new Encryption();
		for(int i=0;i<32;i++)
		{
			end_IP[i]=encry.new_rightText[i];
			end_IP[i+32]=encry.new_leftText[i];
		}
		//System.out.println("加密后的密文是");
		for(int i=0;i<64;i++)
		{
			end2_IP[i]=end_IP[data.IP_1[i]-1];
		}
		for(int i=0;i<64;i++)
		{	
			ret=ret+Integer.toString(end2_IP[i]);
		}
		return ret;
	}
}
