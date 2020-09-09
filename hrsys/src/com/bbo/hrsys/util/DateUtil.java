package com.bbo.hrsys.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//日期格式处理工具类
public class DateUtil {
	private static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getDateString(Date date){
		return fmt.format(date);	
	}

	public static String getDateString(Calendar cal){
		return fmt.format(cal.getTime());	
	}
	/**
	 * 在指定范围中生成随机时间
	 * @param beging
	 * @param end
	 * @return
	 */
	public static Date randomDate(String beging,String end) {
		SimpleDateFormat sdf ;
		Date newDate = null;
		//未检查参数合法性，缺少自定义异常
		if(beging.contains(":")) {
			sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm");
		}else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		try {
			Date begingTime = sdf.parse(beging);
			Date endTime = sdf.parse(end);
			newDate = new Date( (long)(Math.random()*(endTime.getTime()-begingTime.getTime()))+begingTime.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}
}
