package com.solar.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.LinkedList;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServlet;

import com.solar.utils.CopyFileUtil;

public class UnZip extends HttpServlet implements Runnable {
	
	private static String updatePath = "D:/��ͼ��Ŀ/zip4";
	private  static String sourceUpdatePath = "";
	

	public static void monitor(){ 

		// ����ļ�·��
		String outPath = "D:/��ͼ��Ŀ/zip4"; 
		String filePath = ("D:\\��ͼ��Ŀ\\zip3");

		try {

		 

			// ��ȡ�ļ�ϵͳ��WatchService����
			WatchService watchService = FileSystems.getDefault().newWatchService();

			Paths.get(filePath).register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

			// ��Ҫ������ļ�
			File file = new File(filePath);
			LinkedList<File> fList = new LinkedList<File>();
			fList.addLast(file);
			while (fList.size() > 0) {
				File f = fList.removeFirst();
				if (f.listFiles() == null)
					continue;
				for (File file2 : f.listFiles()) {
					if (file2.isDirectory()) {// ��һ��Ŀ¼
						fList.addLast(file2);
						// ����ע����Ŀ¼
						Paths.get(file2.getAbsolutePath()).register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
								StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
					}
				}
			}

			while (true) {
				// ��ȡ��һ���ļ��Ķ��¼�
				WatchKey key = watchService.take();
				for (WatchEvent<?> event : key.pollEvents()) {
					if ((event.kind().toString()).equals("ENTRY_CREATE")) {
						System.out.println(event.context() + " --> " + event.kind());
						String zipPath = traverseFolder2(filePath);
						Thread.sleep(3000);
						unzip(zipPath, outPath);
						//updateFile("","");
					}
					// System.out.println(event.kind() +"," + ((
					// event.kind().toString()).equals("ENTRY_CREATE")));

				}
				// ����WatchKey
				boolean valid = key.reset();
				// �������ʧ�ܣ��˳�����
				if (!valid) {
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public static String traverseFolder2(String path) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("�ļ����ǿյ�!");
                return "";
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("�ļ���:" + file2.getAbsolutePath());
                        return file2.getAbsolutePath();
                      //  traverseFolder2(file2.getAbsolutePath());
                    } else {
                        System.out.println("�ļ�:" + file2.getAbsolutePath());
                        return file2.getAbsolutePath();
                    }
                }
            }
        } else {
            System.out.println("�ļ�������!");
            return "";
        }
		return "";
    }

	public static void unzip(String sourcePath, String outPath) {
		try {
			// �ļ�������
			FileInputStream fin = new FileInputStream(sourcePath);

			// ��Ҫά������ȡ����У��͵���������У��Ϳ�������֤�������ݵ�������
			CheckedInputStream checkIn = new CheckedInputStream(fin, new CRC32());
			//ָ������ �������������ļ���ѹ����
			Charset gbk = Charset.forName("GBK");
			// zip��ʽ�������� 
			ZipInputStream zin = new ZipInputStream(checkIn,gbk);

			// ����ѹ���ļ��е�����ѹ����Ŀ
			ZipEntry zinEntry;
			while ((zinEntry = zin.getNextEntry()) != null) {
				System.out.println(zinEntry);
				File targetFile = new File(outPath + File.separator + zinEntry.getName());
				
				System.out.println("..." + targetFile + "   " + targetFile.getParentFile());
				
				sourceUpdatePath = targetFile.toString();
				if (!targetFile.getParentFile().exists()) {
					System.out.println("..." + targetFile + "   " + targetFile.getParentFile());
					targetFile.getParentFile().mkdirs();
				}
				if (zinEntry.isDirectory()) {
					targetFile.mkdirs();
				} else {
					FileOutputStream fout = new FileOutputStream(targetFile);
					byte[] buff = new byte[1024];
					int length;
					while ((length = zin.read(buff)) > 0) {
						fout.write(buff, 0, length);
					}
					fout.close();
				}
			}
//			Thread.sleep(3000);
//			CopyFileUtil.copyDirectory(sourceUpdatePath,updatePath, true);
			
			
			zin.close();
			fin.close();
			System.out.println(checkIn.getChecksum().getValue());
			checkIn.close();
			
			Thread.sleep(4000);
			File file = new File(sourcePath);
			try {
				if(file.exists())
					file.delete();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
	}

	public static void updateFile(String resourcePath,String desPath){
		 File file = new File(resourcePath);
	        if (file.exists()) {
	            File[] files = file.listFiles();
	            if (files.length == 0) {
	                System.out.println("�ļ����ǿյ�!");
	                
	            } else {
	                for (File file2 : files) {
	                    if (file2.isDirectory()) {
	                        System.out.println("�ļ���:" + file2.getAbsolutePath());
	                       
	                      //  traverseFolder2(file2.getAbsolutePath());
	                    } else {
	                        System.out.println("�ļ�:" + file2.getAbsolutePath());
	                        
	                    }
	                }
	            }
	        } else {
	            System.out.println("�ļ�������!");
	             
	        }
	}
	
	public void init(){
		UnZip unzip = new UnZip();
		Thread thread =new Thread(unzip);
		thread.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(123);
		monitor();
		
	}
	public static void main(String[] args) {
		monitor();
	}
	
}