
//Mason Elliott Connor Jones
//1347257 1351782
import java.util.*;

public class REcompile {

    private static String newRegexp;
    private static int index = 0;
    private static int state = 1;
    private static char[] restrictedCharacters = { '?', '*', '.', '\\', '|', '(', ')' };
    private static final String WILDCARD = "wc";
    private static final String BRANCH = "br";
    private static final String DUMMIE = "du";//Pass throught exit state(easier for concationation)
    private static final String START = "st";

    private static LinkedList<String> characterArray = new LinkedList<String>();
    private static LinkedList<Integer> nextStateOne = new LinkedList<Integer>();
    private static LinkedList<Integer> nextStateTwo = new LinkedList<Integer>();

    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                newRegexp = args[0];

                // intilising the fsm
                characterArray.add(START);
                nextStateOne.add(-1);
                nextStateTwo.add(-1);

                //Finding the inital state of the fsm
                int initialState = findDisjunction();

                // If an early exit from tree, raise error
                if (index != newRegexp.length()) {
                    error();
                }

                // Setting the start state that has been returned to start of fsm
                setState(0, START, initialState, initialState);
                setState(state, "end", -1, -1);

                writeToOutput();

            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("Proper Usage is: java filename \"regularExpression\"");

            System.exit(0);
        }
    }

    // Lowest precedence
    private static int findDisjunction() {

        try {
            int r;

            r = findconcatenation();//Getting intial state of the fsm machine 

            if (index == newRegexp.length()) {
                return r;
            }

            if (newRegexp.charAt(index) == '|') {
                int r2, finalStateT1, e;

                finalStateT1 = state;
                setState(finalStateT1, DUMMIE, -1, -1);//initialises space in the list for dummie state
                state++;
                e = state;//Setting start state of the branching machine
                setState(e, BRANCH, -1, -1);//initialisng space in the list for branch

                index++;// Consuming character
                state++;

                r2 = findDisjunction();
                
                //Creating another branching machine that is the pass through exit of the branching states
                setState(state,DUMMIE,state+1,state+1);
                state++;

                //Correcting both of the next states
                setState(e, BRANCH, r, r2);
                setState(finalStateT1, DUMMIE, state-1, state-1);
                //Setting the start place of the branching machine
                r = e;
            }

            return r;
        } catch (Exception e) {
            error();

            return -1;
        }
    }

    //Second lowest precedence
    private static int findconcatenation() {

        try {
            int r, nextState, laststate;

            r = findTerm();

            if (index == newRegexp.length()) {
                return r;
            }

            if (validVocab(newRegexp.charAt(index)) || newRegexp.charAt(index) == '(' || newRegexp.charAt(index) == '\\'
                    || newRegexp.charAt(index) == '.') {
                // Connecting the last term of the previous machine to the start of the next one
                laststate = state - 1;//Last state that can be used because state is always looking ahead
                nextState = findconcatenation();
                setState(laststate, null, nextState, nextState);
            }

            return r;
        } catch (Exception e) {
            error();
            return -1;
        }

    }

    //Third Lowest precedence 
    private static int findTerm() {

        try {
            int r;
            r = findFactor();

            if (index == newRegexp.length()) {
                return r;
            }
            //Checks for closure
            if (newRegexp.charAt(index) == '*') {
                index++;//Consuming the character
                
                setState(state, BRANCH, r, state + 1);//Connnecting the last state and the next state into a branching machine
                r = state;//Setting the start of the branching machine
                state++;

                setState(state, DUMMIE, state + 1, state + 1);
                state++;
            }
            //Checks for ? option
            else if (newRegexp.charAt(index) == '?') {
                index++;// Consuming the character
                int expressionStart = state;
        
                setState(state, BRANCH, r, state + 1);
                state++;

                //connecting the end of the last machine to the end of this branch to avoid repition. 
                setState(state-2, null, state, state);

                setState(state, DUMMIE, state + 1, state + 1);
                state++;

                //return expressionStart
                r=expressionStart;
            }

            return r;

        } catch (Exception e) {
            error();
            return -1;
        }

    }
    
    private static int findFactor() {

        try {

            int r = state;

            if (newRegexp.charAt(index) == '\\') {
                index++;//Consuming Character
                setState(state, String.valueOf(newRegexp.charAt(index)), state + 1, state + 1);
                index++;//Consuming character
                r = state;//returning the start state of this machine
                state++;

            } else if (newRegexp.charAt(index) == '(') {
                index++;
                r = findDisjunction();//(d) finding the expression in the brackets
                if ((index < newRegexp.length()) && newRegexp.charAt(index) == ')') {
                    index++;
                } else {
                    error();
                }
            } else if (validVocab(newRegexp.charAt(index))) { //Checking for literal 
                setState(state, String.valueOf(newRegexp.charAt(index)), state + 1, state + 1);
                r = state;
                index++;
                state++;
            } else if (newRegexp.charAt(index) == '.') { //Checking for wildcard
                index++;
                setState(state, WILDCARD, state + 1, state + 1);
                r = state;
                state++;
            } else {
                error();
            }

            return r;
        } catch (Exception e) {
            error();
            return -1;
        }

    }

    // Checks to see if a char is valid vocab
    private static boolean validVocab(char c) {
        for (int i = 0; i < restrictedCharacters.length; i++) {
            if (c == restrictedCharacters[i]) {
                return false;
            }
        }
        return true;
    }

    // Error exception
    private static void error() {

        if (!(validVocab(newRegexp.charAt(index)))) {
            if (newRegexp.charAt(index) == '(' || newRegexp.charAt(index) == ')') {
                System.out.println(
                        "Error: Not valid Expression, make sure you have a bracket pair or and something in your brackets ");
                state++;
            } else {
                System.out.println("Error: Not valid Expression ");
                System.out.println(
                        "Error: If you wish to use special characters please use the escape key in the preceding index \"\\\" ");
            }
        } else {
            System.out.println("Somthing went wrong");
        }

        System.out.println("Error at index: " + state);
        System.exit(1);
    }

    // Setting State for the FSM
    private static void setState(int s, String c, int n1, int n2) {

        if (s == characterArray.size()) {
            characterArray.add(c);
            nextStateOne.add(n1);
            nextStateTwo.add(n2);
        }
        //States may be overidden to change the place they branch to
        else if (s < characterArray.size()) {
            nextStateOne.set(s, n1);
            nextStateTwo.set(s, n2);
        }

    }

    private static void writeToOutput() {
        for (int i = 0; i < characterArray.size(); i++) {
            System.out.print(i + ",");
            System.out.print(characterArray.get(i) + ",");
            System.out.print(nextStateOne.get(i) + ",");
            System.out.print(nextStateTwo.get(i));
            System.out.println();
        }
    }
}
