//Mason Elliot Connor Jones

import java.util.*;
import java.io.*;

public class REsearch {

	//Global arrays
	private ArrayList<String> characterArray = new ArrayList<String>();
    	private ArrayList<Integer> nextStateOne = new ArrayList<Integer>();
    	private ArrayList<Integer> nextStateTwo = new ArrayList<Integer>();
    	private ArrayList<Boolean> visited = new ArrayList<Boolean>();
    	
    	//Global variables 
    	private boolean found;
    	private REdeque deque;
    	private int m = 0, p = 0;

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
			//Variable init
			deque = new REdeque();
			BufferedReader tableIn = new BufferedReader(new InputStreamReader(System.in));
			FileReader file = new FileReader(new File(args[0]));
			BufferedReader fileIn = new BufferedReader(file);
			String line;
			String[] inputArr;
			int s1, s2, state;
		
			//Reads the table and sets it up for our search.
			while ((line = tableIn.readLine()) != null){
			inputArr = line.split(",");
			characterArray.add(inputArr[1]);
			nextStateOne.add(Integer.parseInt(inputArr[2]));
			nextStateTwo.add(Integer.parseInt(inputArr[3]));
			visited.add(false);
			}
			tableIn.close();
			
			//Reads every line of the file
			while ((line = fileIn.readLine()) != null){
				found = false;
				//Reads every character in that line
				for (int i = 0; i < line.length(); i++){
					m = i;
					p = i;
					
					//Setup Deque
					deque.clear();
					deque.addFront(0);
					deque.addRear(-2);
					
					//Reset visited to false
					resetVisited();
					
					//Proccesses every state in the deque
					while (true){
						//No characters to match states left. Move m forward.
						if (deque.isEmpty())
							break;
						
						//Get top of current states stack.
						state = deque.removeFront();
						
						//Proccesses the current state
						if (checkState(state, line))
							break;
						
					}
					//Stops loop so we read next line once we have found a match in this line.
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
	
	//Resets visited back to false for every index.
	public void resetVisited(){
		for(int i = 0; i < visited.size(); i++)
			visited.set(i, false);
	}
	
	//Takes the state, identifies it, and then proccesses it based on what it is.
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
			if (!(p >= line.length()))
				if (characterArray.get(state).compareTo("wildcard") == 0)
					deque.addRear(nextStateOne.get(state));	
				else{
					//Check the character, if same increase pointer
					if (line.charAt(p) == characterArray.get(state).charAt(0)){
						//Add these onto stack
						deque.addRear(nextStateOne.get(state));
						visited.set(state, true);	
					}
				}				
		}
		return false;
	}
}
