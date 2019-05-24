package com.grandia.st.action.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bocom.jump.bp.core.Action;
import com.bocom.jump.bp.core.Context;
import com.bocom.jump.bp.core.ContextEx;
import com.bocom.jump.bp.core.CoreException;
import com.bocom.jump.bp.core.impl.DefaultContextEx;
import com.bocom.jump.bp.core.impl.DefaultDispatcher;
import com.grandia.st.common.STOCKErrorCode;
import com.grandia.st.frame.STOCKCommonAction;
import com.grandia.st.util.STOCKFactoryBean;
import com.grandia.st.util.WorkingDayUtils;

public class TimeOrderAction extends STOCKCommonAction implements Action {

	private static Logger logger = LoggerFactory.getLogger(TimeOrderAction.class);

	public void execute(Context ctx) throws Exception {

		this.process(ctx);

	}

	public void execute() throws Exception {

		ContextEx ctx = new DefaultContextEx();

		this.process(ctx);

	}

	public void init(Context ctx) throws Exception {

	}

	public void handle(Context ctx) throws Exception {

		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");

		String dealdate = form.format(new Date());

		if (ctx.getData("dealdate") != null && !ctx.getData("dealdate").toString().isEmpty()) {

			if (!dealdate.equals(ctx.getData("dealdate").toString().trim())) {

				dealdate = ctx.getData("dealdate").toString().trim();

			}

		}

		if (!WorkingDayUtils.isWorkingDay(dealdate)) {

			throw new CoreException(STOCKErrorCode.NOT_WORKING_DAY);

		}

		DefaultDispatcher stCoreDispatcher = (DefaultDispatcher) STOCKFactoryBean.getDispatcher("coreDispatcher");

		Context SHStockListCtex = new DefaultContextEx();

		SHStockListCtex.setData("dealdate", dealdate);

		SHStockListCtex.setProcessId("GetSHStockListProcess");

		logger.debug("GetSHStockListProcess start");

		stCoreDispatcher.dispatch(SHStockListCtex);

		Context SZStockListCtex = new DefaultContextEx();

		SZStockListCtex.setData("dealdate", dealdate);

		logger.debug("GetSZStockListProcess start");

		SZStockListCtex.setProcessId("GetSZStockListProcess");

		stCoreDispatcher.dispatch(SZStockListCtex);

		Context stockDealInfoCtex = new DefaultContextEx();

		stockDealInfoCtex.setData("dealdate", dealdate);

		logger.debug("GetStockDealInfoProcess start");

		stockDealInfoCtex.setProcessId("GetStockDealInfoProcess");

		stCoreDispatcher.dispatch(stockDealInfoCtex);

		Context newDealIncrementStockCtex = new DefaultContextEx();

		newDealIncrementStockCtex.setData("dealdate", dealdate);

		logger.debug("GetNewDealIncrementStockProcess start");

		newDealIncrementStockCtex.setProcessId("GetNewDealIncrementStockProcess");

		stCoreDispatcher.dispatch(newDealIncrementStockCtex);

		Context traceDealIncrementStockCtex = new DefaultContextEx();

		traceDealIncrementStockCtex.setData("dealdate", dealdate);

		logger.debug("TraceDealIncrementStockProcess start");

		traceDealIncrementStockCtex.setProcessId("TraceDealIncrementStockProcess");

		stCoreDispatcher.dispatch(traceDealIncrementStockCtex);

		Context newDealIncrementStockLargeDealCtex = new DefaultContextEx();

		newDealIncrementStockLargeDealCtex.setData("dealdate", dealdate);

		logger.debug("GetNewDealIncrementStockLargeDealProcess start");

		newDealIncrementStockLargeDealCtex.setProcessId("GetNewDealIncrementStockLargeDealProcess");

		stCoreDispatcher.dispatch(newDealIncrementStockLargeDealCtex);

		Context traceDealIncrementStockLargeDealCtex = new DefaultContextEx();

		traceDealIncrementStockLargeDealCtex.setData("dealdate", dealdate);

		logger.debug("TraceDealIncrementStockLargeDealProcess start");

		traceDealIncrementStockLargeDealCtex.setProcessId("TraceDealIncrementStockLargeDealProcess");

		stCoreDispatcher.dispatch(traceDealIncrementStockLargeDealCtex);

	}

}