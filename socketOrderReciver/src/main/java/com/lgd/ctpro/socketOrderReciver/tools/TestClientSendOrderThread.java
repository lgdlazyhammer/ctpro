package com.lgd.ctpro.socketOrderReciver.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.serilizeExecutor.FileSerilizor;

public class TestClientSendOrderThread extends Thread {

	private static Logger logger = LogManager.getLogger(TestClientSendOrderThread.class);
	private static Socket socket;
	private static List<String> orderList;
	final private String endMsg = "10100101";
	private static String socketIp;
	private static int socketPort;
	
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
		socketIp = properties.getProperty("ctpro.socket.server.ip");
		socketPort = Integer.valueOf(properties.getProperty("ctpro.socket.server.port"));
		
		orderList = new ArrayList<String>();
		orderList.add("第一个命令");
		
		try {
			socket = new Socket(socketIp, socketPort);
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
