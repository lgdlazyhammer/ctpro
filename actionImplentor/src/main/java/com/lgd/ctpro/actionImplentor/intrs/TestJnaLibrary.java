package com.lgd.ctpro.actionImplentor.intrs;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface TestJnaLibrary extends Library{

	TestJnaLibrary INSTANCE = Native.loadLibrary("Dll1.dll", TestJnaLibrary.class);
    boolean isPrime(int param);
    
}
