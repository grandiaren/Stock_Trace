package com.grandia.st.action.dealing;

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
import com.grandia.st.common.STOCKErrorCode;
import com.grandia.st.frame.STOCKCommonAction;
import com.grandia.st.util.STOCKFactorySql;
import com.grandia.st.util.WorkingDayUtils;

public class GetTmpDealIncrementStockAction extends STOCKCommonAction implements Action {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(GetTmpDealIncrementStockAction.class);

	public void execute(Context ctx) throws Exception {

		process(ctx);

	}

	public void init(Context ctx) throws Exception {

		if (ctx.getData("dealdate") == null || ctx.getData("dealdate").toString().isEmpty()) {

			SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");

			ctx.setData("dealdate", form.format(new Date()));

		}

		if (!WorkingDayUtils.isWorkingDay((String) ctx.getData("dealdate")).booleanValue()) {

			throw new CoreException(STOCKErrorCode.NOT_WORKING_DAY);

		}

		ctx.setData("preWorkingDay", WorkingDayUtils.getPreWorkingDay((String) ctx.getData("dealdate")));

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void handle(Context ctx) throws Exception {

		SqlMap sqlMap = STOCKFactorySql.getSqlMap();

		ArrayList<HashMap> rerultList = (ArrayList) sqlMap
				.queryForList("GetTmpDealIncrementStockAction.queryDealIncreStockList", ctx.getDataMap());

		if (rerultList == null || rerultList.isEmpty()) {

			return;

		}

		for (HashMap resultMap : rerultList) {

			resultMap.put("status", "O");

			Integer num = (Integer) sqlMap.queryForObject("GetTmpDealIncrementStockAction.queryExistInfo", resultMap);

			if (num.intValue() > 0) {

				continue;

			}

			Integer newStockFlag = (Integer) sqlMap.queryForObject("GetTmpDealIncrementStockAction.queryDealDate",
					resultMap);

			if (newStockFlag.intValue() < 20) {

				continue;

			}

			sqlMap.insert("GetTmpDealIncrementStockAction.insertDealIncreStockInfo", resultMap);

		}

		sqlMap.delete("GetTmpDealIncrementStockAction.filtLargeDeal", ctx.getDataMap());

	}

}