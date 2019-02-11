package automata;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
/**
 * 
 * @author Bruno.Bogaert (at) univ-lille.fr
 *
 */
public class AutomataUtils {

	/**
	 * Extends automaton a, so that it accepts also this word. 
	 * Created states are prefixed by 'q_'  
	 * @param word : word to be accepted
	 * @param a : target automaton
	 */
	public static void addSingleton(String word, AutomatonBuilder a) {
		addSingleton(word, a, "q");
	}

	/**
	 * Extends automaton a so that it accepts also this word.
	 * Created states are prefixed by namePrefix followed by '_' 
	 * @param word : word to be accepted
	 * @param a : target automaton
	 * @param namePrefix : prefix to use for state names.
	 */
	public static void addSingleton(String word, AutomatonBuilder a, String namePrefix) {
		int i = 0;
		char letter;
		String nameTransitionNext;
		String ini = namePrefix;
		namePrefix = namePrefix+"_epsilon";
		try
		{
		a.addNewState(namePrefix);
		a.setInitial(namePrefix);
		}
		catch(Exception e)
		{
			System.out.println("Wow Such Exception");
		}


		for (i = 0 ; i < word.length() ; i++) /* On parcourt chaque lettre */
		{
			letter = word.charAt(i);
			nameTransitionNext = ini + "_" + word.substring(0,i+1);
			try
			{
			a.addNewState(nameTransitionNext);
			a.addTransition(namePrefix, letter, nameTransitionNext); /*Construction du singleton */
			}
			catch(Exception e)
			{
				System.out.println("Wow Such Exception");
			}
			namePrefix = nameTransitionNext;
		}
		a.setAccepting(namePrefix);


	}

	/**
	 * Extends automaton a so that it accepts also this finite language
	 * created states are prefixed by namePrefix followed by '_'  
	 * @param finiteLanguage : set of words to be accepted
	 * @param a : target automaton
	 */
	public static void addFiniteSet(Iterable<String> finiteLanguage, AutomatonBuilder a) {
		int j = 0 ;
		for (String word : finiteLanguage ) {
	   	AutomataUtils.addSingleton(word,a,"q"+j);
			j++ ;
}

	}

	/**
	 * Extends automaton a so that it accepts also language denoted by exp
	 * created states are prefixed by namePrefix followed by '_'  
	 * @param exp : flat regular expression (only letters and *)
	 * @param a : target automaton
	 */
	public static void addFlatExp(String exp, AutomatonBuilder a) {
		addFlatExp(exp, a, "q");
	}
	
	/**
	 * Extends automaton a so that it accepts also language denoted by exp
	 * created states are prefixed by namePrefix followed by '_'  
	 * @param exp : flat regular expression (only letters and *)
	 * @param a : target automaton
	 * @param namePrefix : prefix to use for state names.
	 */
	public static void addFlatExp(String exp, AutomatonBuilder a, String namePrefix) {
		String etat=namePrefix+"_epsilon";
		try {
			a.setInitial(a.addNewState(etat));
		}
		catch (StateException e){
			System.out.println("Wow Such Exception");
		}
		String prochainetat ;
		for ( int j= 0;j<exp.length();j++ ) {
			if ((exp.length() - j > 1) && (exp.charAt(j+1)=='*') )  {
							a.addTransition(etat,exp.charAt(j),etat);
							j++ ;
						}

			else {
				prochainetat = namePrefix + "_" + exp.substring(0,j+1);
				a.addNewState(prochainetat);
				a.addTransition(etat,exp.charAt(j),prochainetat);
				etat = prochainetat ;
			}

		}
		a.setAccepting(etat);
	}

	

	/**
	 * Transpose automaton
	 * Note : mirror is cleared before the operation. 
	 * 
	 * @param original : automaton to be transposed
	 * @param mirror : receive the transposed automaton 
	 */
	public static void transpose(Automaton original, AutomatonBuilder mirror) {
		Set<Character> tyler = original.usedAlphabet();
		 Set<State>  actuel = original.getStates();
		 Set<State> transition;
		 mirror.clear();
		 for (State s : actuel){
			 try {
				 mirror.addNewState(s.getName());
			 }
			 catch(Exception e ){}
			if (original.isInitial(s.getName()))
			 	mirror.setAccepting(s.getName());
			if (original.isAccepting(s.getName()))
				mirror.setInitial(s.getName());
		  for (Character a : tyler ) {
				transition = original.getTransitionSet(s,a);
				for ( State newstate : transition ) {
						try {
							mirror.addNewState(newstate.getName());
							mirror.addTransition(newstate.getName(),a,s.getName());
						}
						catch (Exception e ) {}
				}
			}
		 }

	}

