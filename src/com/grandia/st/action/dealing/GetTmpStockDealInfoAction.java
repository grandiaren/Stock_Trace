package com.grandia.st.action.dealing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.bocom.jump.bp.core.Action;
import com.bocom.jump.bp.core.Context;
import com.bocom.jump.bp.service.sqlmap.SqlMap;
import com.grandia.st.frame.STOCKCommonAction;
import com.grandia.st.util.STOCKFactoryBean;
import com.grandia.st.util.STOCKFactorySql;

public class GetTmpStockDealInfoAction extends STOCKCommonAction implements Action {

	private static Logger logger = LoggerFactory.getLogger(GetTmpStockDealInfoAction.class);

	public void execute(Context ctx) throws Exception {
		process(ctx);
	}

	public void init(Context ctx) throws Exception {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void handle(Context ctx) throws Exception {

		SqlMap sqlMap = STOCKFactorySql.getSqlMap();

		ThreadPoolTaskExecutor threadPool = (ThreadPoolTaskExecutor) STOCKFactoryBean.getDispatcher("threadPoolTask");

		sqlMap.delete("GetTmpStockDealInfoAction.clearStockList");

		List<List<Object>> resultList = splitList(sqlMap.queryForList("GetTmpStockDealInfoAction.queryStockList"), 200);

		List<FutureTask> taskList = new ArrayList<FutureTask>();

		for (List<Object> subList : resultList) {

			FutureTask myTask = new FutureTask(new GetTmpStockDealInfoTask(subList));

			threadPool.submit(myTask);

			taskList.add(myTask);
		}

		for (FutureTask task : taskList) {

			logger.debug((String) task.get());

		}

		logger.debug(
				"threadPool ActiveCount: " + threadPool.getActiveCount() + " ; threadPool: " + threadPool.toString());

	}

	private List<List<Object>> splitList(List<Object> list, int groupSize) {

		int length = list.size();

		int num = (length + groupSize - 1) / groupSize;

		List<List<Object>> newList = new ArrayList<List<Object>>(num);

		for (int i = 0; i < num; i++) {

			int fromIndex = i * groupSize;

			int toIndex = ((i + 1) * groupSize < length) ? ((i + 1) * groupSize) : length;

			newList.add(list.subList(fromIndex, toIndex));

		}

		logger.debug("StockList is splited! Orin is " + length + " sub is " + newList.size());

		return newList;

	}

}