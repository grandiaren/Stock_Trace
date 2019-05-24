package com.grandia.st.action;

import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bocom.jump.bp.core.Action;
import com.bocom.jump.bp.core.Context;
import com.bocom.jump.bp.service.sqlmap.SqlMap;
import com.grandia.st.frame.STOCKCommonAction;
import com.grandia.st.util.STOCKFactorySql;
import com.grandia.st.util.STOCKJuHeManager;

public class GetSZStockListAction extends STOCKCommonAction implements Action {

	private static Logger logger = LoggerFactory.getLogger(GetSZStockListAction.class);

	public void execute(Context ctx) throws Exception {

		this.process(ctx);

	}

	public void init(Context ctx) throws Exception {

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void handle(Context ctx) throws Exception {

		SqlMap sqlMap = STOCKFactorySql.getSqlMap();

		Boolean endFlag = false;

		int page = 1;

		while (!endFlag) {

			HashMap resultMap = STOCKJuHeManager.getSZStockList("a", page, 4);

			logger.debug("resultMap : " + resultMap);

			ArrayList<HashMap> resultList = (ArrayList) resultMap.get("data");

			if (resultList.size() < Integer.parseInt(resultMap.get("num").toString())) {

				endFlag = true;

			} else if (Integer.parseInt(resultMap.get("totalCount").toString()) == page
					* Integer.parseInt(resultMap.get("num").toString())) {

				endFlag = true;

			}

			for (HashMap listMem : resultList) {

				HashMap paramMap = new HashMap();

				paramMap.put("stock_market", "china");

				paramMap.put("stock_code", listMem.get("code"));

				paramMap.put("stock_name", listMem.get("name"));

				paramMap.put("stock_symbol", listMem.get("symbol"));

				paramMap.put("price_change", listMem.get("pricechange"));

				paramMap.put("change_percent", listMem.get("changepercent"));

				paramMap.put("turn_over_ratio", listMem.get("turnoverratio"));

				paramMap.put("market_cap", listMem.get("mktcap"));

				logger.debug("paramMap : " + paramMap);

				sqlMap.insert("GetSZStockListAction.insertStockList", paramMap);

			}

			page++;

		}

	}

}