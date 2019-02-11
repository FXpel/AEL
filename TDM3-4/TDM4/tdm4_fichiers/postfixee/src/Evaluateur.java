/**
 * Évaluation d'expression postfixée
 *
 * @author Bruno.Bogaert (at) univ-lille.fr
 */
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
public class Evaluateur {
  public static void main(String args[])
          throws java.io.FileNotFoundException, java.io.IOException, AriteException, java.util.EmptyStackException
  {
    Yylex tokenizer;
    if (args.length>0)
      tokenizer = new Yylex(new BufferedReader(new FileReader(args[0])));          // lecture dans le fichier donné en argument
    else
      tokenizer = new Yylex(new BufferedReader(new InputStreamReader(System.in))); // lecture de l'entrée standard
    
    Stack<Integer> pile = new Stack<Integer>();                    // pile de calcul de l'expression
    Map<String,Integer> memoire = new HashMap<String, Integer>();  // mémoire, pour la gestion des variables
    
    Yytoken token = tokenizer.yylex();
    
    while (token != null ){
      
      if (token instanceof Operande){ // empiler la valeur
          Integer val ;
          if (token instanceof Entier) {
            val = ((Entier) token).getValue();
          } else if (token instanceof Ident) {
            String name = ((Ident) token).getSource();
            val = memoire.get(name);
            if (val == null)
              val = 0;
          } else  throw new Error("Type " + token.getClass().getName() +" inattendu : implémentation inconsistante");
          pile.push(val);
      } else if (token instanceof Operateur){ // effectuer l'opération
          Operateur op = (Operateur) token;
          int[] arguments = new int[op.arite()];
          for (int i=0; i<arguments.length; i++)
                arguments[i] = pile.pop();
          pile.push(op.evaluate(arguments));
      } else if (token instanceof Stockage) { // copier le sommet de pile en mémoire
          String name = ((Stockage) token).getSource();
          memoire.put(name, pile.peek());
      } else  throw new Error("Type " + token.getClass().getName() +" inattendu : implémentation inconsistante");
      
      token = tokenizer.yylex();
    } 
    if (pile.size() != 1){
      System.out.println("** Erreur : il reste " + pile.size() + " élément(s) sur la pile");
      System.out.print("contenu de la pile ");
      System.out.println(pile);
    }
    else
      System.out.println("Résultat : " + pile.pop());
  }

}