package com.lgd.ctpro.taskanalyzor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.core.entity.CtproOrder;
import com.lgd.ctpro.core.service.CtproCoreServiceManager;
import com.lgd.ctpro.core.tools.EncryptTool;
import com.lgd.ctpro.rbtree.TreeNode;

public class DealingOrderStackManager {
	
	private static Logger logger = LogManager.getLogger(DealingOrderStackManager.class);

	private static DealingOrderStackManager instance;
	private static List<String> orderStack;
	volatile private static Stack<String> dealingOrderStack;
	
	public static synchronized DealingOrderStackManager getInstance(){
		if(instance == null){
			instance = new DealingOrderStackManager();
			orderStack = new ArrayList<String>();
			dealingOrderStack = new Stack<String>();
			List<TreeNode> orderTreeNodeAll = CtproCoreServiceManager.getInstance().getOrderRBTreeManager().getAllTreeNode();
			for(int i=0; i<orderTreeNodeAll.size(); i++){
				CtproOrder ctproOrder = (CtproOrder) orderTreeNodeAll.get(i).getNodeSaveVal();
				orderStack.add(ctproOrder.getOrderMsg());
			}
		}
		return instance;
	}
	
	// 根据传入字符串找到指定命令
	public CtproOrder getSpecifiedOrder(String param){
		CtproOrder ctproOrder = null;
		logger.debug("所有命令集合：" + orderStack);
		for(int i=0; i<orderStack.size(); i++){
			if(judgeOrderByInputStr(param, orderStack.get(i))){
				ctproOrder = new CtproOrder();
				ctproOrder.setOrderMsg(orderStack.get(i));
				return CtproCoreServiceManager.getInstance().getOrder(ctproOrder);
			}
		}
		return ctproOrder;
	}
	
	private boolean judgeOrderByInputStr(String param, String orderMsg){
		if(param.contains(param)){
			return true;
		}
		return false;
	}
	
	public synchronized void addDealingOrderMsg(String param){
		dealingOrderStack.add(param);
		logger.debug("待处理命令栈内容：" + dealingOrderStack);
	}
	
	public synchronized String getDealingOrderMsg(){
		if(!dealingOrderStack.isEmpty()){
			String orderRes = dealingOrderStack.pop();
			logger.debug("处理命令：" + orderRes);
			return orderRes;
		}
		return null;
	}
}
