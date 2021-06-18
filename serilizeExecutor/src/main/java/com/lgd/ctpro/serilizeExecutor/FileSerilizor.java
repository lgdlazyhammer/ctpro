package com.lgd.ctpro.serilizeExecutor;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lgd.ctpro.core.entity.CtproAction;
import com.lgd.ctpro.core.entity.CtproExecution;
import com.lgd.ctpro.core.entity.CtproOrder;
import com.lgd.ctpro.core.entity.CtproTask;
import com.lgd.ctpro.core.service.CtproCoreServiceManager;
import com.lgd.ctpro.core.tools.EncryptTool;
import com.lgd.ctpro.rbtree.RBTreeManager;
import com.lgd.ctpro.rbtree.TreeNode;

public class FileSerilizor implements SerlizorIntr{
	
	private static Logger logger = LogManager.getLogger(FileSerilizor.class);

	String fileGenLoc;
	String ctproOrderFile;
	String ctproTaskFile;
	String ctproExecutionFile;
	String ctproActionFile;

	public FileSerilizor() {
		super();
		Properties properties = new Properties();
		// 使用ClassLoader加载properties配置文件生成对应的输入流
		InputStream in = FileSerilizor.class.getClassLoader().getResourceAsStream("settings.properties");
		// 使用properties对象加载输入流
		try {
			properties.load(in);
		} catch (IOException e) {
			logger.error(e.getStackTrace());
		}
		// 获取key对应的value值
		fileGenLoc = properties.getProperty("fileSerilizeLocation");
		ctproOrderFile = properties.getProperty("fileSerilizeCtproOrder");
		ctproTaskFile = properties.getProperty("fileSerilizeCtproTask");
		ctproExecutionFile = properties.getProperty("fileSerilizeCtproExecution");
		ctproActionFile = properties.getProperty("fileSerilizeCtproAction");
	}

	public void serilize() {
		logger.debug("文件序列化的地址：" + fileGenLoc);
		logger.debug("订单记录文件：" + ctproOrderFile);
		logger.debug("任务记录文件：" + ctproTaskFile);
		logger.debug("执行记录文件：" + ctproExecutionFile);
		logger.debug("动作记录文件：" + ctproActionFile);
		
		List<TreeNode> orderRBTreeList = CtproCoreServiceManager.getInstance().getOrderRBTreeManager().getAllTreeNode();
		List<TreeNode> taskRBTreeList = CtproCoreServiceManager.getInstance().getTaskRBTreeManager().getAllTreeNode();
		List<TreeNode> executionRBTreeList = CtproCoreServiceManager.getInstance().getExecutionRBTreeManager().getAllTreeNode();
		List<TreeNode> actionRBTreeList = CtproCoreServiceManager.getInstance().getActionRBTreeManager().getAllTreeNode();
		
		serilizeSpecifiedClassAll(orderRBTreeList, CtproOrder.class, fileGenLoc + ctproOrderFile);
		serilizeSpecifiedClassAll(taskRBTreeList, CtproTask.class, fileGenLoc + ctproTaskFile);
		serilizeSpecifiedClassAll(executionRBTreeList, CtproExecution.class, fileGenLoc + ctproExecutionFile);
		serilizeSpecifiedClassAll(actionRBTreeList, CtproAction.class, fileGenLoc + ctproActionFile);
		
	}

	public void deSerilize() {
		logger.debug("文件序列化的地址：" + fileGenLoc);
		logger.debug("订单记录文件：" + ctproOrderFile);
		logger.debug("任务记录文件：" + ctproTaskFile);
		logger.debug("执行记录文件：" + ctproExecutionFile);
		logger.debug("动作记录文件：" + ctproActionFile);
		
		deserilizeSpecifiedClassAll(CtproCoreServiceManager.getInstance().getOrderRBTreeManager(), CtproOrder.class, fileGenLoc + ctproOrderFile);
		deserilizeSpecifiedClassAll(CtproCoreServiceManager.getInstance().getTaskRBTreeManager(), CtproTask.class, fileGenLoc + ctproTaskFile);
		deserilizeSpecifiedClassAll(CtproCoreServiceManager.getInstance().getExecutionRBTreeManager(), CtproExecution.class, fileGenLoc + ctproExecutionFile);
		deserilizeSpecifiedClassAll(CtproCoreServiceManager.getInstance().getActionRBTreeManager(), CtproAction.class, fileGenLoc + ctproActionFile);
		
	}
	
