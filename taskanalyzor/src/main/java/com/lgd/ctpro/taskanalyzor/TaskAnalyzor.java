package com.lgd.ctpro.taskanalyzor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.core.entity.CtproOrder;
import com.lgd.ctpro.taskexecutor.OrderStackManager;

/**
 * 任务执行管理器
 * @author liguodong
 *
 */
public class TaskAnalyzor extends Thread{
	
	private static Logger logger = LogManager.getLogger(TaskAnalyzor.class);

	public boolean isOn;// 任务执行器是否开启
	
	// 开始任务执行器
	public void startAnalyzor(){
		isOn = true;
	}
	
	// 停止任务执行器
	public void stopAnalyzor(){
		isOn = false;
	}

	@Override
	public void run() {
		super.run();
		// 任务执行器开启情况下一直接收待处理命令并进行处理
		while(isOn){
			try {
				// 接收待处理命令
				String orderToDeal = DealingOrderStackManager.getInstance().getDealingOrderMsg();
				if(orderToDeal != null){
					AnalyzorThreadPoolManager.getInstance().process(orderToDeal);
				}
			} catch (Exception e) {
				logger.error(e.getStackTrace());
			}
			// 线程休眠100毫秒，避免资源利用率过高
			try {
				sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
