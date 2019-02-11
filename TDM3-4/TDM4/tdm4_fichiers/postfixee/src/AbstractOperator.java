/**
 * implémentation commune aux opérateurs
 *
 * une classe "fille" doit notamment implémenter la méthode calcul
 * 
 * @author Bruno.Bogaert (at) univ-lille.fr
 */
public abstract class AbstractOperator extends AbstractToken implements Operateur {
  public final int arite;
  public int arite(){
    return arite;
  }
  
  /**
   * évaluation de l'opérateur. une classe fille doit implémenter cette méthode NON publique
   * @return résultat de l'opération
   */
  protected abstract int calcul(int... values);
  
  /**
   * effectue l'opération. 
   * @return résultat de l'opération
   * @throws AriteException si values.length != arite()
   */
  public int evaluate(int [] values) throws AriteException{
    if (values.length != arite)
      throw new AriteException();
    return calcul(values);
  }
  /**
   * @param source : source du token
   * @param arité : arité (nombre d'arguments) de l'opérateur
   */
  public AbstractOperator(String source, int arite){
    super(source);
    this.arite = arite;
  }
}
