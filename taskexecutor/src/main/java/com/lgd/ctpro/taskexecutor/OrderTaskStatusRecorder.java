package com.lgd.ctpro.taskexecutor;

import java.util.List;
import java.util.Vector;

import com.lgd.ctpro.taskexecutor.entity.OrderTaskDisplayEntity;

public class OrderTaskStatusRecorder {

	private static OrderTaskStatusRecorder instance;
	private static List<OrderTaskDisplayEntity> orderStatusList;
	
	public static synchronized OrderTaskStatusRecorder getInstance(){
		if(instance == null){
			orderStatusList = new Vector<OrderTaskDisplayEntity>();
			instance = new OrderTaskStatusRecorder();
		}
		return instance;
	}

	public List<OrderTaskDisplayEntity> getOrderStatusList() {
		return orderStatusList;
	}
	
}
