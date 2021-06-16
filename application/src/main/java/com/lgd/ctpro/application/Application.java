package com.lgd.ctpro.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.actionImplentor.impl.IsPrimeAction;
import com.lgd.ctpro.core.entity.CtproAction;
import com.lgd.ctpro.core.entity.CtproExecution;
import com.lgd.ctpro.core.entity.CtproOrder;
import com.lgd.ctpro.core.entity.CtproTask;
import com.lgd.ctpro.orderDetailGengrator.entity.GeneActionEntity;
import com.lgd.ctpro.orderDetailGengrator.entity.GeneExecutionEntity;
import com.lgd.ctpro.orderDetailGengrator.entity.GeneOrderEntity;
import com.lgd.ctpro.orderDetailGengrator.entity.GeneTaskEntity;
import com.lgd.ctpro.orderDetailGengrator.tools.OrderDetailGenerator;
import com.lgd.ctpro.serilizeExecutor.FileSerilizor;
import com.lgd.ctpro.serilizeExecutor.SerlizorIntr;
import com.lgd.ctpro.taskanalyzor.DealingOrderStackManager;
import com.lgd.ctpro.taskanalyzor.TaskAnalyzor;
import com.lgd.ctpro.taskexecutor.TaskExecutor;

/**
 * System Entrance
 *
 */
public class Application {
	
	private static Logger logger = LogManager.getLogger(Application.class);
	
	public static void main(String[] args) {
		
		GeneOrderEntity geneOrderEntity = new GeneOrderEntity();
		CtproOrder ctproOrder = new CtproOrder();
		ctproOrder.setOrderTyp("1");
		ctproOrder.setOrderMsg("第一个命令");
		geneOrderEntity.setCtproOrder(ctproOrder);
		
		GeneTaskEntity geneTaskEntity = new GeneTaskEntity();
		CtproTask ctproTask = new CtproTask();
		ctproTask.setTaskMsg("第一个命令的第一个任务");
		ctproTask.setStep(1);
		geneTaskEntity.setCtproTask(ctproTask);
		
		GeneTaskEntity geneTaskEntity2 = new GeneTaskEntity();
		CtproTask ctproTask2 = new CtproTask();
		ctproTask2.setTaskMsg("第一个命令的第二个任务");
		ctproTask2.setStep(2);
		geneTaskEntity2.setCtproTask(ctproTask2);
		
		geneOrderEntity.getGeneTaskList().add(geneTaskEntity);
		geneOrderEntity.getGeneTaskList().add(geneTaskEntity2);
		
		
		GeneExecutionEntity geneExecutionEntity = new GeneExecutionEntity();
		CtproExecution ctproExecution = new CtproExecution();
		ctproExecution.setExecutionMsg("执行一");
		ctproExecution.setStep(1);
		geneExecutionEntity.setCtproExecution(ctproExecution);
		
		GeneExecutionEntity geneExecutionEntity2 = new GeneExecutionEntity();
		CtproExecution ctproExecution2 = new CtproExecution();
		ctproExecution2.setExecutionMsg("执行二");
		ctproExecution2.setStep(1);
		geneExecutionEntity2.setCtproExecution(ctproExecution2);
		
		geneTaskEntity.getGeneExecutionList().add(geneExecutionEntity);
		geneTaskEntity.getGeneExecutionList().add(geneExecutionEntity2);
		
		geneTaskEntity2.getGeneExecutionList().add(geneExecutionEntity);
		geneTaskEntity2.getGeneExecutionList().add(geneExecutionEntity2);
		
		
		GeneActionEntity geneActionEntity = new GeneActionEntity();
		CtproAction ctproAction = new CtproAction();
		geneActionEntity.setCtproAction(ctproAction);
		
		GeneActionEntity geneActionEntity2 = new GeneActionEntity();
		CtproAction ctproAction2 = new IsPrimeAction();
		geneActionEntity2.setCtproAction(ctproAction2);
		
		geneExecutionEntity.setGeneActionEntity(geneActionEntity);
		geneExecutionEntity2.setGeneActionEntity(geneActionEntity2);
		
		// 生成命令数据结构
		OrderDetailGenerator.generateOrderDetail(geneOrderEntity);
		
		// 关机时将数据序列化
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		        synchronized (Application.class) {
		        	SerlizorIntr serlizorIntr = new FileSerilizor();
		        	serlizorIntr.serilize();
		        }
		    }
		});
		
		// 动作执行器开启
		TaskExecutor executorThread = new TaskExecutor();
        executorThread.startExecutor();
        executorThread.start();
		
        // 动作收集器开启
        TaskAnalyzor taskAnalyzor = new TaskAnalyzor();
        taskAnalyzor.startAnalyzor();
        taskAnalyzor.start();
        
        while(true){
        	DealingOrderStackManager.getInstance().addDealingOrderMsg("第一个命令");
        	logger.debug("任务分发中");
        	try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
	}
}
