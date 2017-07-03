package com.solar.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServlet;

import com.solar.utils.CopyFileUtil;

import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyListener;
import com.solar.thread.*;

public class FileWatch extends HttpServlet implements Runnable {

	String file1 = "D:\\��ͼ��Ŀ\\zip\\";
	String file2 = "D:\\��ͼ��Ŀ\\zip2\\";

	public static void main(String[] args) {
		try {

			new FileWatch().sampleTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 

	public void sampleTest() throws Exception {
		// path to watch
		String path = "D:\\��ͼ��Ŀ\\zip";

		// watch mask, specify events you care about,
		// or JNotify.FILE_ANY for all events.
		int mask = JNotify.FILE_CREATED | JNotify.FILE_DELETED | JNotify.FILE_MODIFIED | JNotify.FILE_RENAMED;
		System.out.println(44);
		// watch subtree?
		boolean watchSubtree = true;
		System.load("D:/��ͼ��Ŀ/jnotify-lib-0.94/jnotify_64bit.dll");

		// add actual watch
		int watchID = JNotify.addWatch(path, mask, watchSubtree, new Listener());

		// sleep a little, the application will exit if you
		// don't (watching is asynchronous), depending on your
		// application, this may not be required
		Thread.sleep(1000000);

		// to remove watch the watch
		boolean res = JNotify.removeWatch(watchID);
		if (!res) {
			// invalid watch ID specified.
		}
	}

	class Listener implements JNotifyListener {
		public void fileRenamed(int wd, String rootPath, String oldName, String newName) {
			print("renamed " + rootPath + " : " + oldName + " -> " + newName);
			File oldFile = new File(file2 + oldName);
			File newFile = new File(file2 + newName);
			if (oldFile.exists())
				oldFile.renameTo(newFile);
			else{
				CopyFileUtil copyFileObject = new CopyFileUtil();
				copyFileObject.copyFile(file1 + newName, file2 + newName, true);
			}
				 
		}

		public void fileModified(int wd, String rootPath, String name) {
			print("modified " + rootPath + " : " + name);

			File file = new File(file1 + name);
			if (file.isFile()) {
				// int index = name.lastIndexOf("\\");
				// if(index > 0)
				// name = name.substring(0,index);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				CopyFileUtil copyFileObject = new CopyFileUtil();
				copyFileObject.copyFile(file1 + name, file2 + name, true);
				System.out.println(123);
				//
			} else if (file.isDirectory()) {
				File file2Path = new File(file2 + name);
				System.out.println("cssssss");
				if (!file2Path.exists())
					file2Path.mkdirs();
			}
		}

		public void fileDeleted(int wd, String rootPath, String name) {
			print("deleted " + rootPath + " : " + name);
			File file = new File("D:\\��ͼ��Ŀ\\zip2\\�½��ļ���\\delete.txt");
			try {
				if (!file.exists())
					file.createNewFile();

				String content = System.lineSeparator() + file + name;
				FileOutputStream o = null;

				o = new FileOutputStream(file, true);
				o.write(content.getBytes("GBK"));
				o.close();

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		public void fileCreated(int wd, String rootPath, String name) {
			print("created " + rootPath + " : " + name);

			File file = new File(file1 + name);
			if (file.isFile()) {
				// int index = name.lastIndexOf("\\");
				// if(index > 0)
				// name = name.substring(0,index);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				CopyFileUtil copyFileObject = new CopyFileUtil();
				copyFileObject.copyFile(file1 + name, file2 + name, true);
				System.out.println(123);
				//
			} else {
				File file2Path = new File(file2 + name);
				if (!file2Path.exists())
					file2Path.mkdirs();
			}
		}

		void print(String msg) {
			System.err.println(msg);
		}
	}
	
	public void init(){
		FileWatch fileWatch = new FileWatch();
		Thread thread =new Thread(fileWatch);
		thread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			sampleTest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

