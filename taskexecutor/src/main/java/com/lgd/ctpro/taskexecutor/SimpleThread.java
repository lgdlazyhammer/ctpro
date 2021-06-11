package com.lgd.ctpro.taskexecutor;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.core.entity.CtproExecution;
import com.lgd.ctpro.core.entity.CtproTask;
import com.lgd.ctpro.core.service.CtproCoreServiceManager;

/**
 * 
 * @ClassName: SimpleThread
 * @Description: simple thread for thread pool
 * @author liguodong
 * @date 2018年5月7日 下午4:25:22
 *
 */
public class SimpleThread extends Thread {
	
	private static Logger logger = LogManager.getLogger(SimpleThread.class);

	private boolean runningFlag;
	private CtproTask ctproTask;

	public boolean isRunning() {
		return runningFlag;
	}

	public synchronized void setRunning(boolean flag) {
		runningFlag = flag;
		if (flag) {
			this.notify();
		}
	}

	public CtproTask getCtproTask() {
		return this.ctproTask;
	}

	public void setCtproTask(CtproTask param) {
		ctproTask = param;
	}

	public SimpleThread(int threadNumber) {
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
					// 任务处理中
					ctproTask.setStatus("processing");
		        	List<CtproExecution> ctproExecutions = CtproCoreServiceManager.getTaskRelatedExecutions(ctproTask);
		            CtproCoreServiceManager.dealAllExecutions(ctproExecutions);
					setRunning(false);
					// 任务处理完成
					ctproTask.setStatus("done");
				}
			}
		} catch (InterruptedException e) {
			logger.error(e.getStackTrace());
		}
	}
}
