package com.lgd.ctpro.core.entity;

import java.util.List;

/**
 * 
 * @author liguodong 命令与执行任务关联对象
 */
public class CtproOrder {

	private String orderId;
	private String orderMsg;
	private List<String> orderTasks;
	private String orderTyp;// 命令类型，顺序1 或异步 2

	public CtproOrder() {
		super();
	}

	public CtproOrder(String orderId, String orderMsg, List<String> orderTasks, String orderTyp) {
		super();
		this.orderId = orderId;
		this.orderMsg = orderMsg;
		this.orderTasks = orderTasks;
		this.orderTyp = orderTyp;
	}

	public String getOrderMsg() {
		return orderMsg;
	}

	public void setOrderMsg(String orderMsg) {
		this.orderMsg = orderMsg;
	}

	public List<String> getOrderTasks() {
		return orderTasks;
	}

	public void setOrderTasks(List<String> orderTasks) {
		this.orderTasks = orderTasks;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderTyp() {
		return orderTyp;
	}

	public void setOrderTyp(String orderTyp) {
		this.orderTyp = orderTyp;
	}

	@Override
	public String toString() {
		return "CtproOrder [orderId=" + orderId + ", orderMsg=" + orderMsg + ", orderTasks=" + orderTasks
				+ ", orderTyp=" + orderTyp + "]";
	}
	
}
