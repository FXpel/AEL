/**
 * Token division (arité 2)
 * 
 * @author Bruno.Bogaert (at) univ-lille.fr
 */
public class Div extends AbstractOperator {
 
  /*
   * division de 2 valeurs 
   */
    protected int calcul(int... values){
    return values[0]/values[1];
  }
  
  /**
   * création d'un token division
   * @param source : texte source du token
   */
 public Div(String source){
    super(source,2);
  }
}
