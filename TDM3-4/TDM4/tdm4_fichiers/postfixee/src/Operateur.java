/**
 * Operateurs
 * 
 * @author Bruno.Bogaert (at) univ-lille.fr
 */
public interface Operateur extends Yytoken {
  /**
   * arité
   * @return nombre d'arguments attendus par l'opérateur
   */
  public int arite();
  /**
   * effectue l'opération. 
   * @return résultat de l'opération
   * @throws AriteException si values.length != arite()
   */
  public int evaluate(int... values) throws AriteException;
}
