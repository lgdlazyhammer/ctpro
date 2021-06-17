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
				// 精简跟踪列表
				simplifyOrderTaskRecorder();
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
	
	public void simplifyOrderTaskRecorder(){

		List<OrderTaskDisplayEntity> resList = OrderTaskStatusRecorder.getInstance().getOrderStatusList();
		// 是否全部在处理中
		boolean isAllOrderProcessing = false;
		while(resList.size() > 10 && !isAllOrderProcessing){
			// 便利寻找并删除已经完成的任务
			for(int i=0; i<resList.size(); i++){
				OrderTaskDisplayEntity orderTaskDisplayEntity = resList.get(i);
				// 是否全部在处理中
				boolean isAllTaskDone = true;
				
				List<CtproTask> taskList = orderTaskDisplayEntity.getTaskList();
				for(int j=0; j<taskList.size(); j++){
					CtproTask ctproTask = taskList.get(j);
					logger.debug(ctproTask);
					if(!ctproTask.getStatus().equals("done")){
						isAllTaskDone = false;
						break;
					}
				}
				
				if(isAllTaskDone){
					resList.remove(i);
					break;
				}
			}
			// 便利完成并没有发现进行中任务，所有命令处理中
			isAllOrderProcessing = true;
		}
		// 重新赋值列表
		OrderTaskStatusRecorder.getInstance().setOrderStatusList(resList);
	}
	
}
