package com.grandia.st.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import com.bocom.jump.bp.channel.http.support.JsonUtils;
import com.grandia.st.common.STOCKConstant;

public class STOCKActionTest {

//	private String url = "http://localhost:8080/STOCK_TRACE/";
	
	private String url = "http://139.196.127.33:8080/STOCK_TRACE/";

	@SuppressWarnings("rawtypes")
	private HashMap caseParam = new HashMap();

	private String actionName = "";

	@SuppressWarnings("rawtypes")
	public HashMap runAction() {

		HashMap rspMap = new HashMap();

		try {

			long temp = System.currentTimeMillis();

			System.out.println("STOCKActionTest runAction start...");

			System.out.println("STOCKActionTest runAction. action is: " + this.getActionName());

			System.out.println("STOCKActionTest runAction. caseParam is: " + this.getCaseParam());

			String jsonStr = new String(JsonUtils.jsonFromObject(this.getCaseParam(), STOCKConstant.DATA_CHANNSET),
					STOCKConstant.DATA_CHANNSET);

			HttpConnManager connManager = new HttpConnManager(url + this.getActionName());

			rspMap = (HashMap) HTTPManager.sendData(connManager, jsonStr);

			System.out.println("STOCKActionTest runAction finished. rspMap is : " + rspMap);

			System.out.println("STOCKActionTest runAction finished. use : " + (System.currentTimeMillis() - temp));

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		}

		return rspMap;

	}

	@SuppressWarnings("rawtypes")
	public HashMap getCaseParam() {

		return caseParam;

	}

	@SuppressWarnings("rawtypes")
	public void setCaseParam(HashMap caseParam) {

		this.caseParam = caseParam;

	}

	public String getActionName() {

		return actionName;

	}

	public void setActionName(String actionName) {

		this.actionName = actionName;

	}

}
