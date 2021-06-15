package com.lgd.ctpro.taskexecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.actionImplentor.impl.IsPrimeAction;
import com.lgd.ctpro.core.entity.CtproAction;
import com.lgd.ctpro.core.entity.CtproExecution;
import com.lgd.ctpro.core.entity.CtproOrder;
import com.lgd.ctpro.core.entity.CtproTask;
import com.lgd.ctpro.core.service.CtproCoreServiceManager;
import com.lgd.ctpro.core.tools.EncryptTool;

/**
 * 
 * @ClassName: TestThreadPool
 * @Description: test thread manager
 * @author liguodong
 * @date 2018年5月7日 下午5:47:52
 *
 */
public class TestThreadPool {
	
	private static Logger logger = LogManager.getLogger(TestThreadPool.class);

	public static void main(String[] args) {

        CtproOrder ctproOrder = new CtproOrder();
        ctproOrder.setOrderMsg("第个一命令");
        String orderid = EncryptTool.getMD5ByBase64(ctproOrder.getOrderMsg());
        logger.debug("第一个命令的ID：" + orderid);
        
        CtproTask ctproTask = new CtproTask();
        ctproTask.setTaskMsg("第一个任务");
        String taskid = EncryptTool.getMD5ByBase64(ctproTask.getTaskMsg());
        logger.debug("第一个任务的ID：" + taskid);
        ctproTask.setStep(1);
        
        CtproTask ctproTask2 = new CtproTask();
        ctproTask2.setTaskMsg("第二个任务");
        String taskid2 = EncryptTool.getMD5ByBase64(ctproTask2.getTaskMsg());
        logger.debug("第二个任务的ID：" + taskid2);
        ctproTask2.setStep(2);
        
        CtproExecution ctproExecution = new CtproExecution();
        ctproExecution.setExecutionMsg("第一个执行对象");
        String executionid = EncryptTool.getMD5ByBase64(ctproExecution.getExecutionMsg());
        logger.debug("第一个执行对象的ID：" + executionid);
        
        CtproExecution ctproExecution2 = new CtproExecution();
        ctproExecution2.setExecutionMsg("第二个执行对象");
        String executionid2 = EncryptTool.getMD5ByBase64(ctproExecution2.getExecutionMsg());
        logger.debug("第二个执行对象的ID：" + executionid2);
        
        CtproAction ctproAction = new CtproAction();
        String actionid = EncryptTool.getMD5ByBase64(ctproAction.getActionMsg());
        logger.debug("第一个执行动作对象的ID：" + actionid);
        
        CtproAction ctproAction2 = new IsPrimeAction();
        String actionid2 = EncryptTool.getMD5ByBase64(ctproAction2.getActionMsg());
        logger.debug("第二个执行动作对象的ID：" + actionid2);
        
        ctproExecution.setExecutionAction(actionid);
        ctproExecution2.setExecutionAction(actionid2);
        
        
        List<String> executionList = new ArrayList<String>();
        executionList.add(executionid);
        executionList.add(executionid2);
        ctproTask.setExecutionActions(executionList);
        List<String> taskList = new ArrayList<String>();
        taskList.add(taskid);
        taskList.add(taskid2);
        ctproOrder.setOrderTasks(taskList);
        ctproOrder.setOrderTyp("1");
        
        CtproCoreServiceManager.getInstance().addAction(ctproAction);
        CtproCoreServiceManager.getInstance().addAction(ctproAction2);
        CtproCoreServiceManager.getInstance().addExecution(ctproExecution);
        CtproCoreServiceManager.getInstance().addExecution(ctproExecution2);
        CtproCoreServiceManager.getInstance().addTask(ctproTask);
        CtproCoreServiceManager.getInstance().addTask(ctproTask2);
        CtproCoreServiceManager.getInstance().addOrder(ctproOrder);

		/*try {
			ThreadPoolManager manager = new ThreadPoolManager(10);
			manager.process(ctproOrder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
        OrderStackManager.getInstance().addOrder(ctproOrder);
        TaskExecutor executorThread = new TaskExecutor();
        executorThread.startExecutor();
        executorThread.start();
        
	}

}
