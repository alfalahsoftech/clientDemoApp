package com.alfalahsoftech.alframe.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtility {

	public static String readFile(String fileName) {
		File file = new File(fileName);
		String fileData = "";
		try {
			FileInputStream fin = new FileInputStream(file);
			byte[] data = new byte[fin.available()];
			fin.read(data);
			fileData = new String(data);
			fin.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Not Available");
		} catch (IOException e) {
		
			e.printStackTrace();
		}

		return fileData.trim();
	}
	
	public static String readFile(String folder,String fileName) {
		File file = new File(folder,fileName);
		String fileData = "";
		try {
			FileInputStream fin = new FileInputStream(file);
			byte[] data = new byte[fin.available()];
			fin.read(data);
			fileData = new String(data);
			fin.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Not Available");
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}

		return fileData.trim();
	}
	
	public static String readFileString(String fileName) throws IOException {
		File file = new File(fileName);
		InputStream is = new FileInputStream(file); 
		BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
		String line = buf.readLine(); StringBuilder sb = new StringBuilder(); 
		while(line != null){ sb.append(line).append("\n"); 
		line = buf.readLine(); 
		} 
		return sb.toString();
	}

	public static void writeFile(String fileName, byte[] fileData) {
		try {
			FileOutputStream fout = new FileOutputStream(fileName);
			fout.write(fileData);
			fout.flush();
			fout.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Not Available");
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}
	
	public static void writeFile(String folder,String fileName, byte[] fileData) {
		File file = new File(folder,fileName);
		try {
			FileOutputStream fout = new FileOutputStream(file);
			fout.write(fileData);
			fout.flush();
			fout.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Not Available");
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}
	
	public static void appendFile(String fileName, byte[] fileData) {
		try {
			FileOutputStream fout = new FileOutputStream(fileName,true);
			fout.write(fileData);
			fout.flush();
			fout.close();
		} catch (FileNotFoundException e) {
			System.err.println("File Not Available");
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}
}
