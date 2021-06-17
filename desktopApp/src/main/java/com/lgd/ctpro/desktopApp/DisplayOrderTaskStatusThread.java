package com.lgd.ctpro.desktopApp;

import java.util.List;

import javax.swing.JTextArea;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.core.entity.CtproTask;
import com.lgd.ctpro.taskexecutor.OrderTaskStatusRecorder;
import com.lgd.ctpro.taskexecutor.entity.OrderTaskDisplayEntity;

public class DisplayOrderTaskStatusThread extends Thread {

	private static Logger logger = LogManager.getLogger(DisplayOrderTaskStatusThread.class);

	private boolean isOn;// 任务执行器是否开启
	private JTextArea textArea;
	
	// 开始任务执行器
	public void startStatusRecorder(){
		isOn = true;
	}
	
	// 停止任务执行器
	public void stopStatusRecorder(){
		isOn = false;
	}
	
	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public void run() {
		
		while (isOn) {
			StringBuffer displayContent = new StringBuffer();
			List<OrderTaskDisplayEntity> resList = OrderTaskStatusRecorder.getInstance().getOrderStatusList();
			if(resList.size() < 10){
				for(int i=0; i<resList.size(); i++){
					OrderTaskDisplayEntity orderTaskDisplayEntity = resList.get(i);
					logger.debug(orderTaskDisplayEntity.getCtproOrder());
					displayContent.append("命令：" + orderTaskDisplayEntity.getCtproOrder().getOrderMsg() + "\r\n");
					
					List<CtproTask> taskList = orderTaskDisplayEntity.getTaskList();
					for(int j=0; j<taskList.size(); j++){
						CtproTask ctproTask = taskList.get(j);
						logger.debug(ctproTask);
						displayContent.append("任务：" + ctproTask.getTaskMsg() + "  执行状态：" + ctproTask.getStatus() + "\r\n");
					}
				}
			}else{
				// 只显示前10条命令任务
				for(int i=0; i<10; i++){
					OrderTaskDisplayEntity orderTaskDisplayEntity = resList.get(i);
					logger.debug(orderTaskDisplayEntity.getCtproOrder());
					displayContent.append("命令：" + orderTaskDisplayEntity.getCtproOrder().getOrderMsg() + "\r\n");
					
					List<CtproTask> taskList = orderTaskDisplayEntity.getTaskList();
					for(int j=0; j<taskList.size(); j++){
						CtproTask ctproTask = taskList.get(j);
						logger.debug(ctproTask);
						displayContent.append("任务：" + ctproTask.getTaskMsg() + "  执行状态：" + ctproTask.getStatus() + "\r\n");
					}
				}
			}
			
			// 显示任务进度
			textArea.setText(displayContent.toString());
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
