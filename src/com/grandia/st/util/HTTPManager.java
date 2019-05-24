package com.grandia.st.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import com.bocom.jump.bp.channel.http.support.JsonUtils;
import com.grandia.st.common.STOCKConstant;

public class HTTPManager {

	@SuppressWarnings("rawtypes")
	public static HashMap sendData(HttpConnManager connManager, String eventString) {

		URLConnection con = null;

		OutputStream os = null;

		InputStream is = null;

		PrintWriter pw = null;

		if (!(eventString == null || "".equals(eventString))) {

			try {

				StringBuffer sb = new StringBuffer(STOCKConstant.DATA_JSON_HREAD
						+ URLEncoder.encode(eventString, STOCKConstant.DATA_CHANNSET) + STOCKConstant.DATA_JSON_BODY);

				con = connManager.getConnection();

				os = con.getOutputStream();

				pw = new PrintWriter(os);

				pw.write(new String((sb.toString()).getBytes(STOCKConstant.DATA_CHANNSET)));

				pw.flush();

				os.flush();

				is = con.getInputStream();

				byte[] rspByte = new byte[is.available()];

				is.read(rspByte);

				String rspString = new String(rspByte, STOCKConstant.DATA_CHANNSET).trim();

				return JsonUtils.objectFromJson(rspString, HashMap.class);

			} catch (IOException e) {

				e.printStackTrace();

			} catch (Throwable e) {

				e.printStackTrace();

			} finally {

				try {
					if (is != null)

						is.close();

					if (pw != null)

						pw.close();

					if (os != null)

						os.close();

				} catch (IOException ex) {

					if (is != null)
						is = null;

					if (pw != null)
						pw = null;

					if (os != null)
						os = null;

				}

			}

		}

		return null;

	}

	public static String sendData(HttpConnManager connManager) {

		URLConnection con = null;

		OutputStream os = null;

		InputStream is = null;

		PrintWriter pw = null;

		try {

			con = connManager.getConnection();

			os = con.getOutputStream();

			// pw = new PrintWriter(os);
			//
			// pw.write(new
			// String((sb.toString()).getBytes(STOCKConstant.DATA_CHANNSET)));
			//
			// pw.flush();

			os.flush();

			is = con.getInputStream();

			byte[] rspByte = new byte[is.available()];

			is.read(rspByte);

			String rspString = new String(rspByte, STOCKConstant.DATA_CHANNSET_GB2312).trim();

			return rspString;

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Throwable e) {

			e.printStackTrace();

		} finally {

			try {
				if (is != null)

					is.close();

				if (pw != null)

					pw.close();

				if (os != null)

					os.close();

			} catch (IOException ex) {

				if (is != null)
					is = null;

				if (pw != null)
					pw = null;

				if (os != null)
					os = null;

			}

		}

		return null;

	}

}