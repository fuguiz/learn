package test_1;


import java.util.ArrayList;
import java.util.Scanner;

public class Opposite {
	
	
	 static ArrayList a=new ArrayList();
	
	public static void anser(int n)
	{
		String yes="Yes";
		String no="No";
		boolean bool=true;
		//int li[]=new int[n];
		ArrayList<Integer> li = new ArrayList<Integer>();		
		Scanner read1=new Scanner(System.in);
		
		for(int i=0;i<n;i++)
		{
			li.add(read1.nextInt());
		}
		for(int i=1;i<n-1;i++)
		{
			if(li.get(i)*li.get(i+1)%4!=0)
				bool=false;
		}
		if(bool==true)
		{
			a.add(yes);
		}
		else
			a.add(no);
		
	}

	public static void main(String[] args) {

		 Scanner read=new Scanner(System.in);
		 //String x=read.next();
		 int x=read.nextInt();
		 int y=0;
		 
		 while(x>0)
		 {
			 y=read.nextInt();
			 anser(y);
			 //System.out.print(anser(y));
			 x--;
		 }
		 
		 int size = a.size();  
		 for (int i=0; i<size; i++) {  
		     System.out.println(a.get(i));   
		 }
	    
	}

}
