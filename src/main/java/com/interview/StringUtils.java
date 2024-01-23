package com.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtils {

	/**
	 * Receives a string and tokenize to list of strings according to the provided delimiter
	 * 
	 * @param str the string to tokenize
	 * @param delimiter the delimiter
	 * 
	 * @return list of strings tokenized by the provided delimiter
	 */
	public static List<String> tokenize(String str, char delimiter) {
		StringTokenizer tokenizer = new StringTokenizer(str, String.valueOf(delimiter));
		List<String> tokens = new ArrayList<>();
		while (tokenizer.hasMoreTokens()) {
			tokens.add(tokenizer.nextToken());
		}
		
		return tokens;
	}
}
