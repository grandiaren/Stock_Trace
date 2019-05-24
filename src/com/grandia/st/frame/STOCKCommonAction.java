package com.grandia.st.frame;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bocom.jump.bp.core.Context;
import com.bocom.jump.bp.core.CoreException;
import com.bocom.jump.bp.core.CoreRuntimeException;
import com.grandia.st.common.STOCKConstant;
import com.grandia.st.common.STOCKErrorCode;
import com.grandia.st.util.STOCKFactoryCache;

public abstract class STOCKCommonAction {

	private Logger logger = LoggerFactory.getLogger(STOCKCommonAction.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void process(Context ctx) throws Exception {

		String formatLog = new String();

		if (ctx.getProcessId() == null || ctx.getRequestId() == null) {

			formatLog = "CPSS_Schedule_Start";

		} else {

			formatLog = ctx.getRequestId() + STOCKConstant.SEPARATOR_LOG + ctx.getProcessId();

		}

		logger.debug("*detail:" + formatLog + STOCKConstant.SEPARATOR_LOG + ctx.getDataMap());

		HashMap replyInformation = new HashMap();

		String result = STOCKConstant.REPLY_TYPE_ERROR;

		try {

			init(ctx);

			handle(ctx);

			replyInformation.put(STOCKConstant.PARAM_REPLY_TYPE_KEY, STOCKConstant.REPLY_TYPE_NORMAL);

			replyInformation.put(STOCKConstant.PARAM_REPLY_CODE_KEY, STOCKConstant.SYSID + STOCKConstant.NORMAL_VALUE);

			result = STOCKConstant.REPLY_TYPE_NORMAL;

		} catch (Throwable e) {

			this.handleException(replyInformation, e, formatLog, (HashMap) ctx.getDataMap());

			HashMap exceptionInfo = new HashMap();

			exceptionInfo.put("ctxInfo", ctx.getDataMap());

			exceptionInfo.put("exception", e);

			this.saveException(exceptionInfo);

			Exception exception = new Exception(e);

			throw exception;

		} finally {

			HashMap dataMap = (HashMap) ctx.getDataMap();

			dataMap.putAll(replyInformation);

			ctx.getDataMapDirectly().clear();

			ctx.setDataMap(dataMap);

			logger.info("*ended:" + formatLog + STOCKConstant.SEPARATOR_LOG + result + STOCKConstant.SEPARATOR_LOG
					+ (System.currentTimeMillis() - ctx.getTimestamp()));

		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void handleException(HashMap replyInformation, Throwable e, String formatLog, HashMap dataMap) {

		replyInformation.put(STOCKConstant.PARAM_REPLY_TYPE_KEY, STOCKConstant.REPLY_TYPE_ERROR);

		String errorCode = STOCKConstant.EMPTY_VALUE;

		String errorInfo = STOCKConstant.EMPTY_VALUE;

		if (e instanceof CoreException) {

			errorCode = ((CoreException) e).getCode();

			errorInfo = (String) STOCKFactoryCache.getErrorInfo(errorCode).get("info");

			e.printStackTrace();

		} else if (e instanceof CoreRuntimeException) {

			errorCode = ((CoreRuntimeException) e).getCode();

			errorInfo = ((CoreRuntimeException) e).getDefaultMessage();

			e.printStackTrace();

		} else {

			errorCode = STOCKErrorCode.DEFEND_ERROR_CODE;

			errorInfo = "未知错误";

			e.printStackTrace();

		}

		replyInformation.put(STOCKConstant.PARAM_REPLY_CODE_KEY, STOCKConstant.SYSID + errorCode);

		replyInformation.put(STOCKConstant.PARAM_REPLY_MESSAGE_KEY, errorInfo);

		logger.error("*error:" + formatLog + STOCKConstant.SEPARATOR_LOG + errorCode + STOCKConstant.SEPARATOR_LOG
				+ errorInfo + STOCKConstant.SEPARATOR_LOG + dataMap);

	}

	public abstract void init(Context ctx) throws Exception;

	public abstract void handle(Context ctx) throws Exception;

	@SuppressWarnings("rawtypes")
	private void saveException(HashMap exceptionInfo) {

	}

}
