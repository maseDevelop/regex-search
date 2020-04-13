

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
            /*if(newRegexp.charAt(index) != '\0'){
                error();
            }*/

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

            System.out.println("in term");
            return;
        }


        if(newRegexp.charAt(index)=='*')
        {
            System.out.println("in *");
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
        //(index < newRegexp.length()) &&
        //System.out.println(newRegexp.charAt(index));
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
            System.out.println("dot");
                index++;
        }
        System.out.println(index);
        return; 
    }

    //Checks to see if something is valid vocab
    private static boolean validVocab(char c){
        for(int i = 0; i < restrictedCharacters.length; i++){
            if(c == restrictedCharacters[i]){
                System.out.println("false" + c + " " + restrictedCharacters[i]);
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
}