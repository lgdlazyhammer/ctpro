package com.lgd.ctpro.rbtree;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RBTreeManager {
	
	private Logger logger = LogManager.getLogger(RBTreeManager.class);

	private TreeNode rootNode;
	final static private String BLACK = "B";
	final static private String RED = "R";
	
	// 增加节点方法
	public synchronized void addNode(TreeNode treeNode){

		// 新插入的节点均为红色
		treeNode.setNodeRed();
		treeNode.setLeftNode(null);
		treeNode.setRightNode(null);
		logger.debug("");
		logger.debug("增加节点开始：" + treeNode);
		
		if(rootNode == null){
			rootNode = treeNode;
			treeNode.setNodeBlack();
		}else{
			// 寻找节点或插入位置
			TreeNode insertNodeLoca = findNodeOrInsertLocation(treeNode, rootNode);
			if(treeNode.getNodeVal() == insertNodeLoca.getNodeVal()){
				// 节点寻找到了，如果找到替换的node，直接替换node里的值
				insertNodeLoca.setNodeSaveVal(treeNode.getNodeSaveVal());
			}else{
				// 返回节点为要插入的父节点，将节点插入树中
				insertNodeLoca.addChildNode(treeNode);
				// 刷新树
				refreshTreeForAddMehtod(treeNode);
			}
			
		}
		logger.debug("增加节点结束 -------------------");
		logger.debug("增加节点结束：" + treeNode);
		logger.debug("增加节点结束，当前节点父节点为：" + treeNode.getParentNode());
		logger.debug("增加节点结束，当前节点左子节点为：" + treeNode.getLeftNode());
		logger.debug("增加节点结束，当前节点右子节点为：" + treeNode.getRightNode());
		logger.debug("增加节点结束，当前根节点为：" + rootNode);
		logger.debug("增加节点结束，当前根节点左子节点为：" + rootNode.getLeftNode());
		logger.debug("增加节点结束，当前根节点右子节点为：" + rootNode.getRightNode());
		logger.debug("增加节点结束 -------------------");
	}
	
	// 刷新树方法
	public synchronized void refreshTreeForAddMehtod(TreeNode treeNode){

		logger.debug("增加刷新树开始，当前节点为：" + treeNode);
		// 当前节点为根节点
		if(treeNode.getParentNode() == null){
			// 当前节点为根节点，重置根节点为黑色
			treeNode.setNodeBlack();
			rootNode = treeNode;
			return;
		}
		TreeNode parentNode = treeNode.getParentNode();
		logger.debug("当前节点父节点左子节点为：" + treeNode.getParentNode().getLeftNode());
		logger.debug("当前节点父节点右子节点为：" + treeNode.getParentNode().getRightNode());
		TreeNode grandParentNode = null;
		if(parentNode != null){
			grandParentNode = parentNode.getParentNode();
		}
		TreeNode uncleNode = null;
		if(grandParentNode != null){
			uncleNode = parentNode.getBrotherNode();
		}
		
		if(parentNode != null && parentNode.getNodeColor().equals(BLACK)){
			// 父节点为黑色，直接插入，不做处理
			return;
		}else{
			// 所有的旋转变色均为祖父节点存在的情况下才进行
			if(grandParentNode != null){
				if(uncleNode != null && uncleNode.getNodeColor().equals(RED)){
					// 存在叔叔节点且为红色，结果为，祖父节点黑色，父亲和叔叔节点红色
					// 处理方式为，置父亲节点和叔叔节点为黑色，祖父节点为红色，重新插入祖父节点，刷新树
					parentNode.setNodeBlack();
					uncleNode.setNodeBlack();
					grandParentNode.setNodeRed();
					// 发起新的一轮更新
					refreshTreeForAddMehtod(grandParentNode);
					return;
				}else if((uncleNode == null || uncleNode.getNodeColor().equals(BLACK)) && isTreeNodeLeftOrRight(parentNode, grandParentNode).equals("L")){
					// 叔叔节点不存在或为黑节点，并且插入节点的父亲节点是祖父节点的左节点，此情况为，当前结点和父亲节点均为红色
					// 处理方式为
					if(isTreeNodeLeftOrRight(treeNode, parentNode).equals("L")){
						// 插入节点为父节点左节点：此情况为当前节点和父节点为红色，祖父节点为黑色，以父节点为轴右旋
						TreeNode parentRightChildNode = parentNode.getRightNode();
						parentNode.setParentNode(grandParentNode.getParentNode());
						if(grandParentNode.getParentNode() != null){
							if(isTreeNodeLeftOrRight(grandParentNode, grandParentNode.getParentNode()).equals("L")){
								grandParentNode.getParentNode().setLeftNode(parentNode);
							}else if(isTreeNodeLeftOrRight(grandParentNode, grandParentNode.getParentNode()).equals("R")){
								grandParentNode.getParentNode().setRightNode(parentNode);
							}
						}
						parentNode.setRightNode(grandParentNode);
						grandParentNode.setParentNode(parentNode);
						grandParentNode.setLeftNode(parentRightChildNode);
						if(parentRightChildNode != null){
							parentRightChildNode.setParentNode(grandParentNode);
						}
						
						// parentNode.setNodeRed();
						// grandParentNode.setNodeBlack();
						treeNode.setNodeBlack();
						logger.debug("右旋结束");
						logger.debug("当前节点父节点为：" + treeNode.getParentNode());
						logger.debug("当前节点父节点左子节点为：" + treeNode.getParentNode().getLeftNode());
						logger.debug("当前节点父节点右子节点为：" + treeNode.getParentNode().getRightNode());
						// 以当前父节点为新增节点刷新树
						refreshTreeForAddMehtod(parentNode);
						return;
					}else if(isTreeNodeLeftOrRight(treeNode, parentNode).equals("R")){
						// 插入节点为父节点右节点：转换成上边后，按照如上右旋
						TreeNode treeLeftChildNode = treeNode.getLeftNode();
						treeNode.setParentNode(parentNode.getParentNode());
						if(parentNode.getParentNode() != null){
							if(isTreeNodeLeftOrRight(parentNode, parentNode.getParentNode()).equals("L")){
								parentNode.getParentNode().setLeftNode(treeNode);
							}else if(isTreeNodeLeftOrRight(parentNode, parentNode.getParentNode()).equals("R")){
								parentNode.getParentNode().setRightNode(treeNode);
							}
						}
						treeNode.setLeftNode(parentNode);
						parentNode.setParentNode(treeNode);
						parentNode.setRightNode(treeLeftChildNode);
						if(treeLeftChildNode != null){
							treeLeftChildNode.setParentNode(parentNode);
						}
						
						TreeNode parentRightChildNode = treeNode.getRightNode();
						treeNode.setParentNode(grandParentNode.getParentNode());
						if(grandParentNode.getParentNode() != null){
							if(isTreeNodeLeftOrRight(grandParentNode, grandParentNode.getParentNode()).equals("L")){
								grandParentNode.getParentNode().setLeftNode(treeNode);
							}else if(isTreeNodeLeftOrRight(grandParentNode, grandParentNode.getParentNode()).equals("R")){
								grandParentNode.getParentNode().setRightNode(treeNode);
							}
						}
						treeNode.setRightNode(grandParentNode);
						grandParentNode.setParentNode(treeNode);
						grandParentNode.setLeftNode(parentRightChildNode);
						if(parentRightChildNode != null){
							parentRightChildNode.setParentNode(grandParentNode);
						}
						
						// treeNode.setNodeRed();
						// grandParentNode.setNodeBlack();
						parentNode.setNodeBlack();
						logger.debug("节点为父节点右节点，右旋结束");
						logger.debug("当前节点父节点为：" + treeNode.getParentNode());
						if(treeNode.getParentNode() != null){
							logger.debug("当前节点父节点左子节点为：" + treeNode.getParentNode().getLeftNode());
							logger.debug("当前节点父节点右子节点为：" + treeNode.getParentNode().getRightNode());
						}
						// 以当前父节点为新增节点刷新树
						refreshTreeForAddMehtod(treeNode);
						return;
					}
				}else if((uncleNode == null || uncleNode.getNodeColor().equals(BLACK)) && isTreeNodeLeftOrRight(parentNode, grandParentNode).equals("R")){
					// 叔叔节点不存在或为黑节点，并且插入节点的父亲节点是祖父节点的右节点，此情况为，当前结点和父亲节点均为红色
					// 处理方式为
					if(isTreeNodeLeftOrRight(treeNode, parentNode).equals("R")){
						// 插入节点为父节点右节点：此情况为当前节点和父节点为红色，祖父节点为黑色，以父节点为轴左旋
						TreeNode parentLeftChildNode = parentNode.getLeftNode();
						parentNode.setParentNode(grandParentNode.getParentNode());
						if(grandParentNode.getParentNode() != null){
							if(isTreeNodeLeftOrRight(grandParentNode, grandParentNode.getParentNode()).equals("L")){
								grandParentNode.getParentNode().setLeftNode(parentNode);
							}else if(isTreeNodeLeftOrRight(grandParentNode, grandParentNode.getParentNode()).equals("R")){
								grandParentNode.getParentNode().setRightNode(parentNode);
							}
						}
						// 原父节点继承当前节点的左节点后节点内容
						parentNode.setLeftNode(grandParentNode);
						grandParentNode.setParentNode(parentNode);
						grandParentNode.setRightNode(parentLeftChildNode);
						if(parentLeftChildNode != null){
							parentLeftChildNode.setParentNode(grandParentNode);
						}
						
						// parentNode.setNodeRed();
						// grandParentNode.setNodeBlack();
						treeNode.setNodeBlack();
						logger.debug("左旋结束");
						logger.debug("当前节点父节点为：" + treeNode.getParentNode());
						logger.debug("当前节点父节点左子节点为：" + treeNode.getParentNode().getLeftNode());
						logger.debug("当前节点父节点右子节点为：" + treeNode.getParentNode().getRightNode());
						// 以当前父节点为新增节点刷新树
						refreshTreeForAddMehtod(parentNode);
						return;
					}else if(isTreeNodeLeftOrRight(treeNode, parentNode).equals("L")){
						// 插入节点为父节点右节点：转换成上边后，按照如上左旋
						TreeNode treeRightChildNode = parentNode.getRightNode();
						treeNode.setParentNode(parentNode.getParentNode());
						if(parentNode.getParentNode() != null){
							if(isTreeNodeLeftOrRight(parentNode, parentNode.getParentNode()).equals("L")){
								parentNode.getParentNode().setLeftNode(treeNode);
							}else if(isTreeNodeLeftOrRight(parentNode, parentNode.getParentNode()).equals("R")){
								parentNode.getParentNode().setRightNode(treeNode);
							}
						}
						treeNode.setRightNode(parentNode);
						parentNode.setParentNode(treeNode);
						parentNode.setLeftNode(treeRightChildNode);
						if(treeRightChildNode != null){
							treeRightChildNode.setParentNode(parentNode);
						}
						
						// 插入节点为父节点右节点：此情况为当前节点和父节点为红色，祖父节点为黑色，以父节点为轴左旋
						TreeNode parentLeftChildNode = treeNode.getLeftNode();
						treeNode.setParentNode(grandParentNode.getParentNode());
						if(grandParentNode.getParentNode() != null){
							if(isTreeNodeLeftOrRight(grandParentNode, grandParentNode.getParentNode()).equals("L")){
								grandParentNode.getParentNode().setLeftNode(treeNode);
							}else if(isTreeNodeLeftOrRight(grandParentNode, grandParentNode.getParentNode()).equals("R")){
								grandParentNode.getParentNode().setRightNode(treeNode);
							}
						}
						// 原父节点继承当前节点的左节点后节点内容
						treeNode.setLeftNode(grandParentNode);
						grandParentNode.setParentNode(treeNode);
						grandParentNode.setRightNode(parentLeftChildNode);
						if(parentLeftChildNode != null){
							parentLeftChildNode.setParentNode(grandParentNode);
						}
						
						// treeNode.setNodeRed();
						// grandParentNode.setNodeBlack();
						parentNode.setNodeBlack();
						logger.debug("节点为父节点左子节点，左旋结束");
						logger.debug("当前节点父节点为：" + treeNode.getParentNode());
						logger.debug("当前节点父节点左子节点为：" + treeNode.getParentNode().getLeftNode());
						logger.debug("当前节点父节点右子节点为：" + treeNode.getParentNode().getRightNode());
						// 以当前父节点为新增节点刷新树
						refreshTreeForAddMehtod(treeNode);
						return;
					}
				}
			}
		}
	}
	
	// 找到插入点
	public TreeNode findNodeOrInsertLocation(TreeNode paramNode, TreeNode startNode){
		
		// 找到合适的左右节点或当前节点
		TreeNode chooseNode = startNode.compareAndChooseChild(paramNode);
		if(chooseNode == null){
			// 查找不到对应的值，返回父亲节点
			return startNode;
		}else if(chooseNode.getNodeVal().equals(paramNode.getNodeVal())){
			// 查找到对应的值，返回当前节点
			return chooseNode;
		}else{
			// 未找到值，轮询进入子节点树寻找
			return findNodeOrInsertLocation(paramNode, chooseNode);			
		}
	}
	
	// 删除节点方法
	public synchronized void deleteNode(TreeNode treeNode){

		logger.debug("");
		logger.debug("删除节点开始：" + treeNode);
		
		if(rootNode == null){
			return;
		}else{
			// 寻找删除节点
			TreeNode deleteNode = findNode(treeNode, rootNode);
			if(deleteNode == null){
				// 节点不存在，结束
				return;
			}else{
				// 获取删除节点颜色
				String deleteNodeColor = deleteNode.getNodeColor();
				TreeNode deleteNodeLeftChildNode = deleteNode.getLeftNode();
				TreeNode deleteNodeRightChildNode = deleteNode.getRightNode();
				TreeNode parentNode = deleteNode.getParentNode();
				
				// 重色更新节点
				TreeNode doubleColorNode = null;
				if(deleteNodeLeftChildNode == null && deleteNodeRightChildNode == null){
					
					// 只剩根节点--删除根节点
					if(deleteNode.getNodeVal() == rootNode.getNodeVal()){
						rootNode = null;
					}
					// 删除节点无子节点
					if(deleteNodeColor.equals(RED)){
						// 无子节点红色节点，直接删掉
						if(isTreeNodeLeftOrRight(deleteNode, deleteNode.getParentNode()).equals("L")){
							deleteNode.getParentNode().setLeftNode(null);
						}else if(isTreeNodeLeftOrRight(deleteNode, deleteNode.getParentNode()).equals("R")){
							deleteNode.getParentNode().setRightNode(null);
						}
						deleteNode.setParentNode(null);
					}else if(deleteNodeColor.equals(BLACK)){
						TreeNode brotherNode = deleteNode.getBrotherNode();
						if(brotherNode == null){
							// 如果删除节点无兄弟节点，双色节点为父节点
							doubleColorNode = deleteNode.getParentNode();
							// 删除节点
							if(isTreeNodeLeftOrRight(deleteNode, deleteNode.getParentNode()).equals("L")){
								deleteNode.getParentNode().setLeftNode(null);
							}else if(isTreeNodeLeftOrRight(deleteNode, deleteNode.getParentNode()).equals("R")){
								deleteNode.getParentNode().setRightNode(null);
							}
						}else{
							// 兄弟节点的子节点用来转换
							TreeNode brotherNodeLeftChildNode = brotherNode.getLeftNode();
							TreeNode brotherNodeRightChildNode = brotherNode.getRightNode();
							// 如果删除节点有兄弟节点
							if(brotherNode.getNodeColor().equals(RED)){
								// 以兄弟节点为支点左旋，并交换父节点和兄弟节点颜色
								if(parentNode.getParentNode() != null){
									if(isTreeNodeLeftOrRight(parentNode, parentNode.getParentNode()).equals("L")){
										parentNode.getParentNode().setLeftNode(brotherNode);
									}else if(isTreeNodeLeftOrRight(parentNode, parentNode.getParentNode()).equals("R")){
										parentNode.getParentNode().setRightNode(brotherNode);
									}
								}
								brotherNode.setParentNode(parentNode.getParentNode());
								brotherNode.setLeftNode(parentNode);
								parentNode.setParentNode(brotherNode);
								parentNode.setRightNode(brotherNodeLeftChildNode);
								if(brotherNodeLeftChildNode != null){
									brotherNodeLeftChildNode.setParentNode(parentNode);
								}
								
								brotherNode.setNodeBlack();
								parentNode.setNodeRed();
								
								// 根节点重定向
								if(parentNode.getNodeVal() == rootNode.getNodeVal()){
									rootNode = brotherNode;
									rootNode.setNodeBlack();
								}
								// 删除原节点
								deleteNode(deleteNode);
								return;
							}else if(brotherNode.getNodeColor().equals(BLACK)){
								// 但两红节点存在时，应该统计并更新
								if(brotherNodeLeftChildNode != null && brotherNodeLeftChildNode.getNodeColor().equals("R") && brotherNodeRightChildNode != null && brotherNodeRightChildNode.getNodeColor().equals(RED)){
									// 但兄弟节点的两个子节点均为红色时，更改节点颜色并刷新树
									brotherNodeLeftChildNode.setNodeBlack();
									brotherNodeRightChildNode.setNodeBlack();
									brotherNode.setNodeRed();
									// 刷新树
									refreshTreeForAddMehtod(brotherNode);
									// 删除原节点
									deleteNode(deleteNode);
									return;
								}else if(brotherNodeLeftChildNode != null && brotherNodeLeftChildNode.getNodeColor().equals(RED)){
									// 兄弟节点的左节点为红色，以兄弟节点左节点为轴进行右旋，兄弟节点和其左节点互换颜色
									if(brotherNode.getParentNode() != null){
										if(isTreeNodeLeftOrRight(brotherNode, brotherNode.getParentNode()).equals("L")){
											brotherNode.getParentNode().setLeftNode(brotherNodeLeftChildNode);
										}else if(isTreeNodeLeftOrRight(brotherNode, brotherNode.getParentNode()).equals("R")){
											brotherNode.getParentNode().setRightNode(brotherNodeLeftChildNode);
										}
									}
									brotherNodeLeftChildNode.setParentNode(brotherNode.getParentNode());
									brotherNodeLeftChildNode.setRightNode(brotherNode);
									brotherNode.setParentNode(brotherNodeLeftChildNode);
									brotherNode.setLeftNode(brotherNodeLeftChildNode.getRightNode());
									if(brotherNodeLeftChildNode != null){
										brotherNodeLeftChildNode.setParentNode(brotherNode);
									}
									
									brotherNodeLeftChildNode.setNodeBlack();
									brotherNode.setNodeRed();
									// 删除原节点
									deleteNode(deleteNode);
									return;
								}else if(brotherNodeRightChildNode != null && brotherNodeRightChildNode.getNodeColor().equals(RED)){
									// 但兄弟节点的右节点为红色时，以兄弟节点为轴左旋
									// 父亲节点为黑色，兄弟节点黑色，兄弟节点右节点为红色，以兄弟节点为轴左旋，变更删除节点颜色
									if(parentNode.getParentNode() != null){
										if(isTreeNodeLeftOrRight(parentNode, parentNode.getParentNode()).equals("L")){
											parentNode.getParentNode().setLeftNode(brotherNode);
										}else if(isTreeNodeLeftOrRight(parentNode, parentNode.getParentNode()).equals("R")){
											parentNode.getParentNode().setRightNode(brotherNode);
										}
									}
									brotherNode.setParentNode(parentNode.getParentNode());
									brotherNode.setLeftNode(parentNode);
									parentNode.setParentNode(brotherNode);
									parentNode.setRightNode(brotherNodeLeftChildNode);
									if(brotherNodeLeftChildNode != null){
										brotherNodeLeftChildNode.setParentNode(parentNode);
									}
									
									deleteNode.setNodeRed();
									// 父亲节点为红色，不需要变色，为黑色需要变色
									if(parentNode.getNodeColor().equals(RED)){
										// DO NOTHING
									}else if(parentNode.getNodeColor().equals(BLACK)){
										brotherNodeRightChildNode.setNodeBlack();
									}
									
									// 根节点重定向
									if(parentNode.getNodeVal() == rootNode.getNodeVal()){
										rootNode = brotherNode;
										rootNode.setNodeBlack();
									}
									
									// 删除原节点
									deleteNode(deleteNode);
									return;
								}else{
									// 获取双色节点
									doubleColorNode = deleteNode.getParentNode();
									// 当兄弟节点的子节点都为黑节点时，删除节点，并把兄弟节点置为红色，父亲节点为双色节点
									if(isTreeNodeLeftOrRight(deleteNode, deleteNode.getParentNode()).equals("L")){
										deleteNode.getParentNode().setLeftNode(null);
									}else if(isTreeNodeLeftOrRight(deleteNode, deleteNode.getParentNode()).equals("R")){
										deleteNode.getParentNode().setRightNode(null);
									}
									brotherNode.setNodeRed();
								}
							}
						}
					}
				}else if(deleteNodeLeftChildNode != null && deleteNodeRightChildNode == null){
					// 删除节点只有左子节点
					doubleColorNode = deleteNode.getLeftNode();
					if(deleteNode.getParentNode() != null){
						if(isTreeNodeLeftOrRight(deleteNode, deleteNode.getParentNode()).equals("L")){
							deleteNode.getParentNode().setLeftNode(doubleColorNode);
						}else if(isTreeNodeLeftOrRight(deleteNode, deleteNode.getParentNode()).equals("R")){
							deleteNode.getParentNode().setRightNode(doubleColorNode);
						}
					}
					doubleColorNode.setParentNode(deleteNode.getParentNode());
					
				}else if(deleteNodeLeftChildNode == null && deleteNodeRightChildNode != null){
					// 删除节点只有右子节点
					doubleColorNode = deleteNode.getRightNode();
					if(deleteNode.getParentNode() != null){
						if(isTreeNodeLeftOrRight(deleteNode, deleteNode.getParentNode()).equals("L")){
							deleteNode.getParentNode().setLeftNode(doubleColorNode);
						}else if(isTreeNodeLeftOrRight(deleteNode, deleteNode.getParentNode()).equals("R")){
							deleteNode.getParentNode().setRightNode(doubleColorNode);
						}
					}
					doubleColorNode.setParentNode(deleteNode.getParentNode());
				}else{
					// 删除节点存在两个子节点，替换节点只可能为无子节点节点或有右子节点元素
					TreeNode replaceNode = findReplaceNode(deleteNode.getRightNode());
					// 删除替换节点
					deleteNode(replaceNode);
					// 把删除节点的值更新成替换节点
					deleteNode.setNodeVal(replaceNode.getNodeVal());
					deleteNode.setNodeSaveVal(replaceNode.getNodeSaveVal());
				}
				
				// 根据双色节点刷新树
				refreshTreeWithDoubleColorNode(doubleColorNode);
			}
		}
		logger.debug("删除节点结束 -------------------");
		logger.debug("删除节点结束：" + treeNode);
		logger.debug("删除节点结束，当前节点父节点为：" + treeNode.getParentNode());
		logger.debug("删除节点结束，当前节点左子节点为：" + treeNode.getLeftNode());
		logger.debug("删除节点结束，当前节点右子节点为：" + treeNode.getRightNode());
		logger.debug("删除节点结束，当前根节点为：" + rootNode);
		if(rootNode != null){
			logger.debug("删除节点结束，当前根节点左子节点为：" + rootNode.getLeftNode());
			logger.debug("删除节点结束，当前根节点右子节点为：" + rootNode.getRightNode());
		}
		logger.debug("删除节点结束 -------------------");
	}
	
	// 根据双色节点刷新树
	public synchronized void refreshTreeWithDoubleColorNode(TreeNode treeNode){
		
		if(treeNode == null){
			return;
		}else{
			if(treeNode.getNodeColor().equals(RED)){
				// 双色节点为黑红--设置为红色结束
				treeNode.setNodeBlack();
				// 如果当前节点为根节点刷新根节点
				if(treeNode.getParentNode() == null){
					rootNode = treeNode;
				}
				return;
			}else if(treeNode.getNodeColor().equals(BLACK)){
				// 如果双色节点更新到根节点结束
				if(treeNode.getNodeVal() == rootNode.getNodeVal()){
					return;
				}

				TreeNode parentNode = treeNode.getParentNode();
				logger.debug("当前节点父节点左子节点为：" + treeNode.getParentNode().getLeftNode());
				logger.debug("当前节点父节点右子节点为：" + treeNode.getParentNode().getRightNode());
				// 双色节点为黑黑，固一定存在兄弟节点

				TreeNode brotherNode = treeNode.getBrotherNode();
				// 兄弟节点的子节点用来转换
				TreeNode brotherNodeLeftChildNode = brotherNode.getLeftNode();
				TreeNode brotherNodeRightChildNode = brotherNode.getRightNode();
				// 如果删除节点有兄弟节点
				if(brotherNode.getNodeColor().equals(RED)){
					
					if(isTreeNodeLeftOrRight(treeNode, treeNode.getParentNode()).equals("L")){
						// 双色节点为左节点
						// 以兄弟节点为支点左旋，并交换父节点和兄弟节点颜色
						if(parentNode.getParentNode() != null){
							if(isTreeNodeLeftOrRight(parentNode, parentNode.getParentNode()).equals("L")){
								parentNode.getParentNode().setLeftNode(brotherNode);
							}else if(isTreeNodeLeftOrRight(parentNode, parentNode.getParentNode()).equals("R")){
								parentNode.getParentNode().setRightNode(brotherNode);
							}
						}
						brotherNode.setParentNode(parentNode.getParentNode());
						brotherNode.setLeftNode(parentNode);
						parentNode.setParentNode(brotherNode);
						parentNode.setRightNode(brotherNodeLeftChildNode);
						if(brotherNodeLeftChildNode != null){
							brotherNodeLeftChildNode.setParentNode(parentNode);
						}
						
						brotherNode.setNodeBlack();
						parentNode.setNodeRed();
						
						// 根节点重定向
						if(parentNode.getNodeVal() == rootNode.getNodeVal()){
							rootNode = brotherNode;
							rootNode.setNodeBlack();
						}
						// 更新双色节点
						refreshTreeWithDoubleColorNode(treeNode);
						return;
					}else if(isTreeNodeLeftOrRight(treeNode, treeNode.getParentNode()).equals("R")){
						// 双色节点为右节点
						// 以兄弟节点为支点右旋，并交换父节点和兄弟节点颜色
						if(parentNode.getParentNode() != null){
							if(isTreeNodeLeftOrRight(parentNode, parentNode.getParentNode()).equals("L")){
								parentNode.getParentNode().setLeftNode(brotherNode);
							}else if(isTreeNodeLeftOrRight(parentNode, parentNode.getParentNode()).equals("R")){
								parentNode.getParentNode().setRightNode(brotherNode);
							}
						}
						brotherNode.setParentNode(parentNode.getParentNode());
						brotherNode.setRightNode(parentNode);
						parentNode.setParentNode(brotherNode);
						parentNode.setLeftNode(brotherNodeRightChildNode);
						if(brotherNodeRightChildNode != null){
							brotherNodeRightChildNode.setParentNode(parentNode);
						}
						
						brotherNode.setNodeBlack();
						parentNode.setNodeRed();
						
						// 根节点重定向
						if(parentNode.getNodeVal() == rootNode.getNodeVal()){
							rootNode = brotherNode;
							rootNode.setNodeBlack();
						}
						// 更新双色节点
						refreshTreeWithDoubleColorNode(treeNode);
						return;
					}
				}else if(brotherNode.getNodeColor().equals(BLACK)){
					// 双色节点为所在树的左节点
					if(isTreeNodeLeftOrRight(treeNode, treeNode.getParentNode()).equals("L")){
						// 但两红节点存在时，应该统计并更新
						if(brotherNodeLeftChildNode != null && brotherNodeLeftChildNode.getNodeColor().equals("R") && brotherNodeRightChildNode != null && brotherNodeRightChildNode.getNodeColor().equals(RED)){
							// 但兄弟节点的两个子节点均为红色时，更改节点颜色并刷新树
							brotherNodeLeftChildNode.setNodeBlack();
							brotherNodeRightChildNode.setNodeBlack();
							brotherNode.setNodeRed();
							// 刷新树
							refreshTreeForAddMehtod(brotherNode);
							// 更新双色节点
							refreshTreeWithDoubleColorNode(treeNode);
							return;
						}else if(brotherNodeLeftChildNode != null && brotherNodeLeftChildNode.getNodeColor().equals(RED)){
							// 兄弟节点的左节点为红色，以兄弟节点左节点为轴进行右旋，兄弟节点和其左节点互换颜色
							if(brotherNode.getParentNode() != null){
								if(isTreeNodeLeftOrRight(brotherNode, brotherNode.getParentNode()).equals("L")){
									brotherNode.getParentNode().setLeftNode(brotherNodeLeftChildNode);
								}else if(isTreeNodeLeftOrRight(brotherNode, brotherNode.getParentNode()).equals("R")){
									brotherNode.getParentNode().setRightNode(brotherNodeLeftChildNode);
								}
							}
							brotherNodeLeftChildNode.setParentNode(brotherNode.getParentNode());
							brotherNodeLeftChildNode.setRightNode(brotherNode);
							brotherNode.setParentNode(brotherNodeLeftChildNode);
							brotherNode.setLeftNode(brotherNodeLeftChildNode.getRightNode());
							if(brotherNodeLeftChildNode.getRightNode() != null){
								brotherNodeLeftChildNode.getRightNode().setParentNode(brotherNode);
							}
							
							brotherNodeLeftChildNode.setNodeBlack();
							brotherNode.setNodeRed();
							// 更新双色节点
							refreshTreeWithDoubleColorNode(treeNode);
							return;
						}else if(brotherNodeRightChildNode != null && brotherNodeRightChildNode.getNodeColor().equals(RED)){
							// 但兄弟节点的右节点为红色时，以兄弟节点为轴左旋
							// 父亲节点为黑色，兄弟节点黑色，兄弟节点右节点为红色，以兄弟节点为轴左旋，变更删除节点颜色
							if(parentNode.getParentNode() != null){
								if(isTreeNodeLeftOrRight(parentNode, parentNode.getParentNode()).equals("L")){
									parentNode.getParentNode().setLeftNode(brotherNode);
								}else if(isTreeNodeLeftOrRight(parentNode, parentNode.getParentNode()).equals("R")){
									parentNode.getParentNode().setRightNode(brotherNode);
								}
							}
							brotherNode.setParentNode(parentNode.getParentNode());
							brotherNode.setLeftNode(parentNode);
							parentNode.setParentNode(brotherNode);
							parentNode.setRightNode(brotherNodeLeftChildNode);
							if(brotherNodeLeftChildNode != null){
								brotherNodeLeftChildNode.setParentNode(parentNode);
							}
							
							if(parentNode.getNodeColor().equals(RED)){
								// DO NOTHING
							}else if(parentNode.getNodeColor().equals(BLACK)){
								brotherNodeRightChildNode.setNodeBlack();
							}
							
							// 根节点重定向
							if(parentNode.getNodeVal() == rootNode.getNodeVal()){
								rootNode = brotherNode;
								rootNode.setNodeBlack();
							}
							// 此时双色节点应转换为红，减少一层黑色，轮询结束
							// treeNode.setNodeBlack();
							return;
						}else{
							// 此时兄弟节点更新为红，当前节点父节点变为双色节点
							brotherNode.setNodeRed();
							// 双色节点解决
							refreshTreeWithDoubleColorNode(treeNode.getParentNode());
							return;
						}
					}else if(isTreeNodeLeftOrRight(treeNode, treeNode.getParentNode()).equals("R")){
						// 但两红节点存在时，应该统计并更新
						if(brotherNodeLeftChildNode != null && brotherNodeLeftChildNode.getNodeColor().equals("R") && brotherNodeRightChildNode != null && brotherNodeRightChildNode.getNodeColor().equals(RED)){
							// 但兄弟节点的两个子节点均为红色时，更改节点颜色并刷新树
							brotherNodeLeftChildNode.setNodeBlack();
							brotherNodeRightChildNode.setNodeBlack();
							brotherNode.setNodeRed();
							// 刷新树
							refreshTreeForAddMehtod(brotherNode);
							// 更新双色节点
							refreshTreeWithDoubleColorNode(treeNode);
							return;
						}else if(brotherNodeRightChildNode != null && brotherNodeRightChildNode.getNodeColor().equals(RED)){
							// 兄弟节点的右节点为红色，以兄弟节点左节点为轴进行左旋，兄弟节点和其左节点互换颜色
							if(brotherNode.getParentNode() != null){
								if(isTreeNodeLeftOrRight(brotherNode, brotherNode.getParentNode()).equals("L")){
									brotherNode.getParentNode().setLeftNode(brotherNodeRightChildNode);
								}else if(isTreeNodeLeftOrRight(brotherNode, brotherNode.getParentNode()).equals("R")){
									brotherNode.getParentNode().setRightNode(brotherNodeRightChildNode);
								}
							}
							brotherNodeRightChildNode.setParentNode(brotherNode.getParentNode());
							brotherNodeRightChildNode.setLeftNode(brotherNode);
							brotherNode.setParentNode(brotherNodeRightChildNode);
							brotherNode.setRightNode(brotherNodeRightChildNode.getLeftNode());
							if(brotherNodeRightChildNode.getLeftNode() != null){
								brotherNodeRightChildNode.getLeftNode().setParentNode(brotherNode);
							}
							
							brotherNodeRightChildNode.setNodeBlack();
							brotherNode.setNodeRed();
							// 更新双色节点
							refreshTreeWithDoubleColorNode(treeNode);
							return;
						}else if(brotherNodeLeftChildNode != null && brotherNodeLeftChildNode.getNodeColor().equals(RED)){
							// 但兄弟节点的左节点为红色时，以兄弟节点为轴右旋
							// 父亲节点为黑色，兄弟节点黑色，兄弟节点左节点为红色，以兄弟节点为轴右旋，变更删除节点颜色
							if(parentNode.getParentNode() != null){
								if(isTreeNodeLeftOrRight(parentNode, parentNode.getParentNode()).equals("L")){
									parentNode.getParentNode().setLeftNode(brotherNode);
								}else if(isTreeNodeLeftOrRight(parentNode, parentNode.getParentNode()).equals("R")){
									parentNode.getParentNode().setRightNode(brotherNode);
								}
							}
							brotherNode.setParentNode(parentNode.getParentNode());
							brotherNode.setRightNode(parentNode);
							parentNode.setParentNode(brotherNode);
							parentNode.setLeftNode(brotherNodeRightChildNode);
							if(brotherNodeRightChildNode != null){
								brotherNodeRightChildNode.setParentNode(parentNode);
							}
							// 此时双色节点应转换为红，减少一层黑色，轮询结束
							// treeNode.setNodeBlack();
							
							if(parentNode.getNodeColor().equals(RED)){
								// DO NOTHING
							}else if(parentNode.getNodeColor().equals(BLACK)){
								brotherNodeLeftChildNode.setNodeBlack();
							}
							
							// 根节点重定向
							if(parentNode.getNodeVal() == rootNode.getNodeVal()){
								rootNode = brotherNode;
								rootNode.setNodeBlack();
							}
							return;
						}else{
							// 此时兄弟节点更新为红，当前节点父节点变为双色节点
							brotherNode.setNodeRed();
							// 双色节点解决
							refreshTreeWithDoubleColorNode(treeNode.getParentNode());
							return;
						}
					}
					
				}
			}
		}
	}
	
	// 寻找指定节点
	public TreeNode findNode(TreeNode paramNode, TreeNode startNode){
		
		// 找到合适的左右节点或当前节点
		TreeNode chooseNode = startNode.compareAndChooseChild(paramNode);
		if(chooseNode == null){
			// 查找不到对应的值
			return null;
		}else if(chooseNode.getNodeVal().equals(paramNode.getNodeVal())){
			// 查找到对应的值，返回当前节点
			return chooseNode;
		}else{
			// 未找到值，轮询进入子节点树寻找
			return findNode(paramNode, chooseNode);			
		}
	}
	
	// 寻找适用的替换节点
	public TreeNode findReplaceNode(TreeNode paramNode){
		
		// 找到合适的替换节点
		TreeNode chooseNode = paramNode.chooseLeftChild();
		if(chooseNode.getNodeVal() == paramNode.getNodeVal()){
			// 查找到对应的值，返回当前节点
			return chooseNode;
		}else{
			// 未找到值，轮询进入子节点树寻找
			return findReplaceNode(chooseNode);		
		}
	}
	
	// 当前节点为父节点的左节点还是右节点
	public static String isTreeNodeLeftOrRight(TreeNode paramNode, TreeNode parentNode){
		if(parentNode != null && parentNode.getLeftNode() != null && paramNode.getNodeVal() == parentNode.getLeftNode().getNodeVal()){
			return "L";
		}else if(parentNode != null && parentNode.getRightNode() != null && paramNode.getNodeVal() == parentNode.getRightNode().getNodeVal()){
			return "R";
		}else{
			return "M";
		}
	}

	// 寻找指定节点
	public TreeNode findNode(TreeNode paramNode){
		return findNode(paramNode, rootNode);
	}
	
	// 获取数的所有节点
	public List<TreeNode> getAllTreeNode(){
		List<TreeNode> treeNodeAll = new ArrayList<TreeNode>();
		TreeNode rootNodeLocal = rootNode;
		List<TreeNode> currentArray = new ArrayList<TreeNode>();
		currentArray.add(rootNodeLocal);

		logger.debug("----------------树所有节点统计-------------------");
		while(currentArray != null && currentArray.size()>0){
			boolean isEmpty = true;
			List<TreeNode> nextArrayList = new ArrayList<TreeNode>();
			for(int i=0; i<currentArray.size(); i++){
				
				TreeNode currNode = currentArray.get(i);
				if(currNode != null){
					if(currNode.getNodeSaveVal() != null){
						treeNodeAll.add(currNode);
					}else{
					}
					if(currNode.getLeftNode() != null){
						nextArrayList.add(currNode.getLeftNode());
						isEmpty = false;
					}else{
						TreeNode emptyNode = new TreeNode();
						nextArrayList.add(emptyNode);
					}
					if(currNode.getRightNode() != null){
						nextArrayList.add(currNode.getRightNode());
						isEmpty = false;
					}else{
						TreeNode emptyNode = new TreeNode();
						nextArrayList.add(emptyNode);
					}
				}
			}
			if(isEmpty){
				currentArray = null;
			}else{
				currentArray = new ArrayList<TreeNode>();
				for(int i=0; i<nextArrayList.size(); i++){
					currentArray.add(nextArrayList.get(i));
				}
			}
		}
		return treeNodeAll;
	}
	
	// 显示树结构
	public void displayRbTree(){
		
		TreeNode rootNodeLocal = rootNode;
		List<TreeNode> currentArray = new ArrayList<TreeNode>();
		currentArray.add(rootNodeLocal);

		logger.debug("----------------树结构展示开始-------------------");
		while(currentArray != null && currentArray.size()>0){
			boolean isEmpty = true;
			List<TreeNode> nextArrayList = new ArrayList<TreeNode>();
			String currentDisplayItem = "";
			for(int i=0; i<currentArray.size(); i++){
				
				TreeNode currNode = currentArray.get(i);
				if(currNode != null){
					if(currNode.getNodeSaveVal() != null){
						currentDisplayItem += "  " + currNode.getNodeColor() + " : " + currNode.getNodeVal() + "  ";
					}else{
						currentDisplayItem += "  E:~~@  ";
					}
					if(currNode.getLeftNode() != null){
						nextArrayList.add(currNode.getLeftNode());
						isEmpty = false;
					}else{
						TreeNode emptyNode = new TreeNode();
						nextArrayList.add(emptyNode);
					}
					if(currNode.getRightNode() != null){
						nextArrayList.add(currNode.getRightNode());
						isEmpty = false;
					}else{
						TreeNode emptyNode = new TreeNode();
						nextArrayList.add(emptyNode);
					}
				}
				
			}
			logger.debug(currentDisplayItem);
			if(isEmpty){
				currentArray = null;
			}else{
				currentArray = new ArrayList<TreeNode>();
				for(int i=0; i<nextArrayList.size(); i++){
					currentArray.add(nextArrayList.get(i));
				}
			}
		}
		logger.debug("----------------树结构展示结束-------------------");
		
	}
	
}
