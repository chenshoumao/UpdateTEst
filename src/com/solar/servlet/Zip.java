package com.solar.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Zip
 */
@WebServlet("/Zip")
public class Zip extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	String filePath = ("D:\\��ͼ��Ŀ\\zip");

	// Ҫѹ���� �ļ��� ·��
	String sourcePath = "D:/��ͼ��Ŀ/zip2";

	// ѹ���ļ���·��
	String outPutZipPath = "D:/��ͼ��Ŀ/zip3/zip.zip"; 
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Zip() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    public static void zip(String sourcePath, String outPutZipPath) {
		try {
			// �ļ������
			FileOutputStream fout = new FileOutputStream(outPutZipPath);

			// ��Ҫά��д������У��͵��������У��Ϳ�������֤������ݵ������ԡ�
			CheckedOutputStream checkOut = new CheckedOutputStream(fout, new CRC32());
			// zip��ʽ�����
			ZipOutputStream zipOput = new ZipOutputStream(checkOut);

			// ��һ��Դ�ļ�д�뵽һ��ѹ���ļ���
			File sourceFile = new File(sourcePath);

			// ѹ����Ŀ
			String zipEntryName = sourceFile.getName();
			zipDire(zipOput, sourceFile, zipEntryName);
			zipOput.closeEntry();
			zipOput.close();
			fout.close();
			System.out.println(checkOut.getChecksum().getValue());
			checkOut.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// ѹ��Ŀ¼
	public static void zipDire(ZipOutputStream zipOput, File sourceFile, String zipEntryName)
			throws IOException, FileNotFoundException {
		for (File file : sourceFile.listFiles()) {
			// String name = sourcePath + "/" + "���رؿ�/" + file.getName() ;
			if (file.isFile())
				ZipFile(file.toString(), zipOput, zipEntryName + "/" + file.getName());
			else {
				if (file.listFiles().length > 0)
					zipDire(zipOput, file, zipEntryName + "/" + file.getName());
				else
					zipOput.putNextEntry(new ZipEntry(zipEntryName + "/" + file.getName() + "/"));
			}
		}

	}

	// ѹ���ļ�
	public static void ZipFile(String sourcePath, ZipOutputStream zipOput, String zipEntryName)
			throws IOException, FileNotFoundException {
		// ��һ��Ҫѹ�����ļ�д�뵽ѹ����Ŀ��
		zipOput.putNextEntry(new ZipEntry(zipEntryName));

		// ����Ҫѹ�����ļ�
		FileInputStream fileInput = new FileInputStream(sourcePath);

		byte[] buff = new byte[1024];
		int length;

		while ((length = fileInput.read(buff)) > 0) {
			zipOput.write(buff, 0, length);
		}

		fileInput.close();
	}
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		out.println("hello world");
		zip(sourcePath, outPutZipPath);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
