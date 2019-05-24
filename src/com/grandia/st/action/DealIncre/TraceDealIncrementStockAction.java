package com.grandia.st.action.DealIncre;

import java.text.DecimalFormat;
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

public class TraceDealIncrementStockAction extends STOCKCommonAction implements Action {

	private static Logger logger = LoggerFactory.getLogger(TraceDealIncrementStockAction.class);

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void handle(Context ctx) throws Exception {

		SqlMap sqlMap = STOCKFactorySql.getSqlMap();

		ArrayList<HashMap> newRerultList = (ArrayList) sqlMap
				.queryForList("TraceDealIncrementStockAction.queryNewDealIncreStockList", ctx.getDataMap());

		if (newRerultList == null || newRerultList.isEmpty()) {

		} else {

			for (HashMap requestMap : newRerultList) {

				requestMap.put("dealdate", ctx.getData("dealdate"));

				HashMap resultMap = sqlMap.queryForObject("TraceDealIncrementStockAction.queryStockDealInfo",
						requestMap);

				if (resultMap == null || resultMap.isEmpty()) {

					logger.debug("*detail:" + requestMap.get("stock_market") + STOCKConstant.SEPARATOR_LOG
							+ requestMap.get("stock_symbol") + STOCKConstant.SEPARATOR_LOG
							+ requestMap.get("stock_name") + STOCKConstant.SEPARATOR_LOG + requestMap.get("dealdate")
							+ STOCKConstant.SEPARATOR_LOG + " is not open to deal!");

					continue;

				} else {

					requestMap.put("buy_date", requestMap.get("dealdate"));

					requestMap.put("buy_price", resultMap.get("open_price"));

					requestMap.put("hold_price", resultMap.get("price"));

					requestMap.put("hold_change_precent", resultMap.get("change_percent"));

					requestMap.put("newStatus", requestMap.get("status"));

					double percent = Double.parseDouble((String) resultMap.get("change_percent"));

					if (percent < -5) {

						requestMap.put("newStatus", STOCKConstant.STOCK_STATUSE_OFFLINE);

					}

					requestMap.put("record_date", requestMap.get("dealdate"));

					sqlMap.update("TraceDealIncrementStockAction.updateNewDealIncreStockInfo", requestMap);

				}

			}

		}

		ArrayList<HashMap> rerultList = (ArrayList) sqlMap
				.queryForList("TraceDealIncrementStockAction.queryDealIncreStockList", ctx.getDataMap());

		if (rerultList == null || rerultList.isEmpty()) {

			return;

		} else {

			for (HashMap requestMap : rerultList) {

				Integer newStockFlag = sqlMap.queryForObject("TraceDealIncrementStockAction.queryDealDate", requestMap);

				if (newStockFlag < 20) {

					logger.debug("*detail:" + requestMap.get("stock_market") + STOCKConstant.SEPARATOR_LOG
							+ requestMap.get("stock_symbol") + STOCKConstant.SEPARATOR_LOG
							+ requestMap.get("stock_name") + " is a new stock!");

					requestMap.put("dealdate", ctx.getData("dealdate"));

					HashMap resultMap = sqlMap.queryForObject("TraceDealIncrementStockAction.queryStockDealInfo",
							requestMap);

					DecimalFormat decimalFormat = new DecimalFormat("0.000");

					double buyPrice = Double.parseDouble((String) requestMap.get("buy_price"));

					// double highPrice = Double.parseDouble(
					// (String) (resultMap.get("high_price") == null ? "0.000" :
					// resultMap.get("high_price")));
					//
					// double lowPrice = Double.parseDouble(
					// (String) (resultMap.get("low_price") == null ? "0.000" :
					// resultMap.get("low_price")));

					double price = Double
							.parseDouble((String) (resultMap.get("price") == null ? "0.000" : resultMap.get("price")));

					// double proPercent = (highPrice - buyPrice) / buyPrice *
					// 100;
					//
					// double lossPercent = (lowPrice - buyPrice) / buyPrice *
					// 100;

					double percent = (price - buyPrice) / buyPrice * 100;

					requestMap.put("newStatus", STOCKConstant.STOCK_STATUSE_NEW);

					requestMap.put("hold_price", decimalFormat.format(price));

					requestMap.put("hold_change_precent", decimalFormat.format(percent));

					requestMap.put("record_date", requestMap.get("dealdate"));

					sqlMap.update("TraceDealIncrementStockAction.updateDealIncreStockInfo", requestMap);

				} else {

					requestMap.put("dealdate", ctx.getData("dealdate"));

					HashMap resultMap = sqlMap.queryForObject("TraceDealIncrementStockAction.queryStockDealInfo",
							requestMap);

					if (resultMap == null || resultMap.isEmpty()) {

						logger.debug("*detail:" + requestMap.get("stock_market") + STOCKConstant.SEPARATOR_LOG
								+ requestMap.get("stock_symbol") + STOCKConstant.SEPARATOR_LOG
								+ requestMap.get("stock_name") + STOCKConstant.SEPARATOR_LOG
								+ requestMap.get("dealdate") + STOCKConstant.SEPARATOR_LOG + " is not open to deal!");

						continue;

					} else {

						DecimalFormat decimalFormat = new DecimalFormat("0.000");

						double buyPrice = Double.parseDouble((String) requestMap.get("buy_price"));

						double highPrice = Double.parseDouble((String) resultMap.get("high_price"));

						double lowPrice = Double.parseDouble((String) resultMap.get("low_price"));

						double price = Double.parseDouble((String) resultMap.get("price"));

						double proPercent = (highPrice - buyPrice) / buyPrice * 100;

						double lossPercent = (lowPrice - buyPrice) / buyPrice * 100;

						double percent = (price - buyPrice) / buyPrice * 100;

						if (proPercent >= 10) {

							double holdPrice = buyPrice * 1.1;

							requestMap.put("newStatus", STOCKConstant.STOCK_STATUSE_COMPLETE);

							requestMap.put("hold_price", decimalFormat.format(holdPrice));

							requestMap.put("hold_change_precent", decimalFormat.format(proPercent));

						} else if (lossPercent <= -5) {

							double holdPrice = buyPrice * 0.95;

							requestMap.put("newStatus", STOCKConstant.STOCK_STATUSE_OFFLINE);

							requestMap.put("hold_price", decimalFormat.format(holdPrice));

							requestMap.put("hold_change_precent", decimalFormat.format(lossPercent));

						} else {

							requestMap.put("newStatus", requestMap.get("status"));

							requestMap.put("hold_price", resultMap.get("price"));

							requestMap.put("hold_change_precent", decimalFormat.format(percent));

						}

						requestMap.put("record_date", requestMap.get("dealdate"));

						sqlMap.update("TraceDealIncrementStockAction.updateDealIncreStockInfo", requestMap);

					}

				}

			}

		}

	}

}