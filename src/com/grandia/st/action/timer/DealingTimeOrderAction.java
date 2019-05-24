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

public class DealingTimeOrderAction extends STOCKCommonAction implements Action {

	private static Logger logger = LoggerFactory.getLogger(DealingTimeOrderAction.class);

	public void execute(Context ctx) throws Exception {

		process(ctx);

	}

	public void execute() throws Exception {

		ContextEx defaultContextEx = new DefaultContextEx();

		process(defaultContextEx);

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

		if (!WorkingDayUtils.isWorkingDay(dealdate).booleanValue()) {

			throw new CoreException(STOCKErrorCode.NOT_WORKING_DAY);

		}

		DefaultDispatcher stCoreDispatcher = (DefaultDispatcher) STOCKFactoryBean.getDispatcher("coreDispatcher");

		DefaultContextEx defaultContextEx1 = new DefaultContextEx();

		defaultContextEx1.setData("dealdate", dealdate);

		logger.debug("GetTmpStockDealInfoProcess start");

		defaultContextEx1.setProcessId("GetTmpStockDealInfoProcess");

		stCoreDispatcher.dispatch(defaultContextEx1);

		DefaultContextEx defaultContextEx2 = new DefaultContextEx();

		defaultContextEx2.setData("dealdate", dealdate);

		logger.debug("GetTmpDealIncrementStockProcess start");

		defaultContextEx2.setProcessId("GetTmpDealIncrementStockProcess");

		stCoreDispatcher.dispatch(defaultContextEx2);

	}

}