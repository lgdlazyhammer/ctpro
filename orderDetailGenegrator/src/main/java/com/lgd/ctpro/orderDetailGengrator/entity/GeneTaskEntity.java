package com.lgd.ctpro.orderDetailGengrator.entity;

import java.util.ArrayList;
import java.util.List;

import com.lgd.ctpro.core.entity.CtproTask;

public class GeneTaskEntity {

	private CtproTask ctproTask;
	private List<GeneExecutionEntity> geneExecutionList;
	
	public GeneTaskEntity() {
		super();
		this.geneExecutionList = new ArrayList<GeneExecutionEntity>();
	}
	
	public GeneTaskEntity(CtproTask ctproTask, List<GeneExecutionEntity> geneExecutionList) {
		super();
		this.ctproTask = ctproTask;
		this.geneExecutionList = geneExecutionList;
	}
	
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

	@Override
	public String toString() {
		return "GeneTaskEntity [ctproTask=" + ctproTask + ", geneExecutionList=" + geneExecutionList + "]";
	}
}
