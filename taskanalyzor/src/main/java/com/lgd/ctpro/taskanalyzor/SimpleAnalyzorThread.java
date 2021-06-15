package com.lgd.ctpro.taskanalyzor;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.core.entity.CtproExecution;
import com.lgd.ctpro.core.entity.CtproOrder;
import com.lgd.ctpro.core.entity.CtproTask;
import com.lgd.ctpro.core.service.CtproCoreServiceManager;
import com.lgd.ctpro.taskexecutor.OrderStackManager;

/**
 * 
 * @ClassName: SimpleThread
 * @Description: simple thread for thread pool
 * @author liguodong
 * @date 2018年5月7日 下午4:25:22
 *
 */
public class SimpleAnalyzorThread extends Thread {
	
	private static Logger logger = LogManager.getLogger(SimpleAnalyzorThread.class);

	private boolean runningFlag;
	private String orderToDeal;

	public boolean isRunning() {
		return runningFlag;
	}

	public synchronized void setRunning(boolean flag) {
		runningFlag = flag;
		if (flag) {
			this.notify();
		}
	}

	public String getOrderToDeal() {
		return orderToDeal;
	}

	public void setOrderToDeal(String orderToDeal) {
		this.orderToDeal = orderToDeal;
	}

	public SimpleAnalyzorThread(int threadNumber) {
		runningFlag = false;
		logger.debug("thread " + threadNumber + " started!");
	}

	public synchronized void run() {
		try {
			while (true) {
				if (!runningFlag) {
					this.wait();
				} else {
					// 执行任务
					CtproOrder ctproOrder = DealingOrderStackManager.getInstance().getSpecifiedOrder(orderToDeal);
					if(ctproOrder != null){
						OrderStackManager.getInstance().addOrder(ctproOrder);
					}
					setRunning(false);
				}
			}
		} catch (InterruptedException e) {
			logger.error(e.getStackTrace());
		}
	}
}
