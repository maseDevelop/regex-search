

//Mason Elliott Connor Jones
public class REcompile {

    private static String newRegexp;
    private static int index = 0;
    private static int state = 1;
    private static char[] restrictedCharacters = {'?','*','.', '\\', '|', '(', ')'};
    private static int regexLength;

    public static void main(String[] args) {
        if(args.length == 1)
        {
           try{
            newRegexp = args[0];
            regexLength = newRegexp.length();
            System.out.println("Regular Expression:" + newRegexp);

            for(int o=0;o<newRegexp.length();o++){
                System.out.println(newRegexp.charAt(o) + " - index:" + o);
            }
            findExpression();
            System.out.println("SAFE");

           }
           catch(Exception e){
            //error();
               System.out.println(e);
           }
        }
        else{
            System.out.println("Proper Usage is: java filename \"regularExpression\"");
            
            System.exit(0);
        }
    }

    private static void findExpression(){
        if(index >= newRegexp.length()){
            error();
        }
        printString();

        findTerm();

            if(index < newRegexp.length()){
                if(validVocab(newRegexp.charAt(index))||newRegexp.charAt(index)=='('){
                    findExpression();
                }
            }
            
            
            
    }

    private static void findTerm(){
        findFactor();
  
        if(index >= newRegexp.length()){

            return;
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
        return;
    }

    private static void findFactor(){

        if(validVocab(newRegexp.charAt(index))){
            index++;
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
        
        return; 
    }

    //Checks to see if something is valid vocab
    private static boolean validVocab(char c){
        for(int i = 0; i < restrictedCharacters.length; i++){
            if(c == restrictedCharacters[i]){
                //System.out.println("false" + c + " " + restrictedCharacters[i]);
                return false;
            }
        }
        return true;
    }

    //Error exception
    private static void error(){
        System.out.println("Error");
        System.exit(1);
    }

    private static void printString(){
        for (int i = index; i < newRegexp.length(); i++) {
            System.out.print(newRegexp.charAt(i));
        }
        System.out.println();
    }
}