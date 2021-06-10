package com.lgd.ctpro.taskexecutor;

import java.util.List;
import java.util.Vector;

import com.lgd.ctpro.core.entity.CtproOrder;
import com.lgd.ctpro.core.entity.CtproTask;
import com.lgd.ctpro.core.service.CtproCoreServiceManager;

/**
 * 
 * @ClassName: ThreadPoolManager
 * @Description: thread pool manager
 * @author liguodong
 * @date 2018年5月7日 下午4:40:01
 *
 */
public class ThreadPoolManager {

	private int maxThread;
	public Vector vector;

	public void setMaxThread(int threadCount) {
		this.maxThread = threadCount;
	}

	public ThreadPoolManager(int threadCount) {

		setMaxThread(threadCount);
		System.out.println("start thread pool ......");
		vector = new Vector();

		for (int i = 0; i < threadCount; i++) {
			SimpleThread thread = new SimpleThread(i);
			vector.add(thread);
			thread.start();
		}
	}

	public void process(CtproOrder ctproOrder) {

		if("1".equals(ctproOrder.getOrderTyp())){
			// 按顺序执行所有任务
		}else if("2".equals(ctproOrder.getOrderTyp())){
			// 所有待处理的任务列表
	        List<CtproTask> ctproTasks = CtproCoreServiceManager.getOrderRelatedTasks(ctproOrder);
	        System.out.println("命令的关联任务列表：" + ctproTasks);
	        
	        // 根据列表大小定义待执行记录表
	        if(ctproTasks != null){
		        while(ctproTasks.size() > 0){
		        	boolean taskAssigned = false;
		        	for(int k=0; k<ctproTasks.size(); k++){
						// 无顺序执行所有任务
						for (int i = 0; i < vector.size(); i++) {
							SimpleThread currentThread = (SimpleThread) vector.get(i);
							if (!currentThread.isRunning()) {
								System.out.println("thread " + i + " is processing: " + ctproTasks.get(k));
								currentThread.setCtproTask(ctproTasks.get(k));
								currentThread.setRunning(true);
								ctproTasks.remove(k);
								// 任务已经分配
								taskAssigned = true;
								break;
							}
						}
						// 如果任务已经分配，重新循环重新分配任务
						if(taskAssigned){
							break;
						}
		        	}
		        }
	        }
	        
		}
	}
}
