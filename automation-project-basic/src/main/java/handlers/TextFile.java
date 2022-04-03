package handlers;

import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

public class TextFile {
		
	public static ArrayList <String> ds_details;
	
	public static List<String> readFile(String fileName) {
		List<String> lines = Collections.emptyList();		
		try {			
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);			
		} catch (IOException e) {e.printStackTrace();}
		return lines;
	}
	
	public static void keyStore (List l) {		
		Iterator<String> itr = l.iterator();				
		while (itr.hasNext()) {
			String s[] = itr.next().split(":");
			System.out.println(s[0]);			
			System.out.println(s[1]);			
		}
	}

	public static void main(String[] args) {		
		List l = readFile("F:\\cipher\\msg2.txt.enc");				
		keyStore(l);
	}
	
}