package com.lgd.ctpro.actionImplentor.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.actionImplentor.intrs.TestJnaLibrary;
import com.lgd.ctpro.core.entity.CtproAction;
import com.lgd.ctpro.core.intr.CtproActionIntr;

public class IsPrimeAction extends CtproAction implements CtproActionIntr{

	private static Logger logger = LogManager.getLogger(IsPrimeAction.class);

	private String actionid;
	private String actionMsg = "判断素数动作";

	public String getActionid() {
		return actionid;
	}

	public void setActionid(String actionid) {
		this.actionid = actionid;
	}
	
	public String getActionMsg() {
		return actionMsg;
	}

	// 执行任务
	public void executeAction(){

		boolean isPrime = TestJnaLibrary.INSTANCE.isPrime(6);
		logger.debug("是否是素数动作执行：isPrime = " + isPrime);
	}

	@Override
	public String toString() {
		return "IsPrimeAction [actionid=" + actionid + ", actionMsg=" + actionMsg + "]";
	}
}
