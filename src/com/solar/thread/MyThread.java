package com.solar.thread;

import com.solar.servlet.FileWatch;

public class MyThread implements Runnable {
	// Tomcat启动结束后执行
	
	 
	
	@Override
	public void run() {
		// 子线程需要做的事情
		FileWatch file = new FileWatch();
		try {
			file.sampleTest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(123);
	}
}

