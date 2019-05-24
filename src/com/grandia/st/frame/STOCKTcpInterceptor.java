package com.grandia.st.frame;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bocom.jump.bp.channel.ChannelContext;
import com.bocom.jump.bp.channel.ChannelInterceptor;
import com.bocom.jump.bp.channel.http.support.JsonUtils;
import com.bocom.jump.bp.core.ContextEx;

public class STOCKTcpInterceptor implements ChannelInterceptor<Object, Object> {

	private Logger logger = LoggerFactory.getLogger(STOCKTcpInterceptor.class);

	@SuppressWarnings("unchecked")
	public void onRequest(ChannelContext<Object, Object> arg0, ContextEx arg1) {

		HashMap<String, Object> dataMap = new HashMap<String, Object>();

		String jsonString = "";

		try {

			jsonString = new String((byte[]) arg0.getRequestPayload(), "UTF-8");

			dataMap = JsonUtils.objectFromJson(new String((byte[]) arg0.getRequestPayload(), "UTF-8").substring(8),
					HashMap.class);

		} catch (UnsupportedEncodingException e) {

			logger.error("decode json msg error. jsonString:" + jsonString);

			e.printStackTrace();

		}

		arg1.setProcessId((String) dataMap.get("stockProcessId"));

		arg1.setDataMap(dataMap);

	}

	@SuppressWarnings("rawtypes")
	public void onResponse(ChannelContext arg0, ContextEx arg1, Throwable arg2) {

		HashMap dataMap = (HashMap) arg1.getDataMap();

		logger.debug("dataMap:" + dataMap);

		String jsonString = "";

		try {

			jsonString = new String(JsonUtils.jsonFromObject(dataMap, "UTF8"), "UTF-8");

			int length = jsonString.getBytes("UTF8").length;

			String len = convertLength(length);

			byte[] result = (len + jsonString).getBytes("UTF-8");

			arg0.setResponsePayload(result);

		} catch (UnsupportedEncodingException e) {

			logger.error("encode json msg error. jsonString:" + jsonString);

			e.printStackTrace();

		}

	}

	private String convertLength(int length) {

		StringBuffer str = new StringBuffer("" + length);

		while (str.length() < 8) {

			str.insert(0, '0');

		}

		return str.toString();

	}

}
