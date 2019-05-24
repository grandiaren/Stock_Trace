package com.grandia.st.common;

public class STOCKErrorCode {

	/************************************************
	 * 
	 * 定义此产品模块中使用的所有错误码
	 * 
	 ************************************************ 
	 */

	/** 未知报错 */
	public static final String DEFEND_ERROR_CODE = "GW1000";

	/** 报文转换失败 */
	public static final String MESSAGE_TRANS_FAIL = "GW2000";

	/** 映射失败 */
	public static final String MAPPING_ERROR_CODE = "GW3000";

	/** 路由失败 */
	public static final String ROUTE_CONN_ERROR_CODE = "GW4000";

	/** 发送超时 */
	public static final String ROUTE_TIMEOUT_ERROR_CODE = "GW4001";

	/** JSON转换失败 */
	public static final String ROUTE_JSON_ERROR_CODE = "GW2001";

	/** JSON转换失败 */
	public static final String PARSE_XML_ERROR_CODE = "GW2002";

	/************************************************
	 * 
	 * 业务信息相关错误
	 * 
	 ************************************************ 
	 */
	/* 非工作日 */
	public static final String NOT_WORKING_DAY = "ST0001";

}
