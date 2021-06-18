package com.lgd.ctpro.socketOrderReciver.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.taskanalyzor.DealingOrderStackManager;

public class OrderSocketDealer implements Runnable {

	private static Logger logger = LogManager.getLogger(OrderSocketDealer.class);

	private Socket socket;
	final private String endMsg = "10100101";

	public OrderSocketDealer(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {

		BufferedReader reader = null;
		PrintWriter writer = null;

		try {

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message = null;
			while (true) {
				message = reader.readLine();
				if (endMsg.equals(message)) {
					// 终止词终止对话
					writer = new PrintWriter(socket.getOutputStream());
					writer.flush();
					continue;
				}
				
				logger.debug("接收到的命令为：" + message);
				// 增加待处理命令到待处理命令队列中
				DealingOrderStackManager.getInstance().addDealingOrderMsg(message);
			}

		} catch (IOException e) {
			logger.error(e.getStackTrace());
		}
	}

}
