package com.lgd.ctpro.taskexecutor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.Exchanger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.core.entity.CtproExecution;
import com.lgd.ctpro.core.entity.CtproOrder;
import com.lgd.ctpro.core.entity.CtproTask;
import com.lgd.ctpro.core.service.CtproCoreServiceManager;
import com.lgd.ctpro.serilizeExecutor.FileSerilizor;
import com.lgd.ctpro.taskexecutor.entity.OrderTaskDisplayEntity;

/**
 * 
 * @ClassName: ThreadPoolManager
 * @Description: thread pool manager
 * @author liguodong
 * @date 2018年5月7日 下午4:40:01
 *
 */
public class ThreadPoolManager {
	
	private static Logger logger = LogManager.getLogger(ThreadPoolManager.class);

	private int maxThread;
	private Vector vector;
	private static ThreadPoolManager instance;
	private static int threadPoolSize;
	
	public static synchronized ThreadPoolManager getInstance(){
		if(instance == null){
			Properties properties = new Properties();
			// 使用ClassLoader加载properties配置文件生成对应的输入流
			InputStream in = FileSerilizor.class.getClassLoader().getResourceAsStream("settings.properties");
			// 使用properties对象加载输入流
			try {
				properties.load(in);
			} catch (IOException e) {
				logger.error(e.getStackTrace());
			}
			// 获取key对应的value值
			threadPoolSize = Integer.valueOf(properties.getProperty("ctpro.executor.threadPool.size"));
			instance = new ThreadPoolManager(threadPoolSize);
		}
		return instance;
	}

	public void setMaxThread(int threadCount) {
		this.maxThread = threadCount;
	}

	public ThreadPoolManager(int threadCount) {

		setMaxThread(threadCount);
		logger.debug("start thread pool ......");
		vector = new Vector();

		for (int i = 0; i < threadCount; i++) {
			SimpleThread thread = new SimpleThread(i);
			vector.add(thread);
			thread.start();
		}
	}

	public void process(CtproOrder ctproOrder) {

		// 将任务同时加入到监控对象里
		OrderTaskDisplayEntity orderTaskDisplayEntity = new OrderTaskDisplayEntity();
		orderTaskDisplayEntity.setCtproOrder(ctproOrder);
		orderTaskDisplayEntity.setThreadId(String.valueOf(Thread.currentThread().getId()));
		// 按顺序执行任务
		if("1".equals(ctproOrder.getOrderTyp())){
			// 按顺序执行所有任务
			List<CtproTask> ctproTaskList = CtproCoreServiceManager.getInstance().getOrderRelatedTasks(ctproOrder);
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
								// 将任务加入到监控对象里
								orderTaskDisplayEntity.getTaskList().add(ctproTask);
								logger.debug("thread " + i + " is processing: " + ctproTask);
								currentThread.setCtproTask(ctproTask);
								currentThread.setRunning(true);
								while(taskProcessing){
									// 休眠100毫秒
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										logger.error(e.getStackTrace());
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
	        List<CtproTask> ctproTasks = CtproCoreServiceManager.getInstance().getOrderRelatedTasks(ctproOrder);
	        logger.debug("命令的关联任务列表：" + ctproTasks);
	        
	        // 根据列表大小定义待执行记录表
	        if(ctproTasks != null){
		        while(ctproTasks.size() > 0){
		        	boolean taskAssigned = false;
		        	for(int k=0; k<ctproTasks.size(); k++){
						// 无顺序执行所有任务
						for (int i = 0; i < vector.size(); i++) {
							SimpleThread currentThread = (SimpleThread) vector.get(i);
							if (!currentThread.isRunning()) {
								// 将任务加入到监控对象里
								orderTaskDisplayEntity.getTaskList().add(ctproTasks.get(k));
								logger.debug("thread " + i + " is processing: " + ctproTasks.get(k));
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
		
		// 将任务同时加入到监控对象里
		OrderTaskStatusRecorder.getInstance().getOrderStatusList().add(orderTaskDisplayEntity);
	}
}
