package com.lgd.ctpro.socketOrderReciver.tools;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderSockerServer extends Thread{

	private static Logger logger = LogManager.getLogger(OrderSockerServer.class);
	private List<OrderSocketDealer> orderSocketDealerThreadList;
	
	private boolean isOn;// 任务执行器是否开启
	
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

		ServerSocket ss = new ServerSocket(1234);
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
