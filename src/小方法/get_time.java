package Ð¡·½·¨;


import java.text.SimpleDateFormat;
import java.util.Date;

public class get_time {

	public static String getTime()
	{
		long time=(new Date().getTime());
		return String.valueOf(time);
	}
	public static String getDate()
	{
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:s");
		return sdf.format(d);
	}
	/*public static void main(String[] args) throws ParseException {
		System.out.println(getTime());
		System.out.println(getDate());
	}*/

}
