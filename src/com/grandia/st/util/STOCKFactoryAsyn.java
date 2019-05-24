package com.grandia.st.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class STOCKFactoryAsyn {
	
	private static Logger logger = LoggerFactory.getLogger(STOCKFactoryAsyn.class);

	private static ExecutorService asynInvokerExecutor = null;

	public static void init(){	

		logger.info("##############################");

		logger.info("STOCKFactoryAsyn init start.");

		asynInvokerExecutor = Executors.newFixedThreadPool(Integer.parseInt(STOCKConfigUtil.getProp("STOCK.ASYN.INVOKE.THREADPOOL.SIZE")));
		
		logger.info("STOCKFactoryAsyn init finish.");

		logger.info("##############################");		 

	}

	public static ExecutorService getExecutor(){

		return asynInvokerExecutor;

	}

	public static void destroy(){	

		logger.info("##############################");

		logger.info("STOCKFactoryAsyn destroy start.");

		asynInvokerExecutor.shutdown();

		logger.info("STOCKFactoryAsyn destroy finish.");

		logger.info("##############################");

	}
}
