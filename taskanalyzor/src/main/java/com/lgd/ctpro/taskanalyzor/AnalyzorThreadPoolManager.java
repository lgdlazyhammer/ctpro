package com.lgd.ctpro.taskanalyzor;

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

/**
 * 
 * @ClassName: ThreadPoolManager
 * @Description: thread pool manager
 * @author liguodong
 * @date 2018年5月7日 下午4:40:01
 *
 */
public class AnalyzorThreadPoolManager {
	
	private static Logger logger = LogManager.getLogger(AnalyzorThreadPoolManager.class);

	private int maxThread;
	private Vector vector;
	private static AnalyzorThreadPoolManager instance;
	private static int threadPoolSize;
	
	public static synchronized AnalyzorThreadPoolManager getInstance(){
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
			threadPoolSize = Integer.valueOf(properties.getProperty("ctpro.analyzor.threadPool.size"));
			instance = new AnalyzorThreadPoolManager(threadPoolSize);
		}
		return instance;
	}

	public void setMaxThread(int threadCount) {
		this.maxThread = threadCount;
	}

	public AnalyzorThreadPoolManager(int threadCount) {

		setMaxThread(threadCount);
		logger.debug("start thread pool ......");
		vector = new Vector();

		for (int i = 0; i < threadCount; i++) {
			SimpleAnalyzorThread thread = new SimpleAnalyzorThread(i);
			vector.add(thread);
			thread.start();
		}
	}

	public void process(String orderToDeal) {

		for (int i = 0; i < vector.size(); i++) {
			SimpleAnalyzorThread currentThread = (SimpleAnalyzorThread) vector.get(i);
			if (!currentThread.isRunning()) {
				currentThread.setOrderToDeal(orderToDeal);
				currentThread.setRunning(true);
			}
		}
			
	}
}
