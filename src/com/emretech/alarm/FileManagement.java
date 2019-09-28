package com.emretech.alarm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileManagement {
	public static FileReader fileReader;
	public static FileWriter fileWriter;
	public static BufferedReader reader;
	public static PrintWriter printWriter;
	
	public static void createFile(File file) {
		try {
			file.createNewFile();
		} catch (IOException e) {
			createFile(file);
		}
		
	}
	public static void writeToFile(File file, String string) {
		try {
			fileWriter = new FileWriter(file, false);
			printWriter = new PrintWriter(fileWriter);
			printWriter.println(string);
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			createFile(file);
		}
		
	}
	public static void appendToFile(File file, String string) {
		try {
			fileWriter = new FileWriter(file, true);
			printWriter = new PrintWriter(fileWriter);
			printWriter.println(string);
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {}
	}
	public static void readLineFromFile(File file, ArrayList<String> list) {
		try {
			fileReader = new FileReader(file);
			reader = new BufferedReader(fileReader);
			String line;
			try {
				while ((line = reader.readLine()) != null) {
					list.add(line);
				}
				fileReader.close();
			} catch (IOException e) {}
		} catch (FileNotFoundException e) {
			createFile(file);
		}
		
	}
	
}
