import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
/**
 * Programme de test du découpage en token
 */
public class Decoupe {
  public static void main(String[] args) throws java.io.FileNotFoundException, java.io.IOException {
  Yylex tokenizer;
      // instanciation de l'analyseur lexical (tokenizer) :
        //tokenizer = new Yylex(new BufferedReader(new FileReader("data.txt")));
  if (args.length>0)
    tokenizer = new Yylex(new BufferedReader(new FileReader(args[0])));          // lecture dans le fichier donné en argument
  else
    tokenizer = new Yylex(new BufferedReader(new InputStreamReader(System.in))); // lecture de l'entrée standard
    
   // lecture du premier token :
  Yytoken token  = tokenizer.yylex();
   // traitement et lecture des suivants :
  while ( token != null ) {
    String show = " [" + token.getSource() +"]";
    if (token instanceof Chaine)
      show += " ("+ ((Chaine) token).getContent()+")";
    System.out.println( "token : " + token.getClass().getName() + show );
    token =  tokenizer.yylex();  // lecture du  suivant
  }
}
}
