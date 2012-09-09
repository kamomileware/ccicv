package org.karmaware.cciconv.work;

import java.util.Calendar;
import java.util.Date;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(String.format("\"%-15s\"", "formating", 2, 2.5));
		System.out.println(String.format("\"%2$05d\"", "formating", 2, (int)(2.5 * 100)));
		System.out.println(String.format("\"%3$05.0f\"", "formating", 2, 2.5 * 100));
		
		System.out.printf("%1$tY%1$tm%1$td\n",Calendar.getInstance());
		System.out.printf("%1$tY%1$tm%1$td\n",new Date());
		System.out.printf("%1$tY%1$tm%1$Td\n",System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.set(2009, 9, 2);
		System.out.printf("%d",cal.getTimeInMillis());
	}

}
