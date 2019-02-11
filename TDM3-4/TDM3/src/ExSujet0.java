/**
 * AEL 2018
 * @author : bruno.bogaert (at) univ-lille.fr
 * Exemple du sujet JFLEX, version 0
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class ExSujet0 {
 /*
  * Usage :
  *
  *   java ExSujet0 <nom_de_fichier> : analyse des données contenues dans le fichier
  *   ou
  *   java ExSujet0  : analyse des données sur l'entrée standard (console)
  */
  public static void main(String[] args) throws java.io.FileNotFoundException, java.io.IOException {
   Yylex tokenizer;
      // instanciation de l'analyseur lexical (tokenizer) :
        //tokenizer = new Yylex(new BufferedReader(new FileReader("data.txt")));
   if (args.length>0)
    tokenizer = new Yylex(new BufferedReader(new FileReader(args[0])));          // lecture dans le fichier donné en argument
   else
    tokenizer = new Yylex(new BufferedReader(new InputStreamReader(System.in))); // lecture de l'entrée standard

    // recherche du premier token :
   Yytoken token  = tokenizer.yylex();
    // traitement et lecture des suivants :
   while ( token != null ) {
    System.out.println( "token : " + token.getType() + " (" + token.getSource() +")" );
    token =  tokenizer.yylex();  // recherche du token suivant
   }
  }
}
