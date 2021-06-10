package com.lgd.ctpro.rbtree;

public class RBTreeMainTest {

	public static void main(String[] args) {
		
		for(int i=1; i<20; i++){
			TreeNode tempNode = new TreeNode();
			tempNode.setNodeVal(formatString(String.valueOf(i)));
			tempNode.setNodeSaveVal(String.valueOf(i));
			
			System.out.println("增加节点开始" + i + " ：" + System.currentTimeMillis());
			RBTreeManager.addNode(tempNode);
			System.out.println("增加节点结束" + i + " ：" + System.currentTimeMillis());

			// DisplayRBTree.displayRbTree();
		}
		DisplayRBTree.displayRbTree();

		System.out.println("删除节点开始：" + System.currentTimeMillis());
		for(int i=1; i<20; i++){
			// DisplayRBTree.displayRbTree();
			TreeNode tempNode = new TreeNode();
			tempNode.setNodeVal(formatString(String.valueOf(i)));
			tempNode.setNodeSaveVal(String.valueOf(i));
			RBTreeManager.deleteNode(tempNode);
			DisplayRBTree.displayRbTree();
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

