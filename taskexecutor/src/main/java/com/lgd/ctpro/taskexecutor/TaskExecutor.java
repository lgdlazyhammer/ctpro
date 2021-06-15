package com.lgd.ctpro.taskexecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.core.entity.CtproOrder;

/**
 * 任务执行管理器
 * @author liguodong
 *
 */
public class TaskExecutor extends Thread{
	
	private static Logger logger = LogManager.getLogger(TaskExecutor.class);

	public boolean isOn;// 任务执行器是否开启
	
	// 开始任务执行器
	public void startExecutor(){
		isOn = true;
	}
	
	// 停止任务执行器
	public void stopExecutor(){
		isOn = false;
	}

	@Override
	public void run() {
		super.run();
		// 任务执行器开启状态下一直处理待接收任务
		while(isOn){
			CtproOrder ctproOrder = OrderStackManager.getInstance().getOrder();
			if(ctproOrder != null){
				try {
					ThreadPoolManager.getInstance().process(ctproOrder);
				} catch (Exception e) {
					logger.error(e.getStackTrace());
				}
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
