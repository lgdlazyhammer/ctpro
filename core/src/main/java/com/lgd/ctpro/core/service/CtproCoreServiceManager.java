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
	
	public static void addOrder(CtproOrder ctproOrder){
		// 设置命令的键
		ctproOrder.setOrderId(EncryptTool.getMD5ByBase64(ctproOrder.getOrderMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproOrder.getOrderId());
		treeNode.setNodeSaveVal(ctproOrder);
		orderRBTreeManager.addNode(treeNode);
	}
	
	public static CtproOrder getOrder(CtproOrder ctproOrder){
		// 设置命令的键
		ctproOrder.setOrderId(EncryptTool.getMD5ByBase64(ctproOrder.getOrderMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproOrder.getOrderId());
		return (CtproOrder) orderRBTreeManager.findNode(treeNode).getNodeSaveVal();
	}
	
	public static void deleteOrder(CtproOrder ctproOrder){
		// 设置命令的键
		ctproOrder.setOrderId(EncryptTool.getMD5ByBase64(ctproOrder.getOrderMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproOrder.getOrderId());
		orderRBTreeManager.deleteNode(treeNode);
	}
	
	public static void addTask(CtproTask ctproTask){
		// 设置命令的键
		ctproTask.setTaskid(EncryptTool.getMD5ByBase64(ctproTask.getTaskMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproTask.getTaskid());
		treeNode.setNodeSaveVal(ctproTask);
		taskRBTreeManager.addNode(treeNode);
	}
	
	public static CtproTask getTask(CtproTask ctproTask){
		// 设置命令的键
		ctproTask.setTaskid(EncryptTool.getMD5ByBase64(ctproTask.getTaskMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproTask.getTaskid());
		return (CtproTask) taskRBTreeManager.findNode(treeNode).getNodeSaveVal();
	}
	
	public static void deleteTask(CtproTask ctproTask){
		// 设置命令的键
		ctproTask.setTaskid(EncryptTool.getMD5ByBase64(ctproTask.getTaskMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproTask.getTaskid());
		taskRBTreeManager.deleteNode(treeNode);
	}
	
	public static void addExecution(CtproExecution ctproExecution){
		// 设置命令的键
		ctproExecution.setExecutionId(EncryptTool.getMD5ByBase64(ctproExecution.getExecutionMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproExecution.getExecutionId());
		treeNode.setNodeSaveVal(ctproExecution);
		executionRBTreeManager.addNode(treeNode);
	}
	
	public static CtproExecution getExecution(CtproExecution ctproExecution){
		// 设置命令的键
		ctproExecution.setExecutionId(EncryptTool.getMD5ByBase64(ctproExecution.getExecutionMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproExecution.getExecutionId());
		return (CtproExecution) executionRBTreeManager.findNode(treeNode).getNodeSaveVal();
	}
	
	public static void deleteExecution(CtproExecution ctproExecution){
		// 设置命令的键
		ctproExecution.setExecutionId(EncryptTool.getMD5ByBase64(ctproExecution.getExecutionMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproExecution.getExecutionId());
		executionRBTreeManager.deleteNode(treeNode);
	}
	
	public static void addAction(CtproAction ctproAction){
		// 设置命令的键
		ctproAction.setActionid(EncryptTool.getMD5ByBase64(ctproAction.getActionMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproAction.getActionid());
		treeNode.setNodeSaveVal(ctproAction);
		actionRBTreeManager.addNode(treeNode);
	}
	
	public static CtproAction getAction(CtproAction ctproAction){
		// 设置命令的键
		ctproAction.setActionid(EncryptTool.getMD5ByBase64(ctproAction.getActionMsg()));
		TreeNode treeNode = new TreeNode();
		treeNode.setNodeVal(ctproAction.getActionid());
		return (CtproAction) actionRBTreeManager.findNode(treeNode).getNodeSaveVal();
	}
	
	public static void deleteAction(CtproAction ctproAction){
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
	public static List<CtproTask> getOrderRelatedTasks(CtproOrder ctproOrder){
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
	public static List<CtproExecution> getTaskRelatedExecutions(CtproTask ctproTask){
		
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
	public static void dealAllExecutions(List<CtproExecution> executionList){
		
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
	public static RBTreeManager getOrderRBTreeManager(){
		return orderRBTreeManager;
	}
	
	// 获取任务树
	public static RBTreeManager getTaskRBTreeManager(){
		return taskRBTreeManager;
	}
	
	// 获取执行树
	public static RBTreeManager getExecutionRBTreeManager(){
		return executionRBTreeManager;
	}
	
	// 获取动作树
	public static RBTreeManager getActionRBTreeManager(){
		return actionRBTreeManager;
	}
}
