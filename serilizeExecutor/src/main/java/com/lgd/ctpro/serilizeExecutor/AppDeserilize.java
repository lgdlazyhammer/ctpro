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
public class AppDeserilize 
{
	private static Logger logger = LogManager.getLogger(AppDeserilize.class);
	
    public static void main( String[] args )
    {
        SerlizorIntr serlizorIntr = new FileSerilizor();
        serlizorIntr.deSerilize();
        
        CtproOrder ctproOrder = new CtproOrder();
        ctproOrder.setOrderMsg("第一个命令");

        CtproTask ctproTask = new CtproTask();
        ctproTask.setTaskMsg("第一个任务");
        
        CtproExecution ctproExecution = new CtproExecution();
        ctproExecution.setExecutionMsg("第一个执行对象");
        
        CtproAction ctproAction = new CtproAction();
        
        
        CtproOrder ctproOrderRes = CtproCoreServiceManager.getInstance().getOrder(ctproOrder);
        logger.debug("反序列化后的命令：" + ctproOrderRes);
        
        CtproTask ctproTask2 = CtproCoreServiceManager.getInstance().getTask(ctproTask);
        logger.debug("反序列化后的任务：" + ctproTask2);
        
        CtproExecution ctproExecution2 = CtproCoreServiceManager.getInstance().getExecution(ctproExecution);
        logger.debug("反序列化后的执行：" + ctproExecution2);
        
        CtproAction ctproAction2 = CtproCoreServiceManager.getInstance().getAction(ctproAction);
        logger.debug("反序列化后的动作：" + ctproAction2);
        
    }
}
