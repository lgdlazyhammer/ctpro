package com.lgd.ctpro.rbtree;

public class TreeNode {

	final private String BLACK = "B";
	final private String RED = "R";
	
	private TreeNode leftNode;// 左子节点
	private TreeNode rightNode;// 右子节点
	private TreeNode parentNode;// 父母节点
	private String nodeColor;// 树节点颜色
	private String nodeVal;// 树节点的保存值和比较值
	private Object nodeSaveVal;// 节点保存的值
	
	public TreeNode getLeftNode() {
		return leftNode;
	}
	public void setLeftNode(TreeNode leftNode) {
		this.leftNode = leftNode;
	}
	public TreeNode getRightNode() {
		return rightNode;
	}
	public void setRightNode(TreeNode rightNode) {
		this.rightNode = rightNode;
	}
	public TreeNode getParentNode() {
		return parentNode;
	}
	public void setParentNode(TreeNode parentNode) {
		this.parentNode = parentNode;
	}
	public String getNodeColor() {
		return nodeColor;
	}
	public void setNodeColor(String nodeColor) {
		this.nodeColor = nodeColor;
	}
	public String getNodeVal() {
		return nodeVal;
	}
	public void setNodeVal(String nodeVal) {
		this.nodeVal = nodeVal;
	}
	public void setNodeBlack(){
		nodeColor = BLACK;
	}
	public void setNodeRed(){
		nodeColor = RED;
	}
	public Object getNodeSaveVal() {
		return nodeSaveVal;
	}
	public void setNodeSaveVal(Object nodeSaveVal) {
		this.nodeSaveVal = nodeSaveVal;
	}
	
	// 比较并返回子节点
	public TreeNode compareAndChooseChild(TreeNode paramNode){
		
		int compareRes = paramNode.getNodeVal().compareTo(nodeVal);
		if( compareRes > 0){
			return rightNode;
		}else if(compareRes < 0){
			return leftNode;
		}else{
			return this;
		}
	}
	
	// 比较并返回左子节点，如果无左子节点，返回当前节点
	public TreeNode chooseLeftChild(){
		
		if(leftNode != null){
			return leftNode;
		}else{
			return this;
		}
	}
	
	// 为节点增加子节点
	public void addChildNode(TreeNode paramNode){

		if(paramNode.getNodeVal().compareTo(nodeVal) > 0){
			// 继承父节点的右子节点 -- 红黑树增加节点时，从最低层开始，没有需要继承的子节点
			rightNode = paramNode;
			paramNode.setParentNode(this);
		}else if(paramNode.getNodeVal().compareTo(nodeVal) < 0){
			// 继承父节点的左子节点 -- 红黑树增加节点时，从最低层开始，没有需要继承的子节点
			leftNode = paramNode;
			paramNode.setParentNode(this);
		}
	}
	
	// 获得兄弟节点
	public TreeNode getBrotherNode(){
		
		// 获得祖父节点
		if(parentNode != null){
			if(parentNode.getLeftNode() != null && parentNode.getLeftNode().getNodeVal() == nodeVal){
				// 获得兄弟节点--父节点存在两个子节点，且左边节点为当前节点
				return parentNode.getRightNode();
			}else if(parentNode.getRightNode() != null && parentNode.getRightNode().getNodeVal() == nodeVal){
				// 获得兄弟节点--父节点存在两个子节点，且右边节点为当前节点
				return parentNode.getLeftNode();
			}else{
				// 父节点只有一个子节点
				return null;
			}
		}else{
			return null;
		}
	}
	
	@Override
	public String toString() {
		return "TreeNode [nodeColor=" + nodeColor + ", nodeVal=" + nodeVal + ", nodeSaveVal="
				+ nodeSaveVal + "]";
	}
}
