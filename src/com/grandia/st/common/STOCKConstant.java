package com.grandia.st.common;

public class STOCKConstant {

	/************************************************
	 * 
	 * 返回参数相关常量
	 * 
	 ************************************************ 
	 */

	public static final String PARAM_REPLY_INFO_KEY = "replyInformation";

	public static final String PARAM_REPLY_TYPE_KEY = "responseType";

	public static final String PARAM_REPLY_CODE_KEY = "responseCode";

	public static final String PARAM_REPLY_MESSAGE_KEY = "responseMessage";

	public static final String REPLY_TYPE_NORMAL = "N";

	public static final String REPLY_TYPE_ERROR = "E";

	public static final String REPLY_TYPE_WARN = "W";

	public static final String REPLY_TYPE_AUTHOR = "A";

	/************************************************
	 * 
	 * 与UI交互时json报文相关常量
	 * 
	 ************************************************ 
	 */
	public static final String DATA_CHANNSET = "UTF8";

	public static final String DATA_CHANNSET_STANDARD = "UTF-8";

	public static final String DATA_JSON_HREAD = "REQ_MESSAGE={\"REQ_BODY\":";

	public static final String DATA_JSON_BODY = ",\"REQ_HEAD\":{}}";

	public static final String DATA_RSP_BODY = "RSP_BODY";

	/************************************************
	 * 
	 * 通用数值相关常量
	 * 
	 ************************************************ 
	 */
	public static final String EMPTY_VALUE = "";

	public static final String POSITIVE_VALUE = "0";

	public static final String NEGATIVE_VALUE = "1";

	public static final String SEPARATOR_DEFAULT = "-";

	public static final String SEPARATOR_LOG = ":";

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final String SYSID = "10000001";

	public static final String NORMAL_VALUE = "000000";

	/************************************************
	 * 
	 * 业务信息相关数据常量
	 * 
	 ************************************************ 
	 */
	/* 聚合数据_沪股列表:http://www.juhe.cn/ */
	public static final String JH_ADDR_HS_STOCK_LIST = "http://web.juhe.cn:8080/finance/stock/shall";
	/* 聚合数据_深股列表 */
	public static final String JH_ADDR_SS_STOCK_LIST = "http://web.juhe.cn:8080/finance/stock/szall";
	/* 聚合数据_股票数据 */
	public static final String JH_ADDR_STOCK_INFO = "http://web.juhe.cn:8080/finance/stock/hs";
	/* 聚合数据_APPKEY */
	public static final String JH_APPKEY = "4de58ee75d7f010036ce20adc28b0631";
	/* 行政区划 */
	public static final String ADM_DIV_CODE = "000000";
	/* GBK */
	public static final String DATA_CHANNSET_GBK = "GBK";
	/* GBK */
	public static final String DATA_CHANNSET_GB2312 = "gb2312";
	/* 股票状态_上线 */
	public static final String STOCK_STATUSE_ONLINE = "O";
	/* 股票状态_下线 */
	public static final String STOCK_STATUSE_OFFLINE = "F";
	/* 股票状态_完成 */
	public static final String STOCK_STATUSE_COMPLETE = "C";
	/* 股票状态_新股 */
	public static final String STOCK_STATUSE_NEW = "N";

}