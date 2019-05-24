package com.grandia.st.action.DealIncre.large.deal;

import java.text.SimpleDateFormat;
import java.util.Date;
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

public class GetNewDealIncrementStockLargeDealAction extends STOCKCommonAction implements Action {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(GetNewDealIncrementStockLargeDealAction.class);

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

	}

	public void handle(Context ctx) throws Exception {

		SqlMap sqlMap = STOCKFactorySql.getSqlMap();

		sqlMap.insert("GetNewDealIncrementStockLargeDealAction.insertDealIncreStockInfo", ctx.getDataMap());

	}

}