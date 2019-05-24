package com.grandia.st.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.GZIPInputStream;
import com.bocom.jump.bp.util.Base64;
import com.grandia.st.common.STOCKConstant;

;

/**
 * 类功能描述
 * 
 * @version
 * @author
 */
public class messageFormatUtil {

	public static String XMLBase64Decode(String oMessage) {

		String decodeData = null;

		try {

			decodeData = new String(Base64.decode(oMessage.getBytes(STOCKConstant.DATA_CHANNSET_GBK)),
					STOCKConstant.DATA_CHANNSET_GBK);

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

			return null;

		}

		return decodeData;

	}

	public static String XMLBase64Encode(byte[] oMessage) {

		String encodeData = null;

		encodeData = Base64.encode(oMessage);

		return encodeData;

	}

	public static String XMLBase64DecodeGunzip(String oMessage, String prefix) {

		int prefixIndex = oMessage.indexOf("<" + prefix + ">", 0) + prefix.length() + 2;

		int suffixIndex = oMessage.indexOf("</" + prefix + ">", prefixIndex);

		String base64Data = oMessage.substring(prefixIndex, suffixIndex);

		byte[] B64decodeData = null;

		String dMessage = null;

		try {

			B64decodeData = Base64.decode(base64Data.getBytes(STOCKConstant.DATA_CHANNSET_STANDARD));

			ByteArrayInputStream bis = new ByteArrayInputStream(B64decodeData);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			byte[] dMessageByte = new byte[102400];

			GZIPInputStream gis = new GZIPInputStream(bis);

			int n = 0;

			while ((n = gis.read(dMessageByte)) >= 0) {

				bos.write(dMessageByte, 0, n);

			}

			gis.close();

			dMessage = bos.toString(STOCKConstant.DATA_CHANNSET_STANDARD);

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

			return null;

		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();

			return null;

		}

		return oMessage.replace(base64Data, dMessage);

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

	public static void main(String[] args) {

		String testStr = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iR0JLIiA/PjxWb3VjaGVyPjxJZD4yMDE2MjAxNjAwMDAwMDE4MjwvSWQ+PEFkbURpdkNvZGU+MDAwMDAwPC9BZG1EaXZDb2RlPjxTdFllYXI+MjAxNjwvU3RZZWFyPjxWdENvZGU+NTIxMjwvVnRDb2RlPjxWb3VEYXRlPjIwMTYxMjI4PC9Wb3VEYXRlPjxWb3VjaGVyTm8+MjAxNjAwMDAwMDE4MjwvVm91Y2hlck5vPjxUcmVDb2RlPjAwMDAwMDAwMDA8L1RyZUNvZGU+PEZpbk9yZ0NvZGU+MDAwMDAwMDAwMDwvRmluT3JnQ29kZT48RnVuZFR5cGVDb2RlPjI8L0Z1bmRUeXBlQ29kZT48RnVuZFR5cGVOYW1lPrLG1f7XqLunudzA7dfKvfA8L0Z1bmRUeXBlTmFtZT48UGF5VHlwZUNvZGU+OTE8L1BheVR5cGVDb2RlPjxQYXlUeXBlTmFtZT7KtbKmPC9QYXlUeXBlTmFtZT48Q2xlYXJCYW5rQ29kZT4wOTY8L0NsZWFyQmFua0NvZGU+PENsZWFyQmFua05hbWU+vbvNqNL40NA8L0NsZWFyQmFua05hbWU+PFBheWVlQWNjdE5vPjYyMjI2MjAxMTAwMjQzODQ2NTQ8L1BheWVlQWNjdE5vPjxQYXllZUFjY3ROYW1lPr3wsO6078j9PC9QYXllZUFjY3ROYW1lPjxQYXllZUFjY3RCYW5rTmFtZT69u82o0vjQ0LGxvqm6zca9wO+2q73W1qfQ0DwvUGF5ZWVBY2N0QmFua05hbWU+PFBheWVlQWNjdEJhbmtObz48L1BheWVlQWNjdEJhbmtObz48UGF5QWNjdE5vPjExMDA2MDE0OTAxODE3MDE3MDYyODwvUGF5QWNjdE5vPjxQYXlBY2N0TmFtZT7W0Ln6yMvD8bmyus25+i3W0NHrssbV/teou6c8L1BheUFjY3ROYW1lPjxQYXlBY2N0QmFua05hbWU+vbvNqNL40NCxsb6pus3GvcDvtqu91tan0NA8L1BheUFjY3RCYW5rTmFtZT48QWdlbmN5Q29kZT4wMDAwMTgyMTk8L0FnZW5jeUNvZGU+PEFnZW5jeU5hbWU+1tC5+tf3vNLQrbvhsb68tjwvQWdlbmN5TmFtZT48UGF5U3VtbWFyeUNvZGU+PC9QYXlTdW1tYXJ5Q29kZT48UGF5U3VtbWFyeU5hbWU+PC9QYXlTdW1tYXJ5TmFtZT48Q3VycmVuY3lDb2RlPkNOWTwvQ3VycmVuY3lDb2RlPjxDdXJyZW5jeU5hbWU+yMvD8bHSPC9DdXJyZW5jeU5hbWU+PFBheUFtdD4zMi4wMDwvUGF5QW10PjxQZXJpb2RUeXBlPtX9s6PStc7xPC9QZXJpb2RUeXBlPjxQZXJpb2RUeXBlQ29kZT4wMTwvUGVyaW9kVHlwZUNvZGU+PFhQYXlBbXQ+PC9YUGF5QW10PjxYUGF5RGF0ZT48L1hQYXlEYXRlPjxYQWdlbnRCdXNpbmVzc05vPjwvWEFnZW50QnVzaW5lc3NObz48UmVtYXJrPjwvUmVtYXJrPjxIb2xkMT48L0hvbGQxPjxIb2xkMj48L0hvbGQyPjxEZXRhaWxMaXN0PjxEZXRhaWw+PElkPjIwMTYxMjI4MjAxNjAwMDAwMDE4MjwvSWQ+PFZvdWNoZXJCaWxsSWQ+MjAxNjIwMTYwMDAwMDAxODI8L1ZvdWNoZXJCaWxsSWQ+PEJndFR5cGVDb2RlPjE8L0JndFR5cGVDb2RlPjxCZ3RUeXBlTmFtZT61scTq1KTL4zwvQmd0VHlwZU5hbWU+PFByb0NhdENvZGU+MTU8L1Byb0NhdENvZGU+PFByb0NhdE5hbWU+t8fLsMrVyOvNy7i2PC9Qcm9DYXROYW1lPjxQYXllZUFjY3RObz42MjIyNjIwMTEwMDI0Mzg0NjU0PC9QYXllZUFjY3RObz48UGF5ZWVBY2N0TmFtZT698LDutO/I/TwvUGF5ZWVBY2N0TmFtZT48UGF5ZWVBY2N0QmFua05hbWU+vbvNqNL40NCxsb6pus3GvcDvtqu91tan0NA8L1BheWVlQWNjdEJhbmtOYW1lPjxQYXllZUFjY3RCYW5rTm8+PC9QYXllZUFjY3RCYW5rTm8+PEFnZW5jeUNvZGU+MDAwMDE4MjE5PC9BZ2VuY3lDb2RlPjxBZ2VuY3lOYW1lPtbQufrX97zS0K274bG+vLY8L0FnZW5jeU5hbWU+PEV4cEZ1bmNDb2RlPjwvRXhwRnVuY0NvZGU+PEV4cEZ1bmNOYW1lPjwvRXhwRnVuY05hbWU+PEV4cEZ1bmNDb2RlMT48L0V4cEZ1bmNDb2RlMT48RXhwRnVuY05hbWUxPjwvRXhwRnVuY05hbWUxPjxFeHBGdW5jQ29kZTI+PC9FeHBGdW5jQ29kZTI+PEV4cEZ1bmNOYW1lMj48L0V4cEZ1bmNOYW1lMj48RXhwRnVuY0NvZGUzPjwvRXhwRnVuY0NvZGUzPjxFeHBGdW5jTmFtZTM+PC9FeHBGdW5jTmFtZTM+PEV4cEVjb0NvZGU+NTAxMDE8L0V4cEVjb0NvZGU+PEV4cEVjb05hbWU+uaTXyr3ysrnM+TwvRXhwRWNvTmFtZT48RXhwRWNvQ29kZTE+NTAxPC9FeHBFY29Db2RlMT48RXhwRWNvTmFtZTE+u/q52Lmk18q4o8D71qez9jwvRXhwRWNvTmFtZTE+PEV4cEVjb0NvZGUyPjAxPC9FeHBFY29Db2RlMj48RXhwRWNvTmFtZTI+u/q52Lmk18q4o8D71qez9jwvRXhwRWNvTmFtZTI+PFBheUFtdD4zMi4wMDwvUGF5QW10PjxYUGF5QW10PjwvWFBheUFtdD48WFBheURhdGU+PC9YUGF5RGF0ZT48WEFnZW50QnVzaW5lc3NObz48L1hBZ2VudEJ1c2luZXNzTm8+PFhBZGRXb3JkPjwvWEFkZFdvcmQ+PEhvbGQxPjwvSG9sZDE+PEhvbGQyPjwvSG9sZDI+PEhvbGQzPjwvSG9sZDM+PEhvbGQ0PjwvSG9sZDQ+PC9EZXRhaWw+PC9EZXRhaWxMaXN0PjwvVm91Y2hlcj4=";

		System.out.println("result1:" + XMLBase64Decode(testStr));

		SimpleDateFormat form = new SimpleDateFormat("mm:HH:dd:MM:yyyy");

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.MINUTE, 5);

		System.out.println("result1:" + form.format(calendar.getTime()));

		// System.out.println("result1:" +
		// testStr.replaceAll("\\<\\?[^<\\]]+\\?\\>", ""));

	}

}
