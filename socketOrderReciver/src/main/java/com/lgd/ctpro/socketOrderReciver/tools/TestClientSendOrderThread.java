package com.lgd.ctpro.socketOrderReciver.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestClientSendOrderThread extends Thread {

	private static Logger logger = LogManager.getLogger(TestClientSendOrderThread.class);
	private static Socket socket;
	private static List<String> orderList;
	final private String endMsg = "10100101";
	
	private boolean isOn;// 任务执行器是否开启
	
	// 开始任务执行器
	public void startClientSender(){
		isOn = true;
	}
	
	// 停止任务执行器
	public void stopClientSender(){
		isOn = false;
	}

	public TestClientSendOrderThread(){

		orderList = new ArrayList<String>();
		orderList.add("第一个命令");
		
		try {
			socket = new Socket("127.0.0.1", 1234);
		} catch (UnknownHostException e) {
			logger.error(e.getStackTrace());
		} catch (IOException e) {
			logger.error(e.getStackTrace());
		}
	}
	
	@Override
	public void run() {
		
		PrintWriter writer = null;

		try {
			writer = new PrintWriter(socket.getOutputStream());

			String message = null;
			while (isOn) {
				// 循环遍历发送命令列表
				for(int i=0; i<orderList.size(); i++){
					message = orderList.get(i);
					writer.println(message);
					writer.println(endMsg);
					writer.flush();
				}
				
				// 休眠1000毫秒
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					logger.error(e.getStackTrace());
				}
			}

		} catch (IOException e) {
			logger.error(e.getStackTrace());
		}
	}
}
