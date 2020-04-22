import java.util.*;

//Mason Elliott Connor Jones
public class REcompile {

    private static String newRegexp;
    private static int index = 0;
    private static int state = 1;
    private static char[] restrictedCharacters = { '?', '*', '.', '\\', '|', '(', ')' };

    private static LinkedList<String> characterArray = new LinkedList<String>();
    private static LinkedList<Integer> nextStateOne = new LinkedList<Integer>();
    private static LinkedList<Integer> nextStateTwo = new LinkedList<Integer>();

    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                newRegexp = args[0];

                // intilising the arrays
                characterArray.add("start");
                nextStateOne.add(-1);
                nextStateTwo.add(-1);

                int initialState = findExpression();

                // If an early exit from tree, raise error
                if (index != newRegexp.length()) {
                    error();
                }
                setState(0, "start", initialState, initialState);
                // changeStatePath(0,initialState,initialState);
                setState(state, "end", -1, -1);
                System.out.println("SAFE");

                for (int i = 0; i < characterArray.size(); i++) {
                    System.out.print(i + " ");
                    System.out.print(characterArray.get(i) + " ");
                    System.out.print(nextStateOne.get(i) + " ");
                    System.out.print(nextStateTwo.get(i) + " ");
                    System.out.println();
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("Proper Usage is: java filename \"regularExpression\"");

            System.exit(0);
        }
    }

    private static int findExpression() {
        int r, nextState, laststate, t1, t2;

        r = findTerm();

        if (index == newRegexp.length()) {
            return r;
        }

        if (validVocab(newRegexp.charAt(index)) || newRegexp.charAt(index) == '(' || newRegexp.charAt(index) == '\\'
                || newRegexp.charAt(index) == '.') {

            laststate = r;
            nextState = findExpression();

            if ((characterArray.get(r).compareTo("branch1")) == 0) {
                // connecting first term of branch
                t1 = nextStateOne.get(r);
                t1 = nextStateOne.get(t1);
                setState(t1, null, nextState, nextState);

                // connecting second term of branch
                t2 = nextStateTwo.get(r);
                setState(t2, null, nextState, nextState);

            } else {
                setState(laststate, null, nextState, nextState);
            }

        }

        return r;
    }

    private static int findTerm() {
        int r;
        r = findFactor();

        if (index == newRegexp.length()) {
            return r;
        }

        if (newRegexp.charAt(index) == '*') {
            index++;
            setState(state, "branch", r, state + 1);
            r = state;
            state++;
        } else if (newRegexp.charAt(index) == '?') {
            index++;
        } else if (newRegexp.charAt(index) == '|') {
            int r2, finalStateT1, e;
            finalStateT1 = state;// System.out.println(laststate);
            setState(finalStateT1, "dummie", -1, -1);
            state++;
            e = state;
            setState(e, "branch1", -1, -1);

            index++;// Consuming character

            state++;

            r2 = findTerm();
            System.out.println("dfsssssss " + state);
            setState(e, "branch1", r, r2);
            setState(finalStateT1, "dummie", state, state);
            System.out.println(r + " " + r2);
            r = e;
        }
        return r;
    }

    private static int findFactor() {
        int r = state;

        if (validVocab(newRegexp.charAt(index))) {
            // System.out.println(newRegexp.charAt(index));
            setState(state, String.valueOf(newRegexp.charAt(index)), state + 1, state + 1);
            r = state;
            index++;
            state++;
        } else if (newRegexp.charAt(index) == '\\') {

            index++;
            setState(state, String.valueOf(newRegexp.charAt(index)), state + 1, state + 1);
            index++;
            r = state;
            state++;
        } else if (newRegexp.charAt(index) == '(') {
            index++;
            r = findExpression();

            if ((index < newRegexp.length()) && newRegexp.charAt(index) == ')') {
                index++;
            } else {
                error();
            }
        } else if (newRegexp.charAt(index) == '.') {
            index++;
            setState(state, "wildcard", state + 1, state + 1);
            r = state;
            state++;
        } else {
            error();
        }

        return r;
    }

    // Checks to see if something is valid vocab
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
        System.out.println("Error: Not valid Expression");
        System.exit(1);
    }

    // Setting State for the FSM
    private static void setState(int s, String c, int n1, int n2) {

        /*
         * characterArray.add(c); nextStateOne.add(n1); nextStateTwo.add(n2);
         */

        System.out.println(s + " " + c + " " + n1 + " " + n2 + " ");
        if (s == characterArray.size()) {
            characterArray.add(c);
            nextStateOne.add(n1);
            nextStateTwo.add(n2);
        }

        else if (s < characterArray.size()) {
            // characterArray.add(s,c);
            nextStateOne.set(s, n1);
            nextStateTwo.set(s, n2);
        }

    }

    private static void changeStatePath(int s, int newState1, int newState2) {
        nextStateOne.set(s, newState1);
        nextStateTwo.set(s, newState1);
    }
}