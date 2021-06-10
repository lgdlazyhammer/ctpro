package com.lgd.ctpro.core.entity;

/*
 * 执行对象
 */
public class CtproExecution {

	private String executionId;
	private String executionMsg;
	private String executionMethod;
	private String executionAction;
	private int step;

	public CtproExecution() {
		super();
	}

	public CtproExecution(String executionId, String executionMsg, String executionMethod, String executionAction,
			int step) {
		super();
		this.executionId = executionId;
		this.executionMsg = executionMsg;
		this.executionMethod = executionMethod;
		this.executionAction = executionAction;
		this.step = step;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getExecutionMsg() {
		return executionMsg;
	}

	public void setExecutionMsg(String executionMsg) {
		this.executionMsg = executionMsg;
	}

	public String getExecutionMethod() {
		return executionMethod;
	}

	public void setExecutionMethod(String executionMethod) {
		this.executionMethod = executionMethod;
	}

	public String getExecutionAction() {
		return executionAction;
	}

	public void setExecutionAction(String executionAction) {
		this.executionAction = executionAction;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	@Override
	public String toString() {
		return "CtproExecution [executionId=" + executionId + ", executionMsg=" + executionMsg + ", executionMethod="
				+ executionMethod + ", executionAction=" + executionAction + ", step=" + step + "]";
	}
}
