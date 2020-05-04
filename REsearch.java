//Mason Elliot Connor Jones

import java.util.*;
import java.io.*;

public class REsearch {

	private ArrayList<String> characterArray = new ArrayList<String>();
    	private ArrayList<Integer> nextStateOne = new ArrayList<Integer>();
    	private ArrayList<Integer> nextStateTwo = new ArrayList<Integer>();
    	private ArrayList<Boolean> visited = new ArrayList<Boolean>();
    	
    	boolean found;
    	
    	
    	REdeque deque;
    	
    	public int m = 0, p = 0;

	public static void main(String[] args) {
		if (args.length != 1){
			System.err.println("Usage: java REsearch <Search File> < table");
			return;
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
					resetVisited();
					
					while (true){
						//No characters to match states left. Move m forward.
						if (deque.isEmpty()){
							break;
						}
						
						//Get top of current states stack.
						state = deque.removeFront();
						
						//Work the state
						if (checkState(state, line))
							break;
						
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
	
	public void resetVisited(){
		for(int i = 0; i < visited.size(); i++)
			visited.set(i, false);
	}
	
	public boolean checkState(int state, String line){
	
		if(state == -2){
			//See if there is more in the stack if so move to back
			if (deque.isEmpty())
				return true;
			deque.addRear(-2);
			p++;
			resetVisited();	
						
		}else if (visited.get(state) == true){
			return false;
		
		}else if (characterArray.get(state).compareTo("start") == 0 || 
			characterArray.get(state).compareTo("dummie") == 0){
			//Comment
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
			return true;
			
		}else{
			if (characterArray.get(state).compareTo("wildcard") == 0){
				//comment
				deque.addRear(nextStateOne.get(state));							
			}else{
				//Comment		
				if (!(p >= line.length())){
					//Check the character, if same increase pointer
					char c = line.charAt(p);
					char e = characterArray.get(state).charAt(0);
					if (c == e){
						//Add these onto stack
						deque.addRear(nextStateOne.get(state));
						visited.set(state, true);	
					}
				}	
			}				
		}
		return false;
	}
}
