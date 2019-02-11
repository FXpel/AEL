/**
 * Token addition
 * 
 * @author Bruno.Bogaert (at) univ-lille.fr
 */
public class Plus extends AbstractOperator{
 
  /*
   * somme de 2 valeurs 
   */
  protected int calcul(int... values){
    return values[0]+values[1];
  }
  /**
   * création d'un token addition
   * @param source : texte source du token
   */
  public Plus(String source){
    super(source,2);
  }
  
}
