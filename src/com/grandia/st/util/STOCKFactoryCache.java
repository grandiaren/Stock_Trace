package com.grandia.st.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bocom.jump.bp.service.sqlmap.SqlMap;

public class STOCKFactoryCache {

	private static Logger logger = LoggerFactory.getLogger(STOCKFactoryCache.class);

	@SuppressWarnings("rawtypes")
	private static ConcurrentHashMap errorInfo = new ConcurrentHashMap();

	@SuppressWarnings("rawtypes")
	private static ConcurrentHashMap routeInfo = new ConcurrentHashMap();

	@SuppressWarnings("rawtypes")
	private static ConcurrentHashMap sufRouteInfo = new ConcurrentHashMap();

	public static void init() {

		logger.info("##############################");

		logger.info("STOCKFactoryCache init start.");

		initErrorInfo();

		// initRouteInfo();
		//
		// initSufInfo();

		logger.info("STOCKFactoryCache init finish.");

		logger.info("##############################");

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void initErrorInfo() {

		SqlMap sqlMap = STOCKFactorySql.getSqlMap();

		ArrayList errorList = (ArrayList) sqlMap.queryForList("STOCKFactoryParam.queryErrorList");

		for (int i = 0; i < errorList.size(); i++) {

			HashMap errorMap = (HashMap) errorList.get(i);

			errorInfo.put(errorMap.get("code"), errorMap);

		}

	}

	@SuppressWarnings({ "rawtypes" })
	public static HashMap getErrorInfo(String key) {

		return (HashMap) errorInfo.get(key);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void initRouteInfo() {

		SqlMap sqlMap = STOCKFactorySql.getSqlMap();

		ArrayList routeList = (ArrayList) sqlMap.queryForList("STOCKFactoryParam.queryRouteList");

		for (int i = 0; i < routeList.size(); i++) {

			HashMap errorMap = (HashMap) routeList.get(i);

			routeInfo.put(errorMap.get("rule"), errorMap);

		}

	}

	@SuppressWarnings("rawtypes")
	public static HashMap getRouteInfo(String rule) {

		return (HashMap) routeInfo.get(rule);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void initSufInfo() {

		SqlMap sqlMap = STOCKFactorySql.getSqlMap();

		ArrayList sufRouteList = (ArrayList) sqlMap.queryForList("STOCKFactoryParam.querySufRuleList");

		for (int i = 0; i < sufRouteList.size(); i++) {

			HashMap sufruleMap = (HashMap) sufRouteList.get(i);

			sufRouteInfo.put(sufruleMap.get("rule"), sufruleMap);

		}

	}

	@SuppressWarnings("rawtypes")
	public static HashMap getSufInfo(String rule) {

		return (HashMap) sufRouteInfo.get(rule);

	}

	@SuppressWarnings("rawtypes")
	public static void destroy() {

		logger.info("##############################");

		logger.info("STOCKFactoryParam destroy start.");

		errorInfo = new ConcurrentHashMap();

		routeInfo = new ConcurrentHashMap();

		sufRouteInfo = new ConcurrentHashMap();

		logger.info("STOCKFactoryParam destroy finish.");

		logger.info("##############################");

	}
}
