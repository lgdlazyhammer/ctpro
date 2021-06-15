package com.lgd.ctpro.serilizeExecutor;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.core.entity.CtproAction;
import com.lgd.ctpro.core.entity.CtproExecution;
import com.lgd.ctpro.core.entity.CtproOrder;
import com.lgd.ctpro.core.entity.CtproTask;
import com.lgd.ctpro.core.service.CtproCoreServiceManager;
import com.lgd.ctpro.core.tools.EncryptTool;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Logger logger = LogManager.getLogger(App.class);
	
    public static void main( String[] args )
    {

        CtproOrder ctproOrder = new CtproOrder();
        ctproOrder.setOrderMsg("第一个命令");
        String orderid = EncryptTool.getMD5ByBase64(ctproOrder.getOrderMsg());
        logger.debug("第一个命令的ID：" + orderid);
        
        CtproOrder ctproOrder2 = new CtproOrder();
        ctproOrder2.setOrderMsg("第二个命令");
        String orderid2 = EncryptTool.getMD5ByBase64(ctproOrder2.getOrderMsg());
        logger.debug("第二个命令的ID：" + orderid2);
        
        CtproOrder ctproOrder3 = new CtproOrder();
        ctproOrder3.setOrderMsg("第三个命令");
        String orderid3 = EncryptTool.getMD5ByBase64(ctproOrder3.getOrderMsg());
        logger.debug("第三个命令的ID：" + orderid3);
        
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
        
        CtproAction ctproAction2 = new CtproAction();
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
        CtproCoreServiceManager.getInstance().addOrder(ctproOrder2);
        CtproCoreServiceManager.getInstance().addOrder(ctproOrder3);
        
        /*byte[] data = SerializeUtils.serialize(ctproOrder, CtproOrder.class);
		for (byte b : data) {
			System.out.print(b);
		}
		logger.debug("反序列化");
		CtproOrder ctproOrder2 = SerializeUtils.deSerialize(data, CtproOrder.class);
		logger.debug(ctproOrder2);*/
		
        SerlizorIntr serlizorIntr = new FileSerilizor();
        serlizorIntr.serilize();
        
    }
}
