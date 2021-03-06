package com.lgd.ctpro.orderDetailGengrator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.actionImplentor.impl.IsPrimeAction;
import com.lgd.ctpro.core.entity.CtproAction;
import com.lgd.ctpro.core.entity.CtproExecution;
import com.lgd.ctpro.core.entity.CtproOrder;
import com.lgd.ctpro.core.entity.CtproTask;
import com.lgd.ctpro.core.service.CtproCoreServiceManager;
import com.lgd.ctpro.orderDetailGengrator.entity.GeneActionEntity;
import com.lgd.ctpro.orderDetailGengrator.entity.GeneExecutionEntity;
import com.lgd.ctpro.orderDetailGengrator.entity.GeneOrderEntity;
import com.lgd.ctpro.orderDetailGengrator.entity.GeneTaskEntity;
import com.lgd.ctpro.orderDetailGengrator.tools.OrderDetailGenerator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	private static Logger logger = LogManager.getLogger(AppTest.class);

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {

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
		ctproExecution2.setStep(2);
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
		
		logger.debug("要增加的对象信息：" + geneOrderEntity);

		// 生成命令数据结构
		OrderDetailGenerator.generateOrderDetail(geneOrderEntity);

		CtproOrder ctproOrderRes = CtproCoreServiceManager.getInstance().getOrder(ctproOrder);
		logger.debug("查找的命令信息：" + ctproOrderRes);

		CtproTask ctproTaskSer1 = new CtproTask();
		ctproTaskSer1.setTaskid(ctproOrderRes.getOrderTasks().get(0));
		CtproTask ctproTaskRes1 = CtproCoreServiceManager.getInstance().getTask(ctproTaskSer1);
		logger.debug("查找的任务信息1：" + ctproTaskRes1);

		CtproTask ctproTaskSer2 = new CtproTask();
		ctproTaskSer2.setTaskid(ctproOrderRes.getOrderTasks().get(1));
		CtproTask ctproTaskRes2 = CtproCoreServiceManager.getInstance().getTask(ctproTaskSer2);
		logger.debug("查找的任务信息2：" + ctproTaskRes2);
		
		CtproExecution ctproExecutionSer1 = new CtproExecution();
		ctproExecutionSer1.setExecutionId(ctproTaskRes1.getExecutionActions().get(0));
		CtproExecution ctproExecutionRes1 = CtproCoreServiceManager.getInstance().getExecution(ctproExecutionSer1);
		logger.debug("查找的执行信息1：" + ctproExecutionRes1);
		
		CtproExecution ctproExecutionSer2 = new CtproExecution();
		ctproExecutionSer2.setExecutionId(ctproTaskRes1.getExecutionActions().get(1));
		CtproExecution ctproExecutionRes2 = CtproCoreServiceManager.getInstance().getExecution(ctproExecutionSer2);
		logger.debug("查找的执行信息2：" + ctproExecutionRes2);
		
		CtproAction ctproActionSer1 = new CtproAction();
		ctproActionSer1.setActionid(ctproExecutionRes1.getExecutionAction());
		CtproAction ctproActionRes1 = CtproCoreServiceManager.getInstance().getAction(ctproActionSer1);
		logger.debug("查找的动作信息1：" + ctproActionRes1);
		
		CtproAction ctproActionSer2 = new CtproAction();
		ctproActionSer2.setActionid(ctproExecutionRes2.getExecutionAction());
		CtproAction ctproActionRes2 = CtproCoreServiceManager.getInstance().getAction(ctproActionSer2);
		logger.debug("查找的动作信息2：" + ctproActionRes2);
		
		
	}
}
