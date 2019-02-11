/**
 * Token entier sans signe
 * 
 * @author Bruno.Bogaert (at) univ-lille.fr
 */
public class Chaine extends AbstractToken {
  private final String content;
  public String getContent(){
    return content;
  }
  
 /**
   * création d'un token Chaine 
   * @param source : texte source du token
   * @param content : contenu de la chaîne (après interprétation des séquences d'échappement)
   */
  public Chaine(String source, String content) {
    super(source);
    this.content = content;
  }
}
