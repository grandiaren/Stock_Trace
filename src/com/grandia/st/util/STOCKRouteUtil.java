package com.grandia.st.util;

import java.util.HashMap;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bocom.jump.bp.core.CoreException;
import com.grandia.st.common.STOCKConstant;
import com.grandia.st.common.STOCKErrorCode;

public class STOCKRouteUtil {

	private static Logger logger = LoggerFactory.getLogger(STOCKRouteUtil.class);

	@SuppressWarnings("rawtypes")
	public static String getRouteInfo(HashMap reqMap) throws CoreException {

		String processId = (String) reqMap.get("stockProcessId");

		logger.debug("processId = " + processId);

		HashMap routInfo = STOCKFactoryCache.getRouteInfo(processId);

		if (routInfo != null) {

			String[] routeList = routInfo.get("tcpInfo").toString().split(STOCKConstant.SEPARATOR_LOG);

			Random rand = new Random();

			int index = rand.nextInt(routeList.length) % (routeList.length);

			logger.debug("routInfo != null " + routeList[index]);

			return (String) routeList[index];// tcpInfo 格式 形如
												// localhost-45101-1000

		} else {

			logger.info("routInfo == null ");

			CoreException exception = new CoreException(STOCKErrorCode.ROUTE_CONN_ERROR_CODE);

			throw exception;

		}

	}

}
