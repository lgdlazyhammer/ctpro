package com.lgd.ctpro.core.service;

import java.util.ArrayList;
import java.util.List;

import com.lgd.ctpro.core.entity.CtproAction;
import com.lgd.ctpro.core.entity.CtproExecution;
import com.lgd.ctpro.core.entity.CtproOrder;
import com.lgd.ctpro.core.entity.CtproTask;
import com.lgd.ctpro.core.intr.CtproActionIntr;
import com.lgd.ctpro.core.tools.EncryptTool;
import com.lgd.ctpro.rbtree.RBTreeManager;
import com.lgd.ctpro.rbtree.TreeNode;

public class CtproCoreServiceManager {
	
	private static CtproCoreServiceManager instance;
	private static RBTreeManager orderRBTreeManager;
	private static RBTreeManager taskRBTreeManager;
	private static RBTreeManager executionRBTreeManager;
	private static RBTreeManager actionRBTreeManager;
	
	public static synchronized CtproCoreServiceManager getInstance(){
		if(instance == null){
			orderRBTreeManager = new RBTreeManager();
			taskRBTreeManager = new RBTreeManager();
			executionRBTreeManager = new RBTreeManager();
			actionRBTreeManager = new RBTreeManager();
			instance = new CtproCoreServiceManager();
		}
		return instance;
	}
	
