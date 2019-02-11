/* Exemple du sujet - version 0 */

%%

%unicode
LETTRE=[a-zA-Z]
CHIFFRE=[0-9]
OP=[-+*/]
IDENT=[a-zA-Z0-9]+
ENTIER=[0-9]+
LETTRE2=[àéùçèÇÀÁîôûâêë]
COMMENTAIRE=(\#.*)|(\{.*\}" "?)|("<!-".*"->"" "?)

%%


{ENTIER}
  { return new Yytoken(TokenType.ENTIER,yytext()); }

({LETTRE}|{LETTRE2})((\_?{IDENT})|{LETTRE2})*
  { return new Yytoken(TokenType.IDENT,yytext()); }

{OP}
  { return new Yytoken(TokenType.OPERATEUR,yytext()); }

[\s\n]|{COMMENTAIRE}
  {}

