package com.solar.thread;

import com.solar.servlet.FileWatch;

public class MyThread implements Runnable {
	// Tomcat����������ִ��
	
	 
	
	@Override
	public void run() {
		// ���߳���Ҫ��������
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

