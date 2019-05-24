package com.grandia.st.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class STOCKFactoryBean  implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		STOCKFactoryBean.applicationContext = applicationContext;
		
	}
	
	public static Object getDispatcher(String beanName) {	
		
		return STOCKFactoryBean.applicationContext.getBean(beanName);

	}

}
