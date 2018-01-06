package com.jf.date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 获取时间段
 * @author rick
 * 
 */
public class TimeSlotUtil {

	public static void main(String[] args) {
		System.out.println(getDateSlot());
	}

	public static String[] dateArr = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November",
			"December" };

	public static List<CusDateVo> getDateSlot() {
		List<CusDateVo> list = new ArrayList<CusDateVo>();
		list.add(new CusDateVo("All time", "All time"));
		list.add(new CusDateVo("This week", getThisWeek()));
		list.add(new CusDateVo("This month", getThisMonth()));
		list.add(new CusDateVo("Last month", getLastMonth()));
		list.add(new CusDateVo(getMonthByIndex(-2), getTimeSlotByMonth(-2)));
		list.add(new CusDateVo(getMonthByIndex(-3), getTimeSlotByMonth(-3)));
		list.add(new CusDateVo(getMonthByIndex(-4), getTimeSlotByMonth(-4)));
		return list;
	}

	public static String getMonthByIndex(int num) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, num);
		return dateArr[cal.get(Calendar.MONTH)];
	}

	public static String getTimeSlotByMonth(int num) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);

		cal.add(Calendar.MONTH, num);
		String str1 = sdf.format(cal.getTime());

		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String str2 = sdf.format(cal.getTime());
		return str1 + "-" + str2;
	}

	/**
	 * 获取本周
	 * 
	 * @author rick
	 * @date 2016年7月19日 上午10:00:20
	 * @return
	 */
	public static String getThisWeek() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);

		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String str1 = sdf.format(cal.getTime());

		cal.set(Calendar.DAY_OF_WEEK, 1);
		String str2 = sdf.format(cal.getTime());
		return str1 + "-" + str2;
	}

	/**
	 * 获取上周
	 * 
	 * @author rick
	 * @date 2016年7月19日 上午10:00:28
	 * @return
	 */
	public static String getLastWeek() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);

		cal.add(Calendar.DATE, -7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String str1 = sdf.format(cal.getTime());

		cal.set(Calendar.DAY_OF_WEEK, 1);
		String str2 = sdf.format(cal.getTime());
		return str1 + "-" + str2;
	}

	/**
	 * 获取本月
	 * 
	 * @author rick
	 * @date 2016年7月19日 上午10:00:36
	 * @return
	 */
	public static String getThisMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);

		cal.add(Calendar.MONTH, 0);
		String str1 = sdf.format(cal.getTime());

		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String str2 = sdf.format(cal.getTime());
		return str1 + "-" + str2;
	}

	/**
	 * 获取上月
	 * 
	 * @author rick
	 * @date 2016年7月19日 上午10:00:45
	 * @return
	 */
	public static String getLastMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);

		cal.add(Calendar.MONTH, -1);
		String str1 = sdf.format(cal.getTime());

		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String str2 = sdf.format(cal.getTime());
		return str1 + "-" + str2;
	}

	/**
	 * 获取上月与本月
	 * 
	 * @author rick
	 * @date 2016年7月19日 上午10:00:51
	 * @return
	 */
	public static String getAllTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);

		cal.add(Calendar.MONTH, -1);
		String str1 = sdf.format(cal.getTime());

		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String str2 = sdf.format(cal.getTime());
		return str1 + "-" + str2;
	}

}

class CusDateVo {

	String key;
	String value;

	public CusDateVo(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CusDate [key=" + key + ", value=" + value + "]";
	}

}
