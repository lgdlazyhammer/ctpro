package com.lgd.ctpro.orderDetailGengrator.entity;

import java.util.List;

import com.lgd.ctpro.core.entity.CtproTask;

public class GeneTaskEntity {

	private CtproTask ctproTask;
	private List<GeneExecutionEntity> geneExecutionList;
	
	public CtproTask getCtproTask() {
		return ctproTask;
	}
	public void setCtproTask(CtproTask ctproTask) {
		this.ctproTask = ctproTask;
	}
	public List<GeneExecutionEntity> getGeneExecutionList() {
		return geneExecutionList;
	}
	public void setGeneExecutionList(List<GeneExecutionEntity> geneExecutionList) {
		this.geneExecutionList = geneExecutionList;
	}
	
}
