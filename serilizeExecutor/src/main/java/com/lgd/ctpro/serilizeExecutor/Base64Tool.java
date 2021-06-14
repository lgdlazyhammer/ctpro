package com.lgd.ctpro.serilizeExecutor;

import java.io.IOException;
import java.util.Base64;

public class Base64Tool {

	static Base64.Decoder decoder = Base64.getDecoder();
	static Base64.Encoder encoder = Base64.getEncoder();
	
	/**
	 * 采用BASE64算法对字节数组进行加密
	 * @param baseBuff 原字节数组
	 * @return 加密后的字符串
	 */
	public static final String encode(byte[] baseBuff){
		return encoder.encodeToString(baseBuff);
	}
	
	/**
	 * 字符串解密，采用BASE64的算法
	 * @param encoder 需要解密的字符串
	 * @return 解密后的字符串
	 */
	public static final byte[] decode(String param) throws IOException{
		return decoder.decode(param);
	}
	
}
