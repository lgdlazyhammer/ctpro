package com.lgd.ctpro.desktopApp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.actionImplentor.impl.IsPrimeAction;
import com.lgd.ctpro.core.entity.CtproAction;
import com.lgd.ctpro.core.entity.CtproExecution;
import com.lgd.ctpro.core.entity.CtproOrder;
import com.lgd.ctpro.core.entity.CtproTask;
import com.lgd.ctpro.orderDetailGengrator.entity.GeneActionEntity;
import com.lgd.ctpro.orderDetailGengrator.entity.GeneExecutionEntity;
import com.lgd.ctpro.orderDetailGengrator.entity.GeneOrderEntity;
import com.lgd.ctpro.orderDetailGengrator.entity.GeneTaskEntity;
import com.lgd.ctpro.orderDetailGengrator.tools.OrderDetailGenerator;
import com.lgd.ctpro.serilizeExecutor.FileSerilizor;
import com.lgd.ctpro.serilizeExecutor.SerlizorIntr;
import com.lgd.ctpro.taskanalyzor.TaskAnalyzor;
import com.lgd.ctpro.taskexecutor.TaskExecutor;
import com.lgd.ctpro.socketOrderReciver.tools.OrderSockerServer;
import com.lgd.ctpro.socketOrderReciver.tools.TestClientSendOrderThread;

public class Application {
	
	private static Logger logger = LogManager.getLogger(Application.class);
	private static TaskExecutor executorThread;
	private static TaskAnalyzor taskAnalyzor;
	private static MockSendCommandThread mockSendCommandThread;
	private static OrderSockerServer orderSockerServer;
	private static TestClientSendOrderThread testClientSendOrderThread;
	

