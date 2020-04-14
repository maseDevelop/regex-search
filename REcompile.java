import java.util.*;

//Mason Elliott Connor Jones
public class REcompile {

    private static String newRegexp;
    private static int index = 0;
    private static int state = 1;
    private static char[] restrictedCharacters = {'?','*','.', '\\', '|', '(', ')'};

    private static LinkedList<Character> characterArray = new LinkedList<Character>();
    private static LinkedList<Integer> nextStateOne = new LinkedList<Integer>();
    private static LinkedList<Integer> nextStateTwo = new LinkedList<Integer>();


    public static void main(String[] args) {
        if(args.length == 1)
        {
           try{
            newRegexp = args[0];

            findExpression();
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
        int r;

        if(index >= newRegexp.length()){
            error();
        }

        r = findTerm();

            if(index < newRegexp.length()){
                if(validVocab(newRegexp.charAt(index))||newRegexp.charAt(index)=='('){
                    findExpression();
               }
            }

        return(r);
            
    }

    private static int findTerm(){
        int r;
        r = findFactor();
  
        if(index >= newRegexp.length()){
            error();
        }

        if(newRegexp.charAt(index)=='*')
        {
            index++;
        }
        else if(newRegexp.charAt(index)=='?')
        {
            index++;
        }
        else if(newRegexp.charAt(index)=='|')
        {
            index++;
        }
        return(r);
    }

    private static int findFactor(){

        int r;

        if(validVocab(newRegexp.charAt(index))){
            setState(state, newRegexp.charAt(index), state+1, state+1);
            r = state;
            index++;
            state++;
        }
        else if(newRegexp.charAt(index)=='\\'){
            index++;
              //Set state
            index++;  
        }
        else if(newRegexp.charAt(index)=='('){
            index++;
            findExpression();
            
            if((index < newRegexp.length()) && newRegexp.charAt(index)==')'){
                index++;
            }
            else{
                error();  
            }   
        }
        else if(newRegexp.charAt(index)=='.'){
                index++;
        }
        
        return(r); 
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
    private static void setState(int s, char c, int n1, int n2){
        characterArray.add(s,c);
        nextStateOne.add(s, n1);
        nextStateTwo.add(s,n2);
    }
}