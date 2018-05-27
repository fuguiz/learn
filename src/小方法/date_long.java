package Ð¡·½·¨;

import java.text.SimpleDateFormat;
import java.util.Date;

public class date_long {

	public static Long convertTimeToLong(String time) {  
	    Date date = null;  
	    try {  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");  
	        date = sdf.parse(time);  
	        return date.getTime();  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	        return 0L;  
	    }  
	}  
	public static void main(String[] args) {
		System.out.println("1526220376686");
		System.out.println(convertTimeToLong("2018-05-13-22:06:16"));

	}

}
