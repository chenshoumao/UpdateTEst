package com.solar.nio;

import java.io.File;
import java.io.FileFilter;

public class MyFileFileter implements FileFilter{

    @Override
    public boolean accept(File pathname) {
        if(pathname.isDirectory()){
        	System.out.println(pathname);
            return true;
        }
        return false;
    }

}