package com.grandia.st.action;

import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bocom.jump.bp.core.Action;
import com.bocom.jump.bp.core.Context;
import com.bocom.jump.bp.service.sqlmap.SqlMap;
import com.grandia.st.frame.STOCKCommonAction;
import com.grandia.st.util.HTTPManager;
import com.grandia.st.util.HttpConnManager;
import com.grandia.st.util.STOCKFactorySql;

public class GetStockDealInfoAction extends STOCKCommonAction implements Action {

	private static Logger logger = LoggerFactory.getLogger(GetStockDealInfoAction.class);

	private static String[] resultKeyArr = { "stock_name", "open_price", "old_end_price", "price", "high_price",
			"low_price", "CA_buy_price", "CA_sell_price", "deal_share", "deal_amount", "first_buy_share",
			"first_buy_price", "second_buy_share", "second_buy_price", "third_buy_share", "third_buy_price",
			"fourth_buy_share", "fourth_buy_price", "fifth_buy_share", "fifth_buy_price", "first_sell_share",
			"first_sell_price", "second_sell_share", "second_sell_price", "third_sell_share", "third_sell_price",
			"fourth_sell_share", "fourth_sell_price", "fifth_sell_share", "fifth_sell_price", "deal_date",
			"deal_time" };

	public void execute(Context ctx) throws Exception {

		this.process(ctx);

	}

	public void init(Context ctx) throws Exception {

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void handle(Context ctx) throws Exception {

		SqlMap sqlMap = STOCKFactorySql.getSqlMap();

		List<HashMap> resultList = sqlMap.queryForList("GetStockDealInfoAction.queryStockList");

		for (HashMap requestMap : resultList) {

			String stockSymbol = requestMap.get("stock_symbol").toString();

			HttpConnManager connManager = new HttpConnManager("http://hq.sinajs.cn/list=" + stockSymbol);

			String resultString = HTTPManager.sendData(connManager);

			logger.debug("resultString is : " + resultString);

			String result = resultString.substring(resultString.indexOf("\"") + 1, resultString.lastIndexOf("\""));

			logger.debug("result is : " + result);

			String[] resultArr = result.split(",");

			HashMap resultMap = new HashMap();

			for (int ix = 0; ix < resultKeyArr.length; ++ix) {

				resultMap.put(resultKeyArr[ix], resultArr[ix]);

			}

			resultMap.putAll(requestMap);

			logger.debug("resultMap is : " + resultMap);

			try {

				sqlMap.insert("GetStockDealInfoAction.insertStockDealInfo", resultMap);

			} catch (Throwable e) {

				logger.debug("insert failed. paramMap is : " + resultMap);

			}

		}

	}

}