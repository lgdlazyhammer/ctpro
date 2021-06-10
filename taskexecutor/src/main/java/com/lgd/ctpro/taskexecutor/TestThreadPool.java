package com.lgd.ctpro.taskexecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @ClassName: TestThreadPool
 * @Description: test thread manager
 * @author liguodong
 * @date 2018年5月7日 下午5:47:52
 *
 */
public class TestThreadPool {

	public static void main(String[] args) {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String s;
			ThreadPoolManager manager = new ThreadPoolManager(10);
			while ((s = br.readLine()) != null) {
				// manager.process(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
