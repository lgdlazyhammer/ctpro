package com.lgd.ctpro.taskexecutor;

import java.util.Stack;

import com.lgd.ctpro.core.entity.CtproOrder;

/**
 * 命令栈管理对象
 * @author liguodong
 *
 */
public class OrderStackManager {
	
	volatile private static Stack<CtproOrder> orderStack;
	private static OrderStackManager instance;
	
	// 单例获取命令栈管理对象
	public static synchronized OrderStackManager getInstance(){
		if(instance == null){
			orderStack = new Stack<CtproOrder>();
			instance = new OrderStackManager();
		}
		return instance;
	}

	// 获取栈中的命令
	public synchronized CtproOrder getOrder(){
		if(!orderStack.isEmpty()){
			return orderStack.pop();
		}
		return null;
	}
	
	// 将命令放入栈中
	public synchronized void addOrder(CtproOrder ctproOrder){
		orderStack.add(ctproOrder);
	}
}
