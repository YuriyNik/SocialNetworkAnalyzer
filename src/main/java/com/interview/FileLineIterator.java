package com.interview;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class FileLineIterator implements Iterator<String> {

	private BufferedReader br;
	private String line;
	
	public FileLineIterator(String fileName) throws FileNotFoundException {
		br = new BufferedReader(new FileReader(fileName));		
		line = null;
	}
	
	@Override
	public boolean hasNext() {
		try {
			line = br.readLine();
			return line != null;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String next() {
		return line;
	}
}
