
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
    private static final String DUMMIE = "du";
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
                setState(finalStateT1, DUMMIE, -1, -1);//Setting a dummie state for the last machine to connect to 
                state++;
                e = state;//Setting start state of the branching machine
                setState(e, BRANCH, -1, -1);

                index++;// Consuming character
                state++;

                r2 = findDisjunction();
                //Correcting both of the next states
                setState(e, BRANCH, r, r2);
                setState(finalStateT1, DUMMIE, state, state);
                //Setting the start place
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
                // Connecting the the term and the concatination
                laststate = state - 1;
                nextState = findconcatenation();
                setState(laststate, null, nextState, nextState);
            }

            return r;
        } catch (Exception e) {
            error();
            return -1;
        }

    }

    private static int findTerm() {

        try {
            int r;
            r = findFactor();

            if (index == newRegexp.length()) {
                return r;
            }

            if (newRegexp.charAt(index) == '*') {
                index++;//Consuming the character
                setState(state, BRANCH, r, state + 1);
                r = state;//Setting the start of the branching machine
                state++;
                setState(state, DUMMIE, state + 1, state + 1);
                state++;
            } 
            else if (newRegexp.charAt(index) == '?') {
                index++;// Consuming the character
                int expressionStart = state;
                setState(state, DUMMIE, r, state + 1);// Dummie state
                state++;

                // For the special case of disjunction - fixes a very specific bug maybe not needed i.e. (a|b)?
                if (characterArray.get(r).compareTo(BRANCH) == 0) {
                    setState(r - 1, null, state, state);
                    setState(r + 1, null, state, state);
                } else {
                    setState(r, null, state, state);
                }

                // setState(r, null, state, state);
                setState(state, DUMMIE, state + 1, state + 1);
                state++;
                return expressionStart;
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
                index++;
                setState(state, String.valueOf(newRegexp.charAt(index)), state + 1, state + 1);
                index++;

                r = state;
                state++;

            } else if (newRegexp.charAt(index) == '(') {
                index++;
                // System.out.println(index);
                r = findDisjunction();
                if ((index < newRegexp.length()) && newRegexp.charAt(index) == ')') {
                    index++;
                } else {
                    error();
                }
            } else if (validVocab(newRegexp.charAt(index))) {

                setState(state, String.valueOf(newRegexp.charAt(index)), state + 1, state + 1);
                r = state;
                index++;
                state++;
            } else if (newRegexp.charAt(index) == '.') {
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
