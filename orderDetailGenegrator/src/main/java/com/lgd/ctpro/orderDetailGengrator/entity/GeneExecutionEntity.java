package com.lgd.ctpro.orderDetailGengrator.entity;

import com.lgd.ctpro.core.entity.CtproExecution;

public class GeneExecutionEntity {

	private CtproExecution ctproExecution;
	private GeneActionEntity geneActionEntity;
	
	public CtproExecution getCtproExecution() {
		return ctproExecution;
	}
	public void setCtproExecution(CtproExecution ctproExecution) {
		this.ctproExecution = ctproExecution;
	}
	public GeneActionEntity getGeneActionEntity() {
		return geneActionEntity;
	}
	public void setGeneActionEntity(GeneActionEntity geneActionEntity) {
		this.geneActionEntity = geneActionEntity;
	}
	
}
