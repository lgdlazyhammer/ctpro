package com.lgd.ctpro.desktopApp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.taskanalyzor.DealingOrderStackManager;

public class MockSendCommandThread extends Thread {
	
	private static Logger logger = LogManager.getLogger(MockSendCommandThread.class);

	public boolean isOn;// 任务执行器是否开启
	
	// 开始任务执行器
	public void startMockSender(){
		isOn = true;
	}
	
	// 停止任务执行器
	public void stopMockSender(){
		isOn = false;
	}

	public void run() {
		while (isOn) {
        	DealingOrderStackManager.getInstance().addDealingOrderMsg("第一个命令");
        	logger.debug("任务分发中");
        	try {
				Thread.sleep(3000);
			} catch (InterruptedException ee) {
				logger.error(ee.getStackTrace());
			}
		}
	}
}
