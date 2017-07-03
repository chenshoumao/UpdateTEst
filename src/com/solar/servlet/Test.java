package com.solar.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.solar.servlet.FileWatch.Listener;
import com.solar.thread.MyThread;
import com.solar.utils.CopyFileUtil;

import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyListener;

/**
 * Servlet implementation class Test
 */
 
public class Test extends HttpServlet {  
	String file1 = "D:\\海图项目\\zip\\";
	String file2 = "D:\\海图项目\\zip2\\";
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
        MyThread read = new MyThread();
        // 使用另一个线程来执行该方法，会避免占用Tomcat的启动时间
        new Thread(read).start();
      }

   
    public void sampleTest() throws Exception {
		// path to watch
		String path = "D:\\海图项目\\zip";

		// watch mask, specify events you care about,
		// or JNotify.FILE_ANY for all events.
		int mask = JNotify.FILE_CREATED | JNotify.FILE_DELETED | JNotify.FILE_MODIFIED | JNotify.FILE_RENAMED;

		// watch subtree?
		boolean watchSubtree = true;
		System.load("D:/海图项目/jnotify-lib-0.94/jnotify_64bit.dll");

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
			else
				System.out.println("文件不存在！");
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
			} else {
				File file2Path = new File(file2 + name);
				if (!file2Path.exists())
					file2Path.mkdirs();
			}
		}

		public void fileDeleted(int wd, String rootPath, String name) {
			print("deleted " + rootPath + " : " + name);
			File file = new File("D:\\海图项目\\zip\\新建文件夹\\delete.txt");
			String content = System.lineSeparator() + file + name;
			FileOutputStream o = null;

			try {
				o = new FileOutputStream(file,true);
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
 
	
}

class MyThreadq implements Runnable {
	  // Tomcat启动结束后执行
	  @Override
	  public void run() {
	    // 子线程需要做的事情
		  Test test = new Test();
		  try {
			test.sampleTest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  System.out.println(123);
	  }
	}


