package com.lgd.ctpro.core.tools;

import java.security.MessageDigest;
import java.util.Base64;

/*
 * 加密公共方法
 */
public class EncryptTool {

	/**
	 * MD5加密,加密结果采用Base64进行编码
	 * 
	 * @param message
	 *            要进行MD5加密的字符串
	 * @return
	 */
	public static String getMD5ByBase64(String message) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] md5 = md.digest(message.getBytes());
			Base64.Encoder base64Encoder = Base64.getEncoder().withoutPadding();
			return base64Encoder.encodeToString(md5);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
