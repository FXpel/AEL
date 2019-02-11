/*
Exemple d'utilisation des états

On traite 2 types de tokens : entiers (simples) et chaîne

Une chaîne est délimitée par des guillements "
Elle est écrite sur une seule ligne
À l'intérieur de la chaîne, le caractère \ est caractère d'échappement
  \" désigne le caractère "
  \\ désigne le caractère \
une chaîne ne peut contenir de \ seul.

La classe chaîne s'instancie avec 2 valeurs : le source de la chaîne et la valeur de son contenu, après intreprétation des séquences d'échappement

On utilise un état INNER_STRING pour changer le comportement de l'analyseur pendant la lecture d'une chaîne.

Donc 2 états :
 - YYINITIAL : état normal, on cherche les tokens en ignorant espaces et fins de ligne
 - INNER_STRING : on lit l'intérieur d'une chaîne, en construisant au fur et à mesure le contenu de cette chaine dans une variable stringContent
   le token Chaine est produit quand on rencontre la fin de la chaîne.


*/

%%


INT_SIMPLE = [1-9][0-9]*

// déclaration d'état :
%state INNER_STRING  


// déclaration de 2 attributs privés dans la classe Yylex :
%{
  private StringBuilder stringContent; 
  private StringBuilder stringSource; 
%}


%%


<YYINITIAL>{  // état "normal" 
  
  {INT_SIMPLE}
    { return new Entier(yytext()); }
    
  \"      /* début de chaîne  */ 
    {
      yybegin(INNER_STRING);
      stringContent = new StringBuilder();
      stringSource = new StringBuilder(yytext());
    }
    
  [\s\n]
    {}
}

<INNER_STRING>{ // intérieur de la chaîne
  \\\\        /* une séquence d'échappement : \\ */
    { stringContent.append('\\');
      stringSource.append(yytext());
    }
  \\\"       /* une autre séquence d'échappement : \" */
    { stringContent.append('"'); 
      stringSource.append(yytext());
    }
 [^\"\\\n]   /* caractère ordinaire de la chaîne */
    { stringContent.append(yytext());
      stringSource.append(yytext());
    }
 \"          /* fin de chaîne */
    { yybegin(YYINITIAL);
      stringSource.append(yytext());
      return new Chaine(stringSource.toString(),stringContent.toString());
    }
}

/*  règles inconditionnelles */
  

[^]
  { throw new RuntimeException("inconnu : " + yytext()); }