	public static void main(String[] args) {
		// 显示应用 GUI
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	/**
	 * { 创建并显示GUI。出于线程安全的考虑， 这个方法在事件调用线程中调用。
	 */
	private static void createAndShowGUI() {
		// 创建 JFrame 实例
		JFrame frame = new JFrame("CTPRO");
		// Setting the width and height of frame
		frame.setSize(700, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * 创建面板，这个类似于 HTML 的 div 标签 我们可以创建多个面板并在 JFrame 中指定位置
		 * 面板中我们可以添加文本字段，按钮及其他组件。
		 */
		JPanel panel = new JPanel();
		// 添加面板
		frame.add(panel);
		/*
		 * 调用用户定义的方法并添加组件到面板
		 */
		placeComponents(panel);

		// 设置界面可见
		frame.setVisible(true);
	}

	private static void placeComponents(JPanel panel) {

		/*
		 * 布局部分我们这边不多做介绍 这边设置布局为 null
		 */
		panel.setLayout(null);

		// 创建启动按钮
		JButton startButton = new JButton("启动");
		startButton.setBounds(10, 10, 80, 25);
		panel.add(startButton);

		// 创建停止按钮
		JButton stopButton = new JButton("停止");
		stopButton.setBounds(100, 10, 80, 25);
		panel.add(stopButton);
		
		final JLabel systemStatus = new JLabel("服务状态");
		systemStatus.setBounds(10, 60, 600, 25);
		panel.add(systemStatus);

		 // 创建一个 5 行 10 列的文本区域
        final JTextArea textArea = new JTextArea(10, 10);
        textArea.setBounds(10, 100, 600, 600);
        // 设置自动换行
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        // 添加到内容面板
        panel.add(textArea);
        
		// 启动任务跟踪线程
		final DisplayOrderTaskStatusThread displayOrderTaskStatusThread = new DisplayOrderTaskStatusThread();
		displayOrderTaskStatusThread.startStatusRecorder();
		displayOrderTaskStatusThread.setTextArea(textArea);
		displayOrderTaskStatusThread.start();
		
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				logger.debug("动作为：" + e.getActionCommand());
				// 生成命令导入，启动命令收集器和执行器
				GeneOrderEntity geneOrderEntity = new GeneOrderEntity();
				CtproOrder ctproOrder = new CtproOrder();
				ctproOrder.setOrderTyp("1");
				ctproOrder.setOrderMsg("第一个命令");
				geneOrderEntity.setCtproOrder(ctproOrder);
				
				GeneTaskEntity geneTaskEntity = new GeneTaskEntity();
				CtproTask ctproTask = new CtproTask();
				ctproTask.setTaskMsg("第一个命令的第一个任务");
				ctproTask.setStep(1);
				geneTaskEntity.setCtproTask(ctproTask);
				
				GeneTaskEntity geneTaskEntity2 = new GeneTaskEntity();
				CtproTask ctproTask2 = new CtproTask();
				ctproTask2.setTaskMsg("第一个命令的第二个任务");
				ctproTask2.setStep(2);
				geneTaskEntity2.setCtproTask(ctproTask2);
				
				geneOrderEntity.getGeneTaskList().add(geneTaskEntity);
				geneOrderEntity.getGeneTaskList().add(geneTaskEntity2);
				
				
				GeneExecutionEntity geneExecutionEntity = new GeneExecutionEntity();
				CtproExecution ctproExecution = new CtproExecution();
				ctproExecution.setExecutionMsg("执行一");
				ctproExecution.setStep(1);
				geneExecutionEntity.setCtproExecution(ctproExecution);
				
				GeneExecutionEntity geneExecutionEntity2 = new GeneExecutionEntity();
				CtproExecution ctproExecution2 = new CtproExecution();
				ctproExecution2.setExecutionMsg("执行二");
				ctproExecution2.setStep(1);
				geneExecutionEntity2.setCtproExecution(ctproExecution2);
				
				geneTaskEntity.getGeneExecutionList().add(geneExecutionEntity);
				geneTaskEntity.getGeneExecutionList().add(geneExecutionEntity2);
				
				geneTaskEntity2.getGeneExecutionList().add(geneExecutionEntity);
				geneTaskEntity2.getGeneExecutionList().add(geneExecutionEntity2);
				
				
				GeneActionEntity geneActionEntity = new GeneActionEntity();
				CtproAction ctproAction = new CtproAction();
				geneActionEntity.setCtproAction(ctproAction);
				
				GeneActionEntity geneActionEntity2 = new GeneActionEntity();
				CtproAction ctproAction2 = new IsPrimeAction();
				geneActionEntity2.setCtproAction(ctproAction2);
				
				geneExecutionEntity.setGeneActionEntity(geneActionEntity);
				geneExecutionEntity2.setGeneActionEntity(geneActionEntity2);
				
				// 生成命令数据结构
				OrderDetailGenerator.generateOrderDetail(geneOrderEntity);
				
				// 动作执行器开启
				executorThread = new TaskExecutor();
		        executorThread.startExecutor();
		        executorThread.start();
				
		        // 动作收集器开启
		        taskAnalyzor = new TaskAnalyzor();
		        taskAnalyzor.startAnalyzor();
		        taskAnalyzor.start();
				
				orderSockerServer = new OrderSockerServer();
				orderSockerServer.start();
				
				// 模拟命令发起器开启
				testClientSendOrderThread = new TestClientSendOrderThread();
				testClientSendOrderThread.startClientSender();
				testClientSendOrderThread.start();
				
				/*mockSendCommandThread = new MockSendCommandThread();
				mockSendCommandThread.startMockSender();
				mockSendCommandThread.start();*/

				systemStatus.setText("服务已经启动");
			}
		});
		

		stopButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				logger.debug("动作为：" + e.getActionCommand());
				// 停止服务，将命令结构保存
				// mockSendCommandThread.stopMockSender();
				testClientSendOrderThread.stopClientSender();
				executorThread.stopExecutor();
				taskAnalyzor.stopAnalyzor();
				displayOrderTaskStatusThread.stopStatusRecorder();
				orderSockerServer.stop();
				
				SerlizorIntr serlizorIntr = new FileSerilizor();
	        	serlizorIntr.serilize();
				
				systemStatus.setText("服务已经停止");
		        
		        textArea.setText("");
			}
		});
		
	}
	
	
}
