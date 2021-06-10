package com.lgd.ctpro.core.entity;

import java.util.List;

/*
 * 任务对象
 */
public class CtproTask {

	private String taskid;
	private String taskMsg;
	private List<String> executionActions;
	private int step;

	public CtproTask() {
		super();
	}

	public CtproTask(String taskid, String taskMsg, List<String> executionActions) {
		super();
		this.taskid = taskid;
		this.taskMsg = taskMsg;
		this.executionActions = executionActions;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getTaskMsg() {
		return taskMsg;
	}

	public void setTaskMsg(String taskMsg) {
		this.taskMsg = taskMsg;
	}

	public List<String> getExecutionActions() {
		return executionActions;
	}

	public void setExecutionActions(List<String> executionActions) {
		this.executionActions = executionActions;
	}

	@Override
	public String toString() {
		return "CtproTask [taskid=" + taskid + ", taskMsg=" + taskMsg + ", executionActions=" + executionActions + "]";
	}
}
