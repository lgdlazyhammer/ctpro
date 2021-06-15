package com.lgd.ctpro.orderDetailGengrator.tools;

import java.util.ArrayList;
import java.util.List;

import com.lgd.ctpro.core.service.CtproCoreServiceManager;
import com.lgd.ctpro.orderDetailGengrator.entity.GeneActionEntity;
import com.lgd.ctpro.orderDetailGengrator.entity.GeneExecutionEntity;
import com.lgd.ctpro.orderDetailGengrator.entity.GeneOrderEntity;
import com.lgd.ctpro.orderDetailGengrator.entity.GeneTaskEntity;

public class OrderDetailGenerator {

	public static void generateOrderDetail(GeneOrderEntity geneOrderEntity){
		
		// 增加任务
		List<GeneTaskEntity> geneTaskEntityList = geneOrderEntity.getGeneTaskList();
		// 任务列表
		List<String> taskList = new ArrayList<String>();
		for(int i=0; i<geneTaskEntityList.size(); i++){
			GeneTaskEntity geneTaskEntity = geneTaskEntityList.get(i);
			
			List<GeneExecutionEntity> geneExecutionEntityList = geneTaskEntity.getGeneExecutionList();
			// 执行列表
			List<String> executionList = new ArrayList<String>();
			for(int j=0; j<geneExecutionEntityList.size(); j++){
				GeneExecutionEntity geneExecutionEntity = geneExecutionEntityList.get(i);
				// 获取行为
				GeneActionEntity geneActionEntity = geneExecutionEntity.getGeneActionEntity();
				// 增加行为
				String actionId = CtproCoreServiceManager.addAction(geneActionEntity.getCtproAction());

				geneExecutionEntity.getCtproExecution().setExecutionAction(actionId);
				// 增加执行
				String executionId = CtproCoreServiceManager.addExecution(geneExecutionEntity.getCtproExecution());
				executionList.add(executionId);
			}
			
			// 任务的执行列表
			geneTaskEntity.getCtproTask().setExecutionActions(executionList);
			// 增加命令
			String taskId = CtproCoreServiceManager.addTask(geneTaskEntity.getCtproTask());
			taskList.add(taskId);
		}

		// 设置任务列表
		geneOrderEntity.getCtproOrder().setOrderTasks(taskList);
		// 增加命令
		CtproCoreServiceManager.addOrder(geneOrderEntity.getCtproOrder());
	}
}
