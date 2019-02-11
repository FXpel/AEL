/**
 * AEL 2018
 * @author : bruno.bogaert (at) univ-lille.fr
 * Exemple du sujet JFLEX, version 0
 */
public class Yytoken {
  private TokenType type;
  private String txt;

  public Yytoken(TokenType type, String txt){
    this.type = type ;
    this.txt = txt;
  }
  public String getSource(){
    return txt;
  }

  public TokenType getType() {
    return type;
  }
}
