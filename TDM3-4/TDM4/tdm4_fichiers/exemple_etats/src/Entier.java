/**
 * Token entier sans signe
 * 
 * @author Bruno.Bogaert (at) univ-lille.fr
 */
public class Entier extends AbstractToken {
  private final int value;
  
  /**
   * valeur
   * @return  valeur numérique de l'entier
   */
  public int getValue(){
    return value;
  }
  
 /**
   * création d'un token Entier sans signe
   * @param source : texte source du token
   */
  public Entier(String source) throws NumberFormatException {
    super(source);
    this.value = Integer.decode(source);
  }
}
