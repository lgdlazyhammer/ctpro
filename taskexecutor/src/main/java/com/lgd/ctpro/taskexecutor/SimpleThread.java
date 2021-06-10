package com.lgd.ctpro.taskexecutor;

import java.util.List;

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
		System.out.println("thread " + threadNumber + " started!");
	}

	public synchronized void run() {
		try {
			while (true) {
				if (!runningFlag) {
					this.wait();
				} else {
					// 执行任务
		        	List<CtproExecution> ctproExecutions = CtproCoreServiceManager.getTaskRelatedExecutions(ctproTask);
		            CtproCoreServiceManager.dealAllExecutions(ctproExecutions);
					setRunning(false);
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
