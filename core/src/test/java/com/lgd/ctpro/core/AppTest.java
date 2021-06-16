package com.lgd.ctpro.core;

import java.util.ArrayList;
import java.util.List;

import com.lgd.ctpro.core.entity.CtproAction;
import com.lgd.ctpro.core.entity.CtproExecution;
import com.lgd.ctpro.core.entity.CtproOrder;
import com.lgd.ctpro.core.entity.CtproTask;
import com.lgd.ctpro.core.service.CtproCoreServiceManager;
import com.lgd.ctpro.core.tools.EncryptTool;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue(true);
        
        CtproOrder ctproOrder = new CtproOrder();
        ctproOrder.setOrderMsg("第个一命令");
        String orderid = EncryptTool.getMD5ByBase64(ctproOrder.getOrderMsg());
        System.out.println("第一个命令的ID：" + orderid);
        
        CtproTask ctproTask = new CtproTask();
        ctproTask.setTaskMsg("第一个任务");
        String taskid = EncryptTool.getMD5ByBase64(ctproTask.getTaskMsg());
        System.out.println("第一个任务的ID：" + taskid);
        
        CtproTask ctproTask2 = new CtproTask();
        ctproTask2.setTaskMsg("第二个任务");
        String taskid2 = EncryptTool.getMD5ByBase64(ctproTask2.getTaskMsg());
        System.out.println("第二个任务的ID：" + taskid2);
        
        
        CtproExecution ctproExecution = new CtproExecution();
        ctproExecution.setExecutionMsg("第一个执行对象");
        String executionid = EncryptTool.getMD5ByBase64(ctproExecution.getExecutionMsg());
        System.out.println("第一个执行对象的ID：" + executionid);
        
        CtproExecution ctproExecution2 = new CtproExecution();
        ctproExecution2.setExecutionMsg("第二个执行对象");
        String executionid2 = EncryptTool.getMD5ByBase64(ctproExecution2.getExecutionMsg());
        System.out.println("第二个执行对象的ID：" + executionid2);
        
        CtproAction ctproAction = new CtproAction();
        String actionid = EncryptTool.getMD5ByBase64(ctproAction.getActionMsg());
        System.out.println("第一个执行动作对象的ID：" + actionid);
        
        CtproAction ctproAction2 = new CtproAction();
        String actionid2 = EncryptTool.getMD5ByBase64(ctproAction2.getActionMsg());
        System.out.println("第二个执行动作对象的ID：" + actionid2);
        
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
        
        
        CtproCoreServiceManager.getInstance().addAction(ctproAction);
        CtproCoreServiceManager.getInstance().addAction(ctproAction2);
        CtproCoreServiceManager.getInstance().addExecution(ctproExecution);
        CtproCoreServiceManager.getInstance().addExecution(ctproExecution2);
        CtproCoreServiceManager.getInstance().addTask(ctproTask);
        CtproCoreServiceManager.getInstance().addTask(ctproTask2);
        CtproCoreServiceManager.getInstance().addOrder(ctproOrder);
        
        
        List<CtproTask> ctproTasks = CtproCoreServiceManager.getInstance().getOrderRelatedTasks(ctproOrder);
        System.out.println("第一个命令的关联任务列表：" + ctproTasks);
        
        for(int i=0; i<ctproTasks.size(); i++){
        	CtproTask ctproTaskItem = ctproTasks.get(i);
        	List<CtproExecution> ctproExecutions = CtproCoreServiceManager.getInstance().getTaskRelatedExecutions(ctproTaskItem);
            System.out.println("第"+ (i+1) +"个任务的关联执行列表：" + ctproExecutions);
            
            CtproCoreServiceManager.getInstance().dealAllExecutions(ctproExecutions);
        }
        
    }
}
