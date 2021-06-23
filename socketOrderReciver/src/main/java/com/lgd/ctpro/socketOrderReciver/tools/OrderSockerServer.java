package com.lgd.ctpro.socketOrderReciver.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.serilizeExecutor.FileSerilizor;

public class OrderSockerServer extends Thread{

	private static Logger logger = LogManager.getLogger(OrderSockerServer.class);
	private List<OrderSocketDealer> orderSocketDealerThreadList;
	private static int socketPort;
	
	private boolean isOn;// 任务执行器是否开启
	
	public OrderSockerServer(){

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
		socketPort = Integer.valueOf(properties.getProperty("ctpro.socket.server.port"));
	}
	
	// 开始任务执行器
	public void startOrderSocketServer(){
		orderSocketDealerThreadList = new Vector<OrderSocketDealer>();
		isOn = true;
	}
	
	// 停止任务执行器
	public void stopOrderSocketServer(){
		for(int i=0; i<orderSocketDealerThreadList.size(); i++){
			orderSocketDealerThreadList.get(i).stopOrderSocketDealer();
		}
		isOn = false;
	}

	public void startWork() throws IOException {

		ServerSocket ss = new ServerSocket(socketPort);
		List<Socket> socketList = new ArrayList<Socket>();
		Socket socket = null;
		int count = 0;

		while (isOn) {
			socket = ss.accept();
			count++;
			logger.debug(count + " client connected to the server!");
			socketList.add(socket);
			OrderSocketDealer orderSocketDealer = new OrderSocketDealer(socket);
			orderSocketDealer.startOrderSocketDealer();
			orderSocketDealer.start();
			// 将线程加入运行线程组中
			orderSocketDealerThreadList.add(orderSocketDealer);
		}
	}
	
	public void run() {
		
		try {
			startWork();
		} catch (IOException e) {
			logger.error(e.getStackTrace());
		}
	}
	
}