	/**
	 * Determinization of nfa automaton. 
	 * Note : dfa is cleared before the operation.
	 * @param nfa : non deterministic automaton (to be determinize)
	 * @param dfa : receive determinization result
	 */
	public static void determinize(Automaton nfa, AutomatonBuilder dfa) {
		// For each computed state set from nfa, a corresponding state has to be created in dfa
		// map represents relationship  between nfa state set (key) and created dfa state (value) 
		Map<Set<State>, State> map = new HashMap<Set<State>, State>();
				
		// stack todo contains state sets whose transitions have not yet been computed
		Stack<Set<State>> todo = new Stack<Set<State>>(); 
		
		dfa.clear();

		Set<State> startSet = nfa.getInitialStates();

		// create matching state in DFA
		State start = dfa.addNewState(startSet.toString()); // state creation
		map.put(startSet, start);  // record relationship in map

		dfa.setAccepting(start); // start is the unique initial state of dfa

		todo.push(startSet); // put it in todo list.

		while (! todo.isEmpty()) {
			Set<State> fromSet = todo.pop(); // pick a state from todo list
			for (Character letter : nfa.usedAlphabet()){
				 startSet = nfa.getTransitionSet(fromSet,letter);
						if (!map.containsKey(startSet)) {
	 					start = dfa.addNewState(startSet.toString());
	 					map.put(startSet,start);
	 					todo.push(startSet);
	 					dfa.addTransition(fromSet.toString(),letter,startSet.toString());
	 				}

				
			 }

			/* TODO :
			 * for each letter of alphabet :
			 * 		compute transitionSet from fromSet
			 * 		if computed set is a new one :
			 * 			create corresponding state in dfa
			 * 			record relationship in map
			 * 			add it to the todo list
			 * 		end if
			 * 		create corresponding transition in dfa
			 */
		}
		for (Set<State> qSet : map.keySet()) {	// foreach computed state set
			/* TODO :
			 * if qset contains accepting state (from nfa)
			 * 	 	in dfa, set corresponding state as accepting state
			 */
			for (State s : qSet){
				 if (nfa.isAccepting(s))
				 			dfa.setAccepting(qSet.toString());
			 }

		}
	}
	/**
	 * Minimalisation of a automaton. 
	 * Note : dfa is cleared before the operation.
	 * @param a : non minimalist automaton (to be determinize)
	 * @param dest : receive minimalisation result
	 */
	public static void minimalise(Automaton a, AutomatonBuilder dest) {
		transpose(a,dest);
		determinize(dest,(AutomatonBuilder) a);
		transpose(a,dest);
		determinize(dest,(AutomatonBuilder) a);
		
	}
	
	class FifoEmpty extends Exception { }

	class Fifo {
	  final static int SIZE=10 ;
	  private int in, out, nb ;
	  private int [] t ;

	  Fifo () { t = new int[SIZE] ; in = out = nb = 0 ; }

	  /* Increment modulo la taille du tableau t, utilisé partout */
	  private int incr(int i) { return (i+1) % t.length ; }

	  boolean isEmpty() { return nb == 0 ; }

	  int remove() throws FifoEmpty {
	    if (isEmpty()) throw new FifoEmpty () ;
	    int r = t[out] ;
	    out = incr(out) ; nb-- ; // Effectivement enlever
	    return r ;
	  }

	  void add(int x) {
	    if (nb+1 >= t.length) resize() ;
	    t[in] = x ;
	    in = incr(in) ; nb++ ; // Effectivement ajouter
	  }

	  private void resize() {
	    int [] newT = new int[2*t.length] ; // Allouer
	    int i = out ; // indice du parcours de t
	    for (int k = 0 ; k < nb ; k++) {    // Copier
	      newT[k] = t[i] ;
	      i = incr(i) ;
	    }
	    t = newT ; out = 0 ; in = nb ;     // Remplacer
	  }

	/* Méthode toString, donne un exemple de parcours de la file */
	  public String toString() {
	    StringBuilder b = new StringBuilder () ;
	    b.append("[") ;
	    if (nb > 0) {
	      int i = out ;
	      b.append(t[i]) ; i = incr(i) ;
	      for ( ; i != in ; i = incr(i))
	        b.append(", " + t[i]) ;
	    }
	    b.append("]") ;
	    return b.toString() ;
	  }
	}
	
		
	

	
}
