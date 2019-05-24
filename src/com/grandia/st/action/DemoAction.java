package com.grandia.st.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bocom.jump.bp.core.Action;
import com.bocom.jump.bp.core.Context;
import com.grandia.st.frame.STOCKCommonAction;
import com.grandia.st.util.WorkingDayUtils;

public class DemoAction extends STOCKCommonAction implements Action {

	private static Logger logger = LoggerFactory.getLogger(DemoAction.class);

	public void execute(Context ctx) throws Exception {

		this.process(ctx);

	}

	public void init(Context ctx) throws Exception {

	}

	public void handle(Context ctx) throws Exception {

		String testStr = ctx.getData("date");

		logger.debug("is working day : " + WorkingDayUtils.isWorkingDay(testStr));

		logger.debug("pre working day is : " + WorkingDayUtils.getPreWorkingDay(testStr));

	}

}