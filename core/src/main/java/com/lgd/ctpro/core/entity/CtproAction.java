package com.lgd.ctpro.core.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.core.intr.CtproActionIntr;

/*
 * 动作对象
 */
public class CtproAction implements CtproActionIntr{
	
	private static Logger logger = LogManager.getLogger(CtproAction.class);

	private String actionid;
	private String actionMsg = "默认动作";

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
		
		logger.debug("任务动作执行：" + Thread.currentThread());
	}

	@Override
	public String toString() {
		return "CtproAction [actionid=" + actionid + ", actionMsg=" + actionMsg + "]";
	}
}
