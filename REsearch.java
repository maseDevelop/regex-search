//Mason Elliot Connor Jones

import java.util.*;
import java.io.*;

public class REsearch {

	private LinkedList<String> characterArray = new LinkedList<String>();
    	private LinkedList<Integer> nextStateOne = new LinkedList<Integer>();
    	private LinkedList<Integer> nextStateTwo = new LinkedList<Integer>();
    	private LinkedList<Boolean> visited = new LinkedList<Boolean>();
    	
    	
    	REdeque deque;
    	
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
			deque = new REdeque();
		
			BufferedReader tableIn = new BufferedReader(new InputStreamReader(System.in));
			FileReader file = new FileReader(new File(args[0]));
			BufferedReader fileIn = new BufferedReader(file);
			
			String line;
			String[] inputArr;
			int s1, s2, state;
			boolean found;
		
			while ((line = tableIn.readLine()) != null){
			inputArr = line.split(",");
			characterArray.add(inputArr[1]);
			nextStateOne.add(Integer.parseInt(inputArr[2]));
			nextStateTwo.add(Integer.parseInt(inputArr[3]));
			visited.add(false);
			}
			tableIn.close();
			
			
			while ((line = fileIn.readLine()) != null){
				found = false;
				for (int i = 0; i < line.length(); i++){
					m = i;
					p = i;
					
					//Setup Deque
					deque.clear();
					deque.addFront(0);
					deque.addRear(-2);
					
					//Reset visited to false
					
					while (true){
						//No characters to match states left. Move m forward.
						if (deque.isEmpty()){
							break;
						}
						
						//Find match
						state = deque.removeFront();
						//System.out.println(state);
						
						if(state == -2){
							//See if there is more in the stack if so move to back
							if (deque.isEmpty())
								break;
							deque.addRear(-2);
							
						}else if (characterArray.get(state).compareTo("start") == 0 || 
							characterArray.get(state).compareTo("Dummie") == 0){
							
							deque.addFront(nextStateOne.get(state));
							visited.set(state, true);
							
						}else if (characterArray.get(state).compareTo("branch") == 0){
							//Add both the nexts states to the top of deque to check
							deque.addFront(nextStateTwo.get(state));
							deque.addFront(nextStateOne.get(state));
							visited.set(state, true);
						
						}else if (nextStateOne.get(state) == -1){
							//Logic to say we have found match
							found = true;
							System.out.println(line);
							break;
							
						}else{
							if (p >= line.length())
								break;
							//Check the character, if same increase pointer
							char c = line.charAt(p);
							char e = characterArray.get(state).charAt(0);
							System.out.println("Comparing: " + c + " " + e);
							if (c == e){
								//add potential states to stack
								deque.addRear(nextStateOne.get(state));
								p++;
							}
							
							
						}
						
					}
					if (found)
						break;
				}
			}
			fileIn.close();
			
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
