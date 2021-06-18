package com.lgd.ctpro.socketOrderReciver.tools;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderSockerServer extends Thread{

	private static Logger logger = LogManager.getLogger(OrderSockerServer.class);

	public void startWork() throws IOException {

		ServerSocket ss = new ServerSocket(1234);
		List<Socket> socketList = new ArrayList<Socket>();
		Socket socket = null;
		int count = 0;

		while (true) {
			socket = ss.accept();
			count++;
			logger.debug(count + " client connected to the server!");
			socketList.add(socket);
			Thread orderSocketDealer = new Thread(new OrderSocketDealer(socket));
			orderSocketDealer.start();
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
