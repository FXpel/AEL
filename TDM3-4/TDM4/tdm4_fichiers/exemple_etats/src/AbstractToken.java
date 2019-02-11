/**
 * implémentation des structures communes aux différents tokens
 * 
 * @author Bruno.Bogaert (at) univ-lille.fr
 */

public abstract class AbstractToken implements Yytoken {
    private final String source;
    
    public String getSource(){
        return source;
    }

    /**
     * @param source : texte source du token
     */
    protected AbstractToken(String source){
        this.source = source;
    }
}
