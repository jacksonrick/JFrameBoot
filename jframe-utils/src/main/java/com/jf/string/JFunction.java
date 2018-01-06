package com.jf.string;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 标签库函数
 * 
 * @author rick
 * @version
 */
public class JFunction {

	public static void main(String[] args) {
		// System.out.println(JFunction.getRemainDay(DateUtil.dateAddDay(new Date(), 5)));
		// System.out.println(JFunction.formatIdcard("340123199311012"));
		// System.out.println(JFunction.formatBankcard("621558130281824"));
	}

	public static String formatPhone(String phone) {
		if (phone == null || "".equals(phone)) {
			return "";
		}
		if (phone.length() == 11) {
			return phone.substring(0, 3) + "****" + phone.substring(7, 11);
		}
		return phone.substring(0, 3) + "********";
	}

	public static String formatIdcard(String idcard) {
		if (idcard == null || "".equals(idcard)) {
			return "";
		}
		if (idcard.length() == 18) {
			return idcard.substring(0, 6) + "********" + idcard.substring(14, 18);
		}
		if (idcard.length() == 15) {
			return idcard.substring(0, 6) + "*****" + idcard.substring(11, 15);
		}
		return idcard.substring(0, 6) + "*********";
	}

	public static String formatBankcard(String bankcard) {
		if (bankcard == null || "".equals(bankcard)) {
			return "";
		}
		if (bankcard.length() > 10) {
			return bankcard.substring(0, 6) + "********" + bankcard.substring(bankcard.length() - 4, bankcard.length());
		}
		return bankcard.substring(0, 6) + "*********";
	}

	public static String formatMoney(Double money) {
		if (money == null) {
			return "0元";
		}
		if (money >= 10000) {
			return money / 10000 + " 万元";
		} else {
			return money + "元";
		}
	}

	public static String formatHTML(String html) {
		// <p>段落替换为换行
		html = html.replaceAll("<p .*?>", "\r\n");
		// <br><br/>替换为换行
		html = html.replaceAll("<br\\s*/?>", "\r\n");
		// 去掉其它的<>之间的东西
		html = html.replaceAll("\\<.*?>", "");
		return html;
	}

	public static Integer getRemainDay(Date endDate) {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// String now=sdf.format(new Date());
		int remain = 0;
		if (endDate == null) {
			return remain;
		}
		try {
			Date date = new Date();
			if (date.getTime() > endDate.getTime()) {
				return remain;
			}
			remain = daysBetween(date, endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return remain;
	}

	private static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	private static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
}
