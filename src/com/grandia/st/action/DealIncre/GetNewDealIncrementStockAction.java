package com.grandia.st.action.DealIncre;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bocom.jump.bp.core.Action;
import com.bocom.jump.bp.core.Context;
import com.bocom.jump.bp.core.CoreException;
import com.bocom.jump.bp.service.sqlmap.SqlMap;
import com.grandia.st.common.STOCKConstant;
import com.grandia.st.common.STOCKErrorCode;
import com.grandia.st.frame.STOCKCommonAction;
import com.grandia.st.util.STOCKFactorySql;
import com.grandia.st.util.WorkingDayUtils;

public class GetNewDealIncrementStockAction extends STOCKCommonAction implements Action {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(GetNewDealIncrementStockAction.class);

	public void execute(Context ctx) throws Exception {

		this.process(ctx);

	}

	public void init(Context ctx) throws Exception {

		if (ctx.getData("dealdate") == null || ctx.getData("dealdate").toString().isEmpty()) {

			SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");

			ctx.setData("dealdate", form.format(new Date()));

		}

		if (!WorkingDayUtils.isWorkingDay((String) ctx.getData("dealdate"))) {

			throw new CoreException(STOCKErrorCode.NOT_WORKING_DAY);

		}

		ctx.setData("preWorkingDay", WorkingDayUtils.getPreWorkingDay((String) ctx.getData("dealdate")));

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void handle(Context ctx) throws Exception {

		SqlMap sqlMap = STOCKFactorySql.getSqlMap();

		ArrayList<HashMap> rerultList = (ArrayList) sqlMap
				.queryForList("GetNewDealIncrementStockAction.queryDealIncreStockList", ctx.getDataMap());

		if (rerultList == null || rerultList.isEmpty()) {

			return;

		}

		for (HashMap resultMap : rerultList) {

			resultMap.put("status", STOCKConstant.STOCK_STATUSE_ONLINE);

			Integer num = sqlMap.queryForObject("GetNewDealIncrementStockAction.queryExistInfo", resultMap);

			if (num > 0) {

				continue;

			} else {

				Integer newStockFlag = sqlMap.queryForObject("GetNewDealIncrementStockAction.queryDealDate", resultMap);

				if (newStockFlag < 20) {

					continue;

				} else {

					sqlMap.insert("GetNewDealIncrementStockAction.insertDealIncreStockInfo", resultMap);

				}

			}

		}

	}

}