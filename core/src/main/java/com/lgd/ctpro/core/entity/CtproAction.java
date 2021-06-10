package com.lgd.ctpro.core.entity;
/*
 * 动作对象
 */
public class CtproAction {

	private String actionid;
	private String actionMsg;

	public String getActionid() {
		return actionid;
	}

	public void setActionid(String actionid) {
		this.actionid = actionid;
	}

	public String getActionMsg() {
		return actionMsg;
	}

	public void setActionMsg(String actionMsg) {
		this.actionMsg = actionMsg;
	}
	
	public void executeAction(){
		
		System.out.println("任务动作执行：" + Thread.currentThread());
	}
	
}
