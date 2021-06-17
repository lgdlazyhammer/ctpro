package com.lgd.ctpro.taskexecutor.entity;

import java.util.List;
import java.util.Vector;

import com.lgd.ctpro.core.entity.CtproOrder;
import com.lgd.ctpro.core.entity.CtproTask;

public class OrderTaskDisplayEntity {

	private String threadId;
	volatile private CtproOrder ctproOrder;
	volatile private List<CtproTask> taskList;
	
	public OrderTaskDisplayEntity() {
		super();
		this.taskList = new Vector<CtproTask>();
	}
	
	public OrderTaskDisplayEntity(String threadId, CtproOrder ctproOrder, List<CtproTask> taskList) {
		super();
		this.threadId = threadId;
		this.ctproOrder = ctproOrder;
		this.taskList = taskList;
	}
	
	public String getThreadId() {
		return threadId;
	}
	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}
	public CtproOrder getCtproOrder() {
		return ctproOrder;
	}
	public void setCtproOrder(CtproOrder ctproOrder) {
		this.ctproOrder = ctproOrder;
	}
	public List<CtproTask> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<CtproTask> taskList) {
		this.taskList = taskList;
	}
}
