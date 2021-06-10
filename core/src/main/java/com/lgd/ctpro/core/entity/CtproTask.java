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
	private String status;// processing 处理中，done 处理完成

	public CtproTask() {
		super();
	}
	
	public CtproTask(String taskid, String taskMsg, List<String> executionActions, int step, String status) {
		super();
		this.taskid = taskid;
		this.taskMsg = taskMsg;
		this.executionActions = executionActions;
		this.step = step;
		this.status = status;
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

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CtproTask [taskid=" + taskid + ", taskMsg=" + taskMsg + ", executionActions=" + executionActions
				+ ", step=" + step + ", status=" + status + "]";
	}
	
}
