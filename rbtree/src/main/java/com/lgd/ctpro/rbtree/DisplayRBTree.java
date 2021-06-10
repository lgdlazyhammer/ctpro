package com.lgd.ctpro.rbtree;

import java.util.ArrayList;
import java.util.List;

public class DisplayRBTree {

	public static void displayRbTree(){
		
		TreeNode rootNode = RBTreeManager.rootNode;
		List<TreeNode> currentArray = new ArrayList<TreeNode>();
		currentArray.add(rootNode);

		System.out.println("----------------树结构展示开始-------------------");
		while(currentArray != null && currentArray.size()>0){
			boolean isEmpty = true;
			List<TreeNode> nextArrayList = new ArrayList<TreeNode>();
			for(int i=0; i<currentArray.size(); i++){
				
				TreeNode currNode = currentArray.get(i);
				if(currNode != null){
					if(currNode.getNodeSaveVal() != null){
						System.out.print("  " + currNode.getNodeColor() + " : " + currNode.getNodeVal() + "  ");
					}else{
						System.out.print("  E:~~@  ");
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
			System.out.println("  ");
			if(isEmpty){
				currentArray = null;
			}else{
				currentArray = new ArrayList<TreeNode>();
				for(int i=0; i<nextArrayList.size(); i++){
					currentArray.add(nextArrayList.get(i));
				}
			}
		}
		System.out.println("----------------树结构展示结束-------------------");
		
	}
}
