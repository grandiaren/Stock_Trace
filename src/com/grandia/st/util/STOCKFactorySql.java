package com.grandia.st.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bocom.jump.bp.service.sqlmap.SqlMap;

public class STOCKFactorySql  implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		STOCKFactorySql.applicationContext = applicationContext;
		
	}
	
	public static SqlMap getSqlMap(String productId) {
		
		return (SqlMap)STOCKFactorySql.applicationContext.getBean(productId);

	}
	
	public static SqlMap getSqlMap() {
		
		String sqlMap = "STOCKSqlMap";
		
		return (SqlMap)STOCKFactorySql.applicationContext.getBean(sqlMap);

	}

}