package com.lgd.ctpro.actionImplentor.main;

import com.lgd.ctpro.actionImplentor.intrs.TestJnaLibrary;

/**
 * Hello world!
 *
 */
public class App {
	
	public static void main(String[] args) {
		boolean initSDKResult = TestJnaLibrary.INSTANCE.VixHz_InitSDK();
		System.out.println("initSDKResult = " + initSDKResult);
	}
}
