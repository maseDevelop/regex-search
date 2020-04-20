import java.util.*;

//Mason Elliott Connor Jones
public class REcompile {

    private static String newRegexp;
    private static int index = 0;
    private static int state = 1;
    private static char[] restrictedCharacters = {'?','*','.', '\\', '|', '(', ')'};

    private static LinkedList<String> characterArray = new LinkedList<String>();
    private static LinkedList<Integer> nextStateOne = new LinkedList<Integer>();
    private static LinkedList<Integer> nextStateTwo = new LinkedList<Integer>();


    public static void main(String[] args) {
        if(args.length == 1)
        {
           try{
            newRegexp = args[0];

            //Setting State array so it can be used
            characterArray.add(" ");
            nextStateOne.add(-1);
            nextStateTwo.add(-1);
            //Setting state that can be changed with no exception being thrown
            characterArray.add(" ");
            nextStateOne.add(-1);
            nextStateTwo.add(-1);

            int startState = findExpression();
            //setState(0, "Start", startState, startState);
            System.out.println("SAFE");

           }
           catch(Exception e){
               System.out.println(e);
           }
        }
        else{
            System.out.println("Proper Usage is: java filename \"regularExpression\"");
            
            System.exit(0);
        }
    }

    private static int findExpression(){
        int startState;

        if(index >= newRegexp.length()){
            error();
        }

        startState = findTerm();

            if(index < newRegexp.length()){
                if(validVocab(newRegexp.charAt(index))||newRegexp.charAt(index)=='('){
                    findExpression();
               }
            }
            else{
                error();
            }

        return startState;
            
    }

    private static int findTerm(){
        int r;
        r = findFactor();
  
        if(index == newRegexp.length()){
            System.out.println("errorOne " + index);
            //error();
            return r;
        }

        if(newRegexp.charAt(index)=='*')
        {
            //setState(state, "Closure", r, state++); //Creating a branching state
            r = state;
            state++;
            index++;
        }
        else if(newRegexp.charAt(index)=='?')
        {
            //setState(state, "Question", r, state++); //factor is there or not 
            r = state;
            state++;
            index++;

        }
        else if(newRegexp.charAt(index)=='|')
        {
            index++;
            int f1 = state;
            int e = state++;
            int r2 = findTerm();
            //setState(e, "disjunction", r, r2);
            //setState(f1,"disjunction",state,state);
            r = e;
        }
        return r;
    }

    private static int findFactor(){

        int r = state;

        if(validVocab(newRegexp.charAt(index))){
            //setState(state, String.valueOf(newRegexp.charAt(index)), state+1, state+1);'
            System.out.println("in vocav " + index);
            r = state;
            index++;
            state++;
        }
        else if(newRegexp.charAt(index)=='\\'){
            index++;
            //setState(state, String.valueOf(newRegexp.charAt(index)), state+1, state+1);
            index++;  
            r = state;
            state++;
        }
        else if(newRegexp.charAt(index)=='('){
            System.out.println("brackets ( "+ index);
            index++;
            r = findExpression();
            System.out.println("out of brakcets ");
            if((index < newRegexp.length()) && newRegexp.charAt(index)==')'){
                index++;
                System.out.println("in ) statment" + index);
            }
            else{
                error();  
            }   
        }
        else if(newRegexp.charAt(index)=='.'){
                //setState(state, String.valueOf(newRegexp.charAt(index)), state+1, state+1);
                index++;
                r = state;
                state++;
        }
        
        return r; 
    }

    //Checks to see if something is valid vocab
    private static boolean validVocab(char c){
        for(int i = 0; i < restrictedCharacters.length; i++){
            if(c == restrictedCharacters[i]){
                return false;
            }
        }
        return true;
    }

    //Error exception
    private static void error(){
        System.out.println("Error: Not valid Expression");
        System.exit(1);
    }

    //Setting State for the FSM
    private static void setState(int s, String c, int n1, int n2){

        
        
        characterArray.add(s,c);
        nextStateOne.add(s, n1);
        nextStateTwo.add(s,n2);

    }
}