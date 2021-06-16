package com.lgd.ctpro.orderDetailGengrator.entity;

import java.util.ArrayList;
import java.util.List;

import com.lgd.ctpro.core.entity.CtproOrder;

public class GeneOrderEntity {
	
	private CtproOrder ctproOrder;
	List<GeneTaskEntity> geneTaskList;
	
	public GeneOrderEntity() {
		super();
		this.geneTaskList = new ArrayList<GeneTaskEntity>();
	}
	
	public GeneOrderEntity(CtproOrder ctproOrder, List<GeneTaskEntity> geneTaskList) {
		super();
		this.ctproOrder = ctproOrder;
		this.geneTaskList = geneTaskList;
	}
	
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

	@Override
	public String toString() {
		return "GeneOrderEntity [ctproOrder=" + ctproOrder + ", geneTaskList=" + geneTaskList + "]";
	}
}