	// 将对象按照制定类型序列化为字节
	public byte[] serilizeSpecifiedClassItem(TreeNode param, Class classTyp){
		return (Base64Tool.encode(SerializeUtils.serialize(param.getNodeSaveVal(), classTyp))+"\r\n").getBytes();
	}
	
	// 将列表数据读入文件
	public void serilizeSpecifiedClassAll(List<TreeNode> treeList, Class classTyp, String fileLoc){
		
		File destFile = new File(fileLoc);
		// 每次都创建新的文件
		if(destFile.exists()){
			destFile.delete();
		}
		try {
			destFile.createNewFile();
		} catch (IOException e) {
			logger.error(e.getStackTrace());
		}

		// 将字节码写进文件
		for(int i=0; i<treeList.size(); i++){
			byte[] itemBytes = serilizeSpecifiedClassItem(treeList.get(i), classTyp);
			try {
				writeFileByBytes(fileLoc, itemBytes, true);
			} catch (IOException e) {
				logger.error(e.getStackTrace());
			}
		}
	}
	
	/**
	 * 向文件写入byte[]
	 * 
	 * @param fileName 文件名
	 * @param bytes    字节内容
	 * @param append   是否追加
	 * @throws IOException
	 */
	public static void writeFileByBytes(String fileName, byte[] bytes, boolean append) throws IOException {
		try(OutputStream out = new BufferedOutputStream(new FileOutputStream(fileName, append))){
			out.write(bytes);
		}
	}
	
	// 将文件数据读入列表
	public void deserilizeSpecifiedClassAll(RBTreeManager rbTreeManager, Class classTyp, String fileLoc){
		
		try {
			FileInputStream fis = new FileInputStream(fileLoc);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			
			String line = null;
			
			while((line = br.readLine()) != null){
				if(!line.trim().equals("")){
					if(classTyp.equals(CtproOrder.class)){
						CtproOrder ctproOrder = SerializeUtils.deSerialize(Base64Tool.decode(line), CtproOrder.class);
						// 命令节点增加
						// ctproOrder.setOrderId(EncryptTool.getMD5ByBase64(ctproOrder.getOrderMsg()));
						TreeNode treeNode = new TreeNode();
						treeNode.setNodeVal(ctproOrder.getOrderId());
						treeNode.setNodeSaveVal(ctproOrder);
						rbTreeManager.addNode(treeNode);
					}else if(classTyp.equals(CtproTask.class)){
						CtproTask ctproTask = SerializeUtils.deSerialize(Base64Tool.decode(line), CtproTask.class);
						// 任务节点增加
						// ctproTask.setTaskid(EncryptTool.getMD5ByBase64(ctproTask.getTaskMsg()));
						TreeNode treeNode = new TreeNode();
						treeNode.setNodeVal(ctproTask.getTaskid());
						treeNode.setNodeSaveVal(ctproTask);
						rbTreeManager.addNode(treeNode);
					}else if(classTyp.equals(CtproExecution.class)){
						CtproExecution ctproExecution = SerializeUtils.deSerialize(Base64Tool.decode(line), CtproExecution.class);
						// 执行节点增加
						// ctproExecution.setExecutionId(EncryptTool.getMD5ByBase64(ctproExecution.getExecutionMsg()));
						TreeNode treeNode = new TreeNode();
						treeNode.setNodeVal(ctproExecution.getExecutionId());
						treeNode.setNodeSaveVal(ctproExecution);
						rbTreeManager.addNode(treeNode);
					}else if(classTyp.equals(CtproAction.class)){
						CtproAction ctproAction = SerializeUtils.deSerialize(Base64Tool.decode(line), CtproAction.class);
						// 动作节点增加
						// ctproAction.setActionid(EncryptTool.getMD5ByBase64(ctproAction.getActionMsg()));
						TreeNode treeNode = new TreeNode();
						treeNode.setNodeVal(ctproAction.getActionid());
						treeNode.setNodeSaveVal(ctproAction);
						rbTreeManager.addNode(treeNode);
					}
				}
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getStackTrace());
		} catch (IOException e) {
			logger.error(e.getStackTrace());
		}
	}

}
