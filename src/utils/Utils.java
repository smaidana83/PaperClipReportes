package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	
	public static String convertDateToString(Date date){		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		String formatted = format1.format(date.getTime());
		return formatted;				
	}

}
