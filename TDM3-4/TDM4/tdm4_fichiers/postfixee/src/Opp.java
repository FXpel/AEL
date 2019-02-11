/**
 * Token opposé (arité 1)
 * 
 * @author Bruno.Bogaert (at) univ-lille.fr
 */
public class Opp extends AbstractOperator{
 
  protected int calcul(int... values){
    return -values[0];
  }
  
  /**
   * création d'un token opposé
   * @param source : texte source du token
   */
 public Opp(String source){
    super(source,1);
  }
  
}
