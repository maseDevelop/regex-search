

//Mason Elliott Connor Jones
public class REcompile {

    private static String newRegexp;
    private static int index = 0; 
    public static void main(String[] args) {
        if(args.length == 1)
        {
           try{
            newRegexp = args[0];
            





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

    private static boolean findExpression(String exp){ 
        return true;
    }

    private String findTerm(String term){
        return null;
    }

    private String findFactor(String factor){
        return null;
    }
}