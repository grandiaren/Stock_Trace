package com.grandia.st.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import com.bocom.jump.bp.channel.http.support.JsonUtils;
import com.bocom.jump.bp.core.CoreException;
import com.grandia.st.common.STOCKConstant;

public class STOCKJuHeManager {

	private static final String DEF_CHATSET = "UTF-8";

	private static final int DEF_CONN_TIMEOUT = 30000;

	private static final int DEF_READ_TIMEOUT = 30000;

	private static final String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	// 1.沪深股市
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getHSStockDetail(String stockNo, int type) {

		String result = null;

		String url = "http://web.juhe.cn:8080/finance/stock/hs";// 请求接口地址

		Map params = new HashMap();// 请求参数

		params.put("gid", stockNo);// 股票编号，上海股市以sh开头，深圳股市以sz开头如：sh601009

		params.put("key", STOCKConstant.JH_APPKEY);// APP Key

		if (type == 0 || type == 1) {

			params.put("type", type);// 0：上证指数 1：深圳指数

		}

		HashMap object = new HashMap();

		try {

			result = net(url, params, "GET");

			object = JsonUtils.objectFromJson(result, HashMap.class);

			if (Integer.parseInt(object.get("error_code").toString()) == 0) {

			} else {

				System.out.println(object.get("error_code") + ":" + object.get("reason"));

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return object;

	}

	// 2.香港股市
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getHKStockDetail(String stockNo) {

		String result = null;

		String url = "http://web.juhe.cn:8080/finance/stock/hk";// 请求接口地址

		Map params = new HashMap();// 请求参数

		params.put("num", stockNo);// 股票代码，如：00001 为“长江实业”股票代码

		params.put("key", STOCKConstant.JH_APPKEY);// APP Key

		HashMap object = new HashMap();

		try {

			result = net(url, params, "GET");

			object = JsonUtils.objectFromJson(result, HashMap.class);

			if (Integer.parseInt(object.get("error_code").toString()) == 0) {

			} else {

				System.out.println(object.get("error_code") + ":" +

						object.get("reason"));

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return object;

	}

	// 3.美国股市
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getUSStockDetail(String stockNo) {

		String result = null;

		String url = "http://web.juhe.cn:8080/finance/stock/usa";// 请求接口地址

		Map params = new HashMap();// 请求参数

		params.put("gid", stockNo);// 股票代码，如：aapl 为“苹果公司”的股票代码

		params.put("key", STOCKConstant.JH_APPKEY);// APP Key

		HashMap object = new HashMap();

		try {

			result = net(url, params, "GET");

			object = JsonUtils.objectFromJson(result, HashMap.class);

			if (Integer.parseInt(object.get("error_code").toString()) == 0) {

			} else {

				System.out.println(object.get("error_code") + ":" + object.get("reason"));

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return object;

	}

	// 4.香港股市列表
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getHKStockList(int page, int type) {

		String result = null;

		String url = "http://web.juhe.cn:8080/finance/stock/hkall";// 请求接口地址

		Map params = new HashMap();// 请求参数

		params.put("key", STOCKConstant.JH_APPKEY);// 您申请的APPKEY

		params.put("page", page);// 第几页,每页20条数据,默认第1页

		if (type == 1 || type == 2 || type == 3 || type == 4) {

			params.put("type", type);// 每页返回条数,1(20条默认),2(40条),3(60条),4(80条)

		}

		HashMap object = new HashMap();

		try {

			result = net(url, params, "GET");

			object = JsonUtils.objectFromJson(result, HashMap.class);

			if (Integer.parseInt(object.get("error_code").toString()) == 0) {

			} else {

				System.out.println(object.get("error_code") + ":" + object.get("reason"));

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return object;

	}

	// 5.美国股市列表
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getUSStockList(int page, int type) {

		String result = null;

		String url = "http://web.juhe.cn:8080/finance/stock/usaall";// 请求接口地址

		Map params = new HashMap();// 请求参数

		params.put("key", STOCKConstant.JH_APPKEY);// 您申请的APPKEY

		params.put("page", page);// 第几页,每页20条数据,默认第1页

		if (type == 1 || type == 2 || type == 3) {

			params.put("type", type);// 每页返回条数,1(20条默认),2(40条),3(60条)

		}

		HashMap object = new HashMap();

		try {

			result = net(url, params, "GET");

			object = JsonUtils.objectFromJson(result, HashMap.class);

			if (Integer.parseInt(object.get("error_code").toString()) == 0) {

			} else {

				System.out.println(object.get("error_code") + ":" + object.get("reason"));

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return object;

	}

	// 6.深圳股市列表
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getSZStockList(String sType, int page, int type) throws Exception {

		String result = null;

		String url = "http://web.juhe.cn:8080/finance/stock/szall";// 请求接口地址

		Map params = new HashMap();// 请求参数

		params.put("key", STOCKConstant.JH_APPKEY);// 您申请的APPKEY

		params.put("page", page);// 第几页(每页20条数据),默认第1页

		if ("a".equals(sType) || "b".equals(sType)) {

			params.put("stock", sType);// a表示A股，b表示B股,默认所有(建议加上a或b)

		}

		if (type == 1 || type == 2 || type == 3 || type == 4) {

			params.put("type", type);// 每页返回条数,1(20条默认),2(40条),3(60条),4(80条)

		}

		HashMap object = new HashMap();

		try {

			result = net(url, params, "GET");

			object = JsonUtils.objectFromJson(result, HashMap.class);

			if (Integer.parseInt(object.get("error_code").toString()) == 0) {

			} else {

				System.out.println(object.get("error_code") + ":" + object.get("reason"));

				throw new CoreException(object.get("error_code").toString(), object.get("reason").toString());

			}

		} catch (Exception e) {

			e.printStackTrace();

			throw e;

		}

		return (HashMap) object.get("result");

	}

	// 7.沪股列表
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap getSHStockList(String sType, int page, int type) throws Exception {

		String result = null;

		String url = "http://web.juhe.cn:8080/finance/stock/shall";// 请求接口地址

		Map params = new HashMap();// 请求参数

		params.put("key", STOCKConstant.JH_APPKEY);// 您申请的APPKEY

		params.put("page", page);// 第几页,每页20条数据,默认第1页

		if ("a".equals(sType) || "b".equals(sType)) {

			params.put("stock", sType);// a表示A股，b表示B股,默认所有(建议加上a或b)

		}

		if (type == 1 || type == 2 || type == 3 || type == 4) {

			params.put("type", type);// 每页返回条数,1(20条默认),2(40条),3(60条),4(80条)

		}

		HashMap object = new HashMap();

		try {

			result = net(url, params, "GET");

			object = JsonUtils.objectFromJson(result, HashMap.class);

			if (Integer.parseInt(object.get("error_code").toString()) == 0) {

			} else {

				System.out.println(object.get("error_code") + ":" + object.get("reason"));

				throw new CoreException(object.get("error_code").toString(), object.get("reason").toString());

			}

		} catch (Exception e) {

			e.printStackTrace();

			throw e;

		}

		return (HashMap) object.get("result");

	}

	/**
	 *
	 * @param strUrl
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param method
	 *            请求方法
	 * @return 网络请求字符串
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String net(String strUrl, Map params, String method) throws Exception {

		HttpURLConnection conn = null;

		BufferedReader reader = null;

		String rs = null;

		try {
			StringBuffer sb = new StringBuffer();
			if (method == null || method.equals("GET")) {
				strUrl = strUrl + "?" + urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if (method == null || method.equals("GET")) {
				conn.setRequestMethod("GET");
			} else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params != null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlencode(params));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	// 将map型转为请求参数型
	@SuppressWarnings("rawtypes")
	public static String urlencode(Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
