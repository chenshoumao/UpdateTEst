package com.solar.nio;

import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.nio.ByteBuffer;  
import java.nio.channels.FileChannel;  
import java.util.Arrays;  
  
  
public class Test {  
      
    public static final int BUFSIZE = 1024 * 8;  
      
    public static void mergeFiles(String outFile, String[] files) {  
        FileChannel outChannel = null;  
        System.out.println("Merge " + Arrays.toString(files) + " into " + outFile);  
        try {  
            outChannel = new FileOutputStream(outFile).getChannel();  
            for(String f : files){  
                FileChannel fc = new FileInputStream(f).getChannel();   
                ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);  
                while(fc.read(bb) != -1){  
                    bb.flip();  
                    outChannel.write(bb);  
                    bb.clear();  
                }  
                fc.close();  
            }  
            System.out.println("Merged!! ");  
        } catch (IOException ioe) {  
            ioe.printStackTrace();  
        } finally {  
            try {if (outChannel != null) {outChannel.close();}} catch (IOException ignore) {}  
        }  
    }  
      
    public static void main(String[] args) {  
        mergeFiles("D:/海图项目/sql/output.txt", new String[]{"D:/海图项目/sql/text1.sql", "D:/海图项目/sql/text2.sql"});  
    }  
}  