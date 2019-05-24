package com.grandia.st.util;

import java.util.concurrent.ThreadFactory;

import com.grandia.st.common.STOCKConstant;

public class STOCKFactoryThread implements ThreadFactory {

	private String poolName = STOCKConstant.EMPTY_VALUE;
	
	public STOCKFactoryThread(String poolName) {
		
		this.poolName = poolName;
		
	}

	public Thread newThread(Runnable r) {
		
		Thread thread = new Thread(r);
		
		thread.setName(this.poolName+STOCKConstant.SEPARATOR_DEFAULT+thread.getId());
		
		return thread;
		
	}

}
