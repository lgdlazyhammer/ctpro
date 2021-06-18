package com.lgd.ctpro.taskexecutor;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.core.entity.CtproTask;
import com.lgd.ctpro.taskexecutor.entity.OrderTaskDisplayEntity;

public class TestDisplayOrderTaskRecorder extends Thread {
	
	private static Logger logger = LogManager.getLogger(TestDisplayOrderTaskRecorder.class);

	public void run() {
		
		while (true) {
			
			List<OrderTaskDisplayEntity> resList = OrderTaskStatusRecorder.getInstance().getOrderStatusList();
			for(int i=0; i<resList.size(); i++){
				OrderTaskDisplayEntity orderTaskDisplayEntity = resList.get(i);
				logger.debug(orderTaskDisplayEntity.getCtproOrder());
				
				List<CtproTask> taskList = orderTaskDisplayEntity.getTaskList();
				for(int j=0; j<taskList.size(); j++){
					CtproTask ctproTask = taskList.get(j);
					logger.debug(ctproTask);
				}
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				logger.error(e.getStackTrace());
			}
		}
	}
}
