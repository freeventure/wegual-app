package com.wegual.webapp;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

public abstract class PrettyTimeUtil {
	
	private static PrettyTime pt = new PrettyTime();
	
	public static PrettyTime prettyTime() {
		return pt;
	}
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM. yyyy");
	
	public static String prettyDayFormat(long timestamp) {
		return dateFormat.format(new Date(timestamp));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrettyTime p = new PrettyTime();
		System.out.println(p.format(new Date(System.currentTimeMillis() - (120*1000))));
		
		System.out.println(p.format(new Date(System.currentTimeMillis() - (12000*1000))));
		
		String pattern = "d MMM. yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(new Date());
		System.out.println(date);		
		
	}

}
