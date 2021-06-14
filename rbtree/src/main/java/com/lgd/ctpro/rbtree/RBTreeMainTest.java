package com.lgd.ctpro.rbtree;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RBTreeMainTest {
	
	private static Logger logger = LogManager.getLogger(RBTreeMainTest.class);

	public static void main(String[] args) {
		RBTreeManager rbTreeManager = new RBTreeManager();
		for(int i=1; i<20; i++){
			TreeNode tempNode = new TreeNode();
			tempNode.setNodeVal(formatString(String.valueOf(i)));
			tempNode.setNodeSaveVal(String.valueOf(i));
			
			logger.debug("增加节点开始" + i + " ：" + System.currentTimeMillis());
			rbTreeManager.addNode(tempNode);
			logger.debug("增加节点结束" + i + " ：" + System.currentTimeMillis());

			// DisplayRBTree.displayRbTree();
		}
		rbTreeManager.displayRbTree();

		logger.debug("删除节点开始：" + System.currentTimeMillis());
		for(int i=1; i<20; i++){
			// DisplayRBTree.displayRbTree();
			TreeNode tempNode = new TreeNode();
			tempNode.setNodeVal(formatString(String.valueOf(i)));
			tempNode.setNodeSaveVal(String.valueOf(i));
			rbTreeManager.deleteNode(tempNode);
			rbTreeManager.displayRbTree();
		}
	}
	
	public static String formatString(String param){
		
		String result = null;
		if(param != null && param.length() == 1){
			return "0" + param;
		}else if(param != null && param.length() == 2){
			return param;
		}
		return result;
	}

}

