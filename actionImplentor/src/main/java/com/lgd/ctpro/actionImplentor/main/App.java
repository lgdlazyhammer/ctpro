package com.lgd.ctpro.actionImplentor.main;

import com.lgd.ctpro.actionImplentor.intrs.TestJnaLibrary;

public class App {
	
	public static void main(String[] args) {
		
		boolean isPrime = TestJnaLibrary.INSTANCE.isPrime(6);
		System.out.println("isPrime = " + isPrime);
	}
}
