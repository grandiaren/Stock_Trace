package com.grandia.st.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import com.bocom.jump.bp.service.sqlmap.SqlMap;

/**
 * 类功能描述
 * 
 * @version
 * @author
 */
public class WorkingDayUtils {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Boolean isWorkingDay(String date) {

		SqlMap sqlMap = STOCKFactorySql.getSqlMap();

		String[] dateArry = date.split("-");

		HashMap dateParam = new HashMap();

		dateParam.put("year", Integer.parseInt(dateArry[0]));

		dateParam.put("month", Integer.parseInt(dateArry[1]));

		int day = Integer.parseInt(dateArry[2]);

		String sequence = sqlMap.queryForObject("WorkingDayUtils.queryHolidaySequence", dateParam);

		int flag = Integer.parseInt(sequence.substring(day - 1, day));

		if (0 == flag) {

			return false;

		} else {

			return true;

		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getPreWorkingDay(String date) {

		SqlMap sqlMap = STOCKFactorySql.getSqlMap();

		String[] dateArry = date.split("-");

		int year = Integer.parseInt(dateArry[0]);

		int month = Integer.parseInt(dateArry[1]);

		int day = Integer.parseInt(dateArry[2]);

		if (month == 1 && day == 1) {

			year = year - 1;

			month = 12;

		} else if (month != 1 && day == 1) {

			month = month - 1;

		}

		day = day - 1;

		HashMap dateParam = new HashMap();

		dateParam.put("year", year);

		dateParam.put("month", month);

		String sequence = sqlMap.queryForObject("WorkingDayUtils.queryHolidaySequence", dateParam);

		int newDay = 0;

		if (day == 0) {

			newDay = sequence.lastIndexOf("1") + 1;

		} else {

			newDay = sequence.substring(0, day).lastIndexOf("1") + 1;

		}

		if (newDay == 0) {

			return getPreWorkingDay(
					String.valueOf(year) + "-" + (month < 10 ? "0" + String.valueOf(month) : String.valueOf(month))
							+ "-" + (newDay < 10 ? "0" + String.valueOf(newDay + 1) : String.valueOf(newDay)));

		} else {

			return String.valueOf(year) + "-" + (month < 10 ? "0" + String.valueOf(month) : String.valueOf(month)) + "-"
					+ (newDay < 10 ? "0" + String.valueOf(newDay) : String.valueOf(newDay));

		}

	}

	public static String getCronExpression(int munite) {

		SimpleDateFormat form = new SimpleDateFormat("00 mm HH dd MM ? yyyy");

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.MINUTE, munite);

		return form.format(calendar.getTime());

	}

	public static String postFormat(String oMessage, String transCode) {

		String preFix = "<mof><head><transCode>" + transCode + "</transCode></head>";

		String postFix = "</mof>";

		return preFix + oMessage.replaceFirst("\\<\\?[^<\\]]+\\?\\>", "") + postFix;

	}

	public static String postDeFormat(String oMessage) {

		int startIdx = oMessage.indexOf("</head>");

		return "<?xml version=\"1.0\" encoding=\"GBK\"?>" + oMessage.substring(startIdx + 7, oMessage.length() - 6);

	}

	public static String assembleXML(String voucherCount, String admDivCode, String stYear, String vtCode,
			String voucherNo, String voucherFlag, String returnReason, String vouherBody) {

		String head = "<?xml version=\"1.0\" encoding=\"GBK\"?><MOF><VoucherCount>" + voucherCount
				+ "</VoucherCount><VoucherBody AdmDivCode=\"" + admDivCode + "\" StYear=\"" + stYear + "\" VtCode=\""
				+ vtCode + "\" VoucherNo=\"" + voucherNo + "\"><VoucherFlag>" + voucherFlag
				+ "</VoucherFlag><Return_Reason>" + returnReason + "</Return_Reason><Attach></Attach><Voucher>";

		String body = vouherBody;

		String tail = "</Voucher></VoucherBody></MOF>";

		return head + body + tail;

	}

	public static String assembleStampInfo(String vouherBody) {

		// String head =
		// "<?xml version=\"1.0\" encoding=\"GBK\"?><MOF><VoucherCount>" +
		// voucherCount + "</VoucherCount><VoucherBody AdmDivCode=\"" +
		// admDivCode + "\" StYear=\"" + stYear
		// + "\" VtCode=\"" + vtCode + "\" VoucherNo=\"" + voucherNo +
		// "\"><VoucherFlag>" + voucherFlag + "</VoucherFlag><Return_Reason>" +
		// returnReason
		// + "</Return_Reason><Attach></Attach><Voucher>";
		//
		// String body = vouherBody;
		//
		// String tail = "</Voucher></VoucherBody></MOF>";
		//
		return vouherBody;

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		String testStr = "2017-03-27";

		System.out.println("result1:" + getCronExpression(20));

		// System.out.println("result1:" +
		// testStr.replaceAll("\\<\\?[^<\\]]+\\?\\>", ""));

	}

}
