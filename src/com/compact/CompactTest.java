package com.compact;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class CompactTest {
	
	public static void main(String[] args) {
		String path1 = "D:\\海图项目\\sql\\CompactTest1.class";
		String path2 = "D:\\海图项目\\sql\\CompactTest2.class";
		
		  
		String md5P1 = getFileMD5(new File(path1));
		String md5P2 = getFileMD5(new File(path2));
		System.out.println(md5P1);
		System.out.println(md5P2);
		System.out.println(md5P1.equals(md5P2));
	}
	
	// 计算文件的 MD5 值
	public static String getFileMD5(File file) {
	    if (!file.isFile()) {
	        return null;
	    }
	    MessageDigest digest = null;
	    FileInputStream in = null;
	    byte buffer[] = new byte[8192];
	    int len;
	    try {
	        digest =MessageDigest.getInstance("MD5");
	        in = new FileInputStream(file);
	        while ((len = in.read(buffer)) != -1) {
	            digest.update(buffer, 0, len);
	        }
	        BigInteger bigInt = new BigInteger(1, digest.digest());
	        return bigInt.toString(16);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        try {
	            in.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	  
	}

}