	public String addOrder(CtproOrder ctproOrder){
		// 设置命令的键
		ctproOrder.setOrderId(EncryptTool.getMD5ByBase64(ctproOrder.getOrderMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproOrder.getOrderId());
		treeNode.setNodeSaveVal(ctproOrder);
		orderRBTreeManager.addNode(treeNode);
		return ctproOrder.getOrderId();
	}
	
	public CtproOrder getOrder(CtproOrder ctproOrder){
		if(ctproOrder == null){
			return null;
		}
		// 设置命令的键
		ctproOrder.setOrderId(EncryptTool.getMD5ByBase64(ctproOrder.getOrderMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproOrder.getOrderId());
		TreeNode treeNodeRes = orderRBTreeManager.findNode(treeNode);
		if(treeNodeRes != null){
			Object serRes = treeNodeRes.getNodeSaveVal();
			if(serRes != null){
				return (CtproOrder) serRes;
			}
		}
		return null;
	}
	
	public void deleteOrder(CtproOrder ctproOrder){
		// 设置命令的键
		ctproOrder.setOrderId(EncryptTool.getMD5ByBase64(ctproOrder.getOrderMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproOrder.getOrderId());
		orderRBTreeManager.deleteNode(treeNode);
	}
	
	public String addTask(CtproTask ctproTask){
		// 设置命令的键
		ctproTask.setTaskid(EncryptTool.getMD5ByBase64(ctproTask.getTaskMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproTask.getTaskid());
		treeNode.setNodeSaveVal(ctproTask);
		taskRBTreeManager.addNode(treeNode);
		return ctproTask.getTaskid();
	}
	
	public CtproTask getTask(CtproTask ctproTask){
		if(ctproTask == null){
			return null;
		}
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproTask.getTaskid());
		Object selRes = taskRBTreeManager.findNode(treeNode).getNodeSaveVal();
		if(selRes != null){
			return (CtproTask) selRes;
		}
		return null;
	}
	
	public void deleteTask(CtproTask ctproTask){
		// 设置命令的键
		ctproTask.setTaskid(EncryptTool.getMD5ByBase64(ctproTask.getTaskMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproTask.getTaskid());
		taskRBTreeManager.deleteNode(treeNode);
	}
	
	public String addExecution(CtproExecution ctproExecution){
		// 设置命令的键
		ctproExecution.setExecutionId(EncryptTool.getMD5ByBase64(ctproExecution.getExecutionMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproExecution.getExecutionId());
		treeNode.setNodeSaveVal(ctproExecution);
		executionRBTreeManager.addNode(treeNode);
		return ctproExecution.getExecutionId();
	}
	
	public CtproExecution getExecution(CtproExecution ctproExecution){
		if(ctproExecution == null){
			return null;
		}
		// 设置命令的键
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproExecution.getExecutionId());
		TreeNode treeNodeRes = executionRBTreeManager.findNode(treeNode);
		if(treeNodeRes != null){
			Object selRes = treeNodeRes.getNodeSaveVal();
			if(selRes != null){
				return (CtproExecution) selRes; 
			}
		}
		return null;
	}
	
	public void deleteExecution(CtproExecution ctproExecution){
		// 设置命令的键
		ctproExecution.setExecutionId(EncryptTool.getMD5ByBase64(ctproExecution.getExecutionMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproExecution.getExecutionId());
		executionRBTreeManager.deleteNode(treeNode);
	}
	
	public String addAction(CtproAction ctproAction){
		// 设置命令的键
		ctproAction.setActionid(EncryptTool.getMD5ByBase64(ctproAction.getActionMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproAction.getActionid());
		treeNode.setNodeSaveVal(ctproAction);
		actionRBTreeManager.addNode(treeNode);
		return ctproAction.getActionid();
	}
	
	public CtproAction getAction(CtproAction ctproAction){
		if(ctproAction == null){
			return null;
		}
		// 设置命令的键
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproAction.getActionid());
		TreeNode treeNodeRes = actionRBTreeManager.findNode(treeNode);
		if(treeNodeRes != null){
			Object selRes = treeNodeRes.getNodeSaveVal();
			if(selRes != null){
				return (CtproAction) selRes;
			}
		}
		return null;
	}
	
	public void deleteAction(CtproAction ctproAction){
		// 设置命令的键
		ctproAction.setActionid(EncryptTool.getMD5ByBase64(ctproAction.getActionMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproAction.getActionid());
		actionRBTreeManager.deleteNode(treeNode);
	}
	
	/**
	 * 获取命令关联的所有任务
	 * @param ctproOrder
	 * @return
	 */
	public List<CtproTask> getOrderRelatedTasks(CtproOrder ctproOrder){
		// 设置命令的键
		ctproOrder.setOrderId(EncryptTool.getMD5ByBase64(ctproOrder.getOrderMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproOrder.getOrderId());
		TreeNode treeActionNode = orderRBTreeManager.findNode(treeNode);
		
		CtproOrder treeOrderNode = (CtproOrder) treeActionNode.getNodeSaveVal();
		List<String> treeOrderTasks = treeOrderNode.getOrderTasks();
		
		List<CtproTask> taskResList = new ArrayList<CtproTask>();
		for(int i=0; i<treeOrderTasks.size(); i++){
			TreeNode treeNodeTask = new TreeNode();
			treeNodeTask.setNodeVal(treeOrderTasks.get(i));
			TreeNode treeNodeTaskRes = taskRBTreeManager.findNode(treeNodeTask);
			
			if(treeNodeTaskRes != null && treeNodeTaskRes.getNodeSaveVal() != null){
				CtproTask ctproTask = (CtproTask) treeNodeTaskRes.getNodeSaveVal();
				taskResList.add(ctproTask);
			}
		}
		return taskResList;
	}
	
	/**
	 * 获取命令关联的所有执行
	 * @param ctproOrder
	 * @return
	 */
	public List<CtproExecution> getTaskRelatedExecutions(CtproTask ctproTask){
		
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproTask.getTaskid());
		TreeNode treeTaskNode = taskRBTreeManager.findNode(treeNode);
		
		CtproTask ctproTaskSerch = (CtproTask) treeTaskNode.getNodeSaveVal();
		List<String> treeTaskExecutions = ctproTaskSerch.getExecutionActions();
		
		List<CtproExecution> executionResList = new ArrayList<CtproExecution>();
		if(treeTaskExecutions != null){
			for(int i=0; i<treeTaskExecutions.size(); i++){
				TreeNode treeNodeTask = new TreeNode();
				treeNodeTask.setNodeVal(treeTaskExecutions.get(i));
				TreeNode treeNodeExecutionRes = executionRBTreeManager.findNode(treeNodeTask);
				
				if(treeNodeExecutionRes != null && treeNodeExecutionRes.getNodeSaveVal() != null){
					CtproExecution ctproExecution = (CtproExecution) treeNodeExecutionRes.getNodeSaveVal();
					executionResList.add(ctproExecution);
				}
			}
		}
		return executionResList;
	}
	
	/**
	 * 处理所有的执行动作
	 * @param executionList
	 */
	public void dealAllExecutions(List<CtproExecution> executionList){
		
		// 冒泡排序将执行按步骤号从小到大排序
		for(int i=0; i<executionList.size(); i++){
			for(int j=executionList.size()-1; j>i; j--){
				if(executionList.get(j).getStep() < executionList.get(j-1).getStep()){
					CtproExecution temp = executionList.get(j);
					executionList.set(j, executionList.get(j-1));
					executionList.set(j-1, temp);
				}
			}
		}
		
		for(int i=0; i<executionList.size(); i++){
			// 正在执行的任务
			CtproExecution ctproExecution = executionList.get(i);
			String actionMethod = ctproExecution.getExecutionMethod();
			String action = ctproExecution.getExecutionAction();
			
			TreeNode treeNode = new TreeNode();
			treeNode.setNodeVal(action);
			TreeNode treeNodeAction = actionRBTreeManager.findNode(treeNode);
			
			CtproActionIntr ctproAction = (CtproAction) treeNodeAction.getNodeSaveVal();
			// 任务执行机进行动作执行
			ctproAction.executeAction();
		}
		
	}
	
	// 获取命令树
	public RBTreeManager getOrderRBTreeManager(){
		return orderRBTreeManager;
	}
	
	// 获取任务树
	public RBTreeManager getTaskRBTreeManager(){
		return taskRBTreeManager;
	}
	
	// 获取执行树
	public RBTreeManager getExecutionRBTreeManager(){
		return executionRBTreeManager;
	}
	
	// 获取动作树
	public RBTreeManager getActionRBTreeManager(){
		return actionRBTreeManager;
	}
}
