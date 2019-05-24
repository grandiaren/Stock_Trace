package com.grandia.st.action.dealing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bocom.jump.bp.service.sqlmap.SqlMap;
import com.grandia.st.util.HTTPManager;
import com.grandia.st.util.HttpConnManager;
import com.grandia.st.util.STOCKFactorySql;

public class GetTmpStockDealInfoTask extends Object implements Callable<Object> {

	private static Logger logger = LoggerFactory.getLogger(GetTmpStockDealInfoTask.class);

	private static String[] resultKeyArr = { "stock_name", "open_price", "old_end_price", "price", "high_price",
			"low_price", "CA_buy_price", "CA_sell_price", "deal_share", "deal_amount", "first_buy_share",
			"first_buy_price", "second_buy_share", "second_buy_price", "third_buy_share", "third_buy_price",
			"fourth_buy_share", "fourth_buy_price", "fifth_buy_share", "fifth_buy_price", "first_sell_share",
			"first_sell_price", "second_sell_share", "second_sell_price", "third_sell_share", "third_sell_price",
			"fourth_sell_share", "fourth_sell_price", "fifth_sell_share", "fifth_sell_price", "deal_date",
			"deal_time" };

	private List<Object> stockList;

	public GetTmpStockDealInfoTask(List<Object> paramList) {

		this.stockList = paramList;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String call() throws Exception {

		long temp = System.currentTimeMillis();

		SqlMap sqlMap = STOCKFactorySql.getSqlMap();

		for (Object requestMap : stockList) {

			String stockSymbol = ((HashMap) requestMap).get("stock_symbol").toString();

			HttpConnManager connManager = new HttpConnManager("http://hq.sinajs.cn/list=" + stockSymbol);

			String resultString = HTTPManager.sendData(connManager);

			logger.debug("resultString is : " + resultString);

			String result = resultString.substring(resultString.indexOf("\"") + 1, resultString.lastIndexOf("\""));

			logger.debug("result is : " + result);

			String[] resultArr = result.split(",");

			HashMap resultMap = new HashMap();

			for (int ix = 0; ix < resultKeyArr.length; ix++) {

				resultMap.put(resultKeyArr[ix], resultArr[ix]);

			}

			resultMap.putAll((Map) requestMap);

			logger.debug("resultMap is : " + resultMap);

			try {

				sqlMap.insert("GetTmpStockDealInfoAction.insertTmpStockDealInfo", resultMap);

			} catch (Throwable t) {

				logger.debug("insert failed. paramMap is : " + resultMap);

			}

		}

		Thread.currentThread().setName(getClass().getSimpleName());

		return "Thread: " + Thread.currentThread().getName() + " use " + (System.currentTimeMillis() - temp);

	}

}