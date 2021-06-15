package com.lgd.ctpro.taskanalyzor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.lgd.ctpro.core.entity.CtproOrder;
import com.lgd.ctpro.core.service.CtproCoreServiceManager;
import com.lgd.ctpro.core.tools.EncryptTool;
import com.lgd.ctpro.rbtree.TreeNode;

public class DealingOrderStackManager {

	private static DealingOrderStackManager instance;
	private static List<String> orderStack;
	private static Stack<String> dealingOrderStack;
	
	public static synchronized DealingOrderStackManager getInstance(){
		if(instance == null){
			instance = new DealingOrderStackManager();
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
		for(int i=0; i<orderStack.size(); i++){
			if(judgeOrderByInputStr(param, orderStack.get(i))){
				String orderMsg = EncryptTool.getMD5ByBase64(orderStack.get(i));
				ctproOrder = new CtproOrder();
				ctproOrder.setOrderMsg(orderMsg);
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
	}
	
	public synchronized String getDealingOrderMsg(){
		return dealingOrderStack.pop();
	}
}
