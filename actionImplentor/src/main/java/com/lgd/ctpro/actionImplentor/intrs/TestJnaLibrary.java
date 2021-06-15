package com.lgd.ctpro.actionImplentor.intrs;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface TestJnaLibrary extends Library{

	TestJnaLibrary INSTANCE = Native.loadLibrary("browser.dll", TestJnaLibrary.class);
    
	/**
     * 初始化SDK 注意：调用SDK其他接口前必须先调用此接口！
     *
     * @return TRUE表示成功，FALSE表示失败
     */
    boolean VixHz_InitSDK();
    
}
