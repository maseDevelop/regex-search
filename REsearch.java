//Name: Mason Elliot, Connor Jones
//ID: 1347257, 1351782

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
			//BufferedReader tableIn = new BufferedReader(new InputStreamReader(System.in));
			FileReader file = new FileReader(new File(args[0]));
			BufferedReader fileIn = new BufferedReader(file);
			
			//Variable init
			deque = new REdeque();
			int s1, s2, state;
			String line;
		
			//Setting up the table.
			setupTable();
			
			//Reads every line of the file
			while ((line = fileIn.readLine()) != null){
				//Reset found since this is now a new line.
				found = false;
				//Reads every character in that line
				for (int i = 0; i < line.length(); i++){
					//Reset p to the starting point of the search.
					p = i;
					
					//Setup Deque
					deque.clear();
					deque.addFront(0);
					deque.addRear(-2);
					
					//Reset visited to false
					resetVisited();
					
					//Proccesses every state in the deque
					while (true){
						//No characters to match states left. Move starting char forward forward.
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
	
	//Sets the table up that will be used to find the patterns.
	public void setupTable(){
		try{
			//Variable setup
			BufferedReader tableIn = new BufferedReader(new InputStreamReader(System.in));
			String line;
			String[] inputArr;
	
			//Reads the table and sets it up for our search.
			while ((line = tableIn.readLine()) != null){
				//Split each line into their seperate parts
				inputArr = line.split(",");
				//Check to see if we're not getting the expected input
				if(inputArr.length != 4){
					
					System.err.println(line);
					while ((line = tableIn.readLine()) != null)
						System.err.println(line);
					System.exit(1);
				}
				//Put each part into their corresponding array
				characterArray.add(inputArr[1]);
				nextStateOne.add(Integer.parseInt(inputArr[2]));
				nextStateTwo.add(Integer.parseInt(inputArr[3]));
				visited.add(false);
			}
			//Have read the whole file so close the reader.
			tableIn.close();
		}
		catch(Exception e){
			System.err.print("Error: " + e);
		}
	}
	
	//Takes the state, identifies it, and then proccesses it based on what it is.
	public boolean checkState(int state, String line){
	
		if(state == -2){
			//See if there is more in the stack if so move to back
			if (deque.isEmpty())
				return true;
			
			//If add the SCAN to the back to make the queue now the current state stack.
			deque.addRear(-2);
			//Say we have now proccessed this character.
			p++;
			//Since we have used a character reset the visited states on the table.
			resetVisited();	
		}
		else if (visited.get(state) == true){
			//If we're in a visited state we just want to discard this state.
			return false;
		
		}
		else if (characterArray.get(state).compareTo("st") == 0 || 
			characterArray.get(state).compareTo("du") == 0){
			//For the start and the dummie states we want to add their next state one to the current states.
			deque.addFront(nextStateOne.get(state));
			//Set this state to visited to avoid infinite loops
			visited.set(state, true);
									
		}
		else if (characterArray.get(state).compareTo("br") == 0){
			//Add both the branches nexts states to the top of deque to check
			deque.addFront(nextStateTwo.get(state));
			deque.addFront(nextStateOne.get(state));
			//Set this state to visited to avoid infinite loops
			visited.set(state, true);
			
		}
		else if (nextStateOne.get(state) == -1){
			//We have reached the end state, set the found variable to true so the next loop stop proccessing this line.
			found = true;
			//Output this line.
			System.out.println(line);
			//End this loop.
			return true;
		}
		else{//Else we're in a state that wants to check a character
			//If there are still characters to check against
			if (!(p >= line.length()))
				//If it's a wild character
				if (characterArray.get(state).compareTo("wc") == 0){
					//We want to just add it to the queue without checking and end.
					deque.addRear(nextStateOne.get(state));
					//Stop infinite loops
					visited.set(state, true);
				}
				else{
					//Check the character to see if they match
					if (line.charAt(p) == characterArray.get(state).charAt(0)){
						//Since they match add the next state to the queue
						deque.addRear(nextStateOne.get(state));
						//Stop infinite loops
						visited.set(state, true);	
					}
				}				
		}
		return false;
	}
}
