//Mason Elliot Connor Jones

import java.util.*;
import java.io.*;

public class REsearch {

	private static LinkedList<String> characterArray = new LinkedList<String>();
    	private static LinkedList<Integer> nextStateOne = new LinkedList<Integer>();
    	private static LinkedList<Integer> nextStateTwo = new LinkedList<Integer>();
    	
    	public int m = 0, p = 0;

	public static void main(String[] args) {
		if (args.length != 1){
			System.err.println("Usage: java REsearch <Search File> < table");
		}
		
		REsearch e = new REsearch();
		e.run(args);
	}
	
	public void run(String[] args){
		try{
			BufferedReader tableIn = new BufferedReader(new InputStreamReader(System.in));
			FileReader fileIn = new FileReader(new File(args[0]));
			
			String line;
			String[] inputArr;
			int s1, s2;
		
			while ((line = tableIn.readLine()) != null){
			inputArr = line.split(",");
			characterArray.add(inputArr[1]);
			nextStateOne.add(Integer.parseInt(inputArr[2]));
			nextStateTwo.add(Integer.parseInt(inputArr[3]));
			}
			tableIn.close();
			
			
			while ((line = fileIn.readLine()) != null){
				for (int i = o; i < line.length - ; i++){
					m = i;
				}
			}
			
		}
		catch (Exception ex){
			System.out.println("Error: " + ex);
		}
	}
	
	public void dump(){
		for (int i = 0; i < characterArray.size(); i++){
			System.out.println(characterArray.get(i) + " " + nextStateOne.get(i) + " " + nextStateTwo.get(i));
		}
	}
}
