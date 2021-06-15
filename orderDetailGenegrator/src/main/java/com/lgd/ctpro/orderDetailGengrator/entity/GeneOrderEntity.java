package com.lgd.ctpro.orderDetailGengrator.entity;

import java.util.List;

import com.lgd.ctpro.core.entity.CtproOrder;

public class GeneOrderEntity {
	
	private CtproOrder ctproOrder;
	List<GeneTaskEntity> geneTaskList;
	
	public CtproOrder getCtproOrder() {
		return ctproOrder;
	}
	public void setCtproOrder(CtproOrder ctproOrder) {
		this.ctproOrder = ctproOrder;
	}
	public List<GeneTaskEntity> getGeneTaskList() {
		return geneTaskList;
	}
	public void setGeneTaskList(List<GeneTaskEntity> geneTaskList) {
		this.geneTaskList = geneTaskList;
	}
	
}
