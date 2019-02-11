/**
 * Token soustraction (arité 2)
 * 
 * @author Bruno.Bogaert (at) univ-lille.fr
 */
public class Moins extends AbstractOperator{
 
  /*
   * soustraction de 2 valeurs 
   */
   protected int calcul(int... values){
    return values[0]-values[1];
  }
  
  /**
   * création d'un token soustraction
   * @param source : texte source du token
   */
  public Moins(String source){
    super(source,2);
  }
}
