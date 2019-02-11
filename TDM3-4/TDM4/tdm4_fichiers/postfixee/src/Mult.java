/**
 * Token multiplication (arité 2)
 * 
 * @author Bruno.Bogaert (at) univ-lille.fr
 */
public class Mult extends AbstractOperator{
 
  /*
   * soustraction de 2 valeurs 
   */
   protected int calcul(int... values){
    return values[0]*values[1];
  }
  
  /**
   * création d'un token multiplication
   * @param source : texte source du token
   */
  public Mult(String source){
    super(source,2);
  }
}
