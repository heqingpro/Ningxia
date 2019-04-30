package cn.ipanel.ningxia.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	public static Date strToDate(String dateStr , String format){
		try {
			if(dateStr == null || "".equals(dateStr.trim())){
				return null;
			}
			if(format == null || "".equals(format)){
				//默认格式
				format = "yyyy-MM-dd HH:mm:ss";
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param date
	 * @param format 不传的话默认为yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String dateToStr(Date date, String format){
		if(format == null || "".equals(format)){
			//默认格式
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
}
