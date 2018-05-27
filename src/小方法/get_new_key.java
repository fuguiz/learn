package 小方法;

import java.util.Random;

public class get_new_key {

	public static String Key_Producer(){
		String result="";
		int str[];
		char s[];
		s=new char[14];
		str=new int[14];
		Random rand = new Random();
		  for(int i=0; i<14; i++) {
			  str[i]=rand.nextInt(89) + 48;//生成33-122的整数
		   s[i]=(char)str[i];
		  }
		  result=s.toString();
		  result=String.valueOf(s);
		return result;
	}
	public static void main (String []args){
		
		System.out.println(get_new_key.Key_Producer());
		System.out.println(get_new_key.Key_Producer());
		System.out.println(get_new_key.Key_Producer());
		System.out.println(get_new_key.Key_Producer());
	}
}

