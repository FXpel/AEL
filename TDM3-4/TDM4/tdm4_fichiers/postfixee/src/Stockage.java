/**
 * Token identificateur destinataire d'un stockage
 *
 * représente un identificateur utilisé dans une commande de stockage
 *
 * le texte source est celui de l'identificateur
 * 
 * @author Bruno.Bogaert (at) univ-lille.fr
 */
public class Stockage extends AbstractToken implements Yytoken {
 
  /**
   * crée un token identificateur utilisé dans une commande de stockage
   * @param source : source de l'identificateur.
   */
  public Stockage(String source){
    super(source);
  }
}
