package postfixee;

%%

%unicode
%line
%column

ENTIER_SIMPLE=[0-9]+
PLUS=[+]|plus
MINUS=[-]|minus
MULT=[*]|mult
QUO=[/]|quo
OPP=opp
ESPACE=[:space:]
END_LINE=[^$]

%%

{ENTIER_SIMPLE}
      { return new Valeur(yytext()); }

{PLUS}
      { return new Plus(yytext()); }

/* ajouter le cas des espaces et fins de ligne */

{ESPACE}|{END_LINE}
    {}

/* ajouter les autres tokens */

{MINUS}
    { return new Minus(yytext()); }

{MULT}
    { return new Mult(yytext()); }

{QUO}
    { return new Quo(yytext()); }

{OPP}
    { return new Opp(yytext()); }
