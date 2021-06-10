package com.lgd.ctpro.taskexecutor;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Exchanger;

import com.lgd.ctpro.core.entity.CtproExecution;
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
	private Exchanger<String> exchanger;
	private String dealStu;

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
			List<CtproTask> ctproTaskList = CtproCoreServiceManager.getOrderRelatedTasks(ctproOrder);
			// 冒泡排序将执行按步骤号从小到大排序
			for(int i=0; i<ctproTaskList.size(); i++){
				for(int j=ctproTaskList.size()-1; j>i; j--){
					if(ctproTaskList.get(j).getStep() < ctproTaskList.get(j-1).getStep()){
						CtproTask temp = ctproTaskList.get(j);
						ctproTaskList.set(j, ctproTaskList.get(j-1));
						ctproTaskList.set(j-1, temp);
					}
				}
			}
			// 顺序执行所有任务
			if(ctproTaskList != null){
		        while(ctproTaskList.size() > 0){
		        	boolean taskProcessing = true;
		        	for(int k=0; k<ctproTaskList.size(); k++){
						// 无顺序执行所有任务
						for (int i = 0; i < vector.size(); i++) {
							SimpleThread currentThread = (SimpleThread) vector.get(i);
							if (!currentThread.isRunning()) {
								CtproTask ctproTask = ctproTaskList.get(k);
								System.out.println("thread " + i + " is processing: " + ctproTask);
								currentThread.setCtproTask(ctproTask);
								currentThread.setRunning(true);
								while(taskProcessing){
									// 休眠100毫秒
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									if(ctproTask.getStatus() == "done"){
										taskProcessing = false;
									}
								}
								// 删除已经处理完成的任务
								ctproTaskList.remove(k);
								break;
							}
						}
						// 触发重新处理
						break;
		        	}
		        }
			}
			
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
