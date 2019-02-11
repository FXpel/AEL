package automata;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

/**
 * 
 * Automate destiné à la recherche d'un ensemble de mots dans un texte.
 * Construit selon l'algorithme de AhoCorasick.
 * 
 * @author Bruno.Bogaert (at) univ-lille1.fr
 *
 */
public class AhoCorasick extends NDAutomaton implements DeterministicAutomaton{

	private static String ROOT_NAME = "";
	
	private State root;        				// initial state
	private Map<State, State> repli;		// "fails" epsilon-transitions
	private Map<State, Set<String>> foundWords;		// found words on final states
	


	/**
	 * Construit l'automate à partir d'un ensemble non vide de mots non vides
	 * 
	 * @param words
	 *            mots à rechercher
	 */
	public AhoCorasick(String... words) {
		this(Arrays.asList(words));
	}

	/**
	 * Construit l'automate à partir d'un ensemble non vide de mots non vides
	 * 
	 * @param words
	 *            mots à rechercher
	 */
	public AhoCorasick(Collection<String> words) {
		// check words conformity
	// ==> COMPLETER
		// initialize structure
		repli = new HashMap<State, State>();
		foundWords = new HashMap<State, Set<String>>();
		
		// construct automaton
		root = this.addNewState(ROOT_NAME);
		this.setInitial(root);		
		skeleton(words);
		addRegressTransitions();
	}
	
	/**
	 * Ensemble des mots reconnus quand l'état q est atteint
	 * @param q : état à tester
	 * @return ensemble des mots reconnus quand l'état q est atteint
	 */
	Set<String> getFoundWords(State q){
		if (isAccepting(q))
			return Collections.unmodifiableSet(foundWords.get(q));
		else 
			return null;
	}
	

	/**
	 * Fabrique le squelette (arbre) de l'automate
	 * @throws FifoEmpty 
	 * @throws StateException 
	 */
	private void skeleton(Collection<String> words) throws StateException, FifoEmpty {
		// ==> COMPLETER
		Fifo file = null;
		Iterator<String> iterator = words.iterator();
		AutomatonBuilder a = new NDAutomatonIncomplete();
		while (iterator.hasNext()) {
			file.add(iterator.next());
		}
		
		while(!file.isEmpty()) {
			a.addNewState(file.remove());
		}
		
		Set<State> racine;
	}

	/**
	 * Fabrique un nouvel état accessible à partir du noeud parent par la lettre
	 * letter.
	 * Calcule également la relation de repli pour le nouvel état.
	 * 
	 * @param parent
	 *            parent du nœud à créer
	 * @param letter
	 *            label de la transition entre parent et noeud à créer
	 * @return état créé
	 * @throws StateException
	 * 
	 */
	private State addNewState(State parent, char letter) {
		// ==> COMPLETER
	}

	/**
	 * Complète les transitions définies pour l'état q en utilisant la position
	 * de repli.
	 * 
	 * @param q
	 */
	private void addRegressTransitions() {
		// ==> COMPLETER
	}

	@Override
	public Set<State> getTransitionSet(State s, char letter) {
		// Toutes les transitions non explicitement définies renvoient à l'état
		// initial
		Set<State> dest = super.getTransitionSet(s, letter);
		return dest.isEmpty() ? Collections.singleton(root) : dest;
	}
	
	
	
	
// ========== Specific or redefined methods for deterministic automaton  ============
	/**
	 * @see automata.DeterministicAutomaton#getInitialState()
	 */
	public State getInitialState() {
		Set<State> set = getInitialStates();
		if ( ! set.isEmpty()) {	
			return set.iterator().next();
		} else {
			return null;
		}
	}	
	/**
	 * @see automata.DeterministicAutomaton#getTransition(automata.State, char)
	 */
	public State getTransition(State s, char letter) throws StateException {
		Set<State> set = getTransitionSet(s, letter);
		if (! set.isEmpty()) {	
			return set.iterator().next();
		} else {
			return null;
		}
	}
	/**
	 * @see automata.DeterministicAutomaton#getTransition(java.lang.String, char)
	 */
	public State getTransition(String name, char letter) throws StateException {
		return this.getTransitionSet(name, letter).iterator().next();
	}
	/**
	 * @see automata.DeterministicAutomaton#getTransition(java.lang.Integer, char)
	 */
	public State getTransition(Integer id, char letter) throws StateException {
		return this.getTransitionSet(id, letter).iterator().next();
	}
	@Override
	/**
	 * @see automata.AbstractNDAutomaton#setInitial(automata.State)
	 * @throws StateException if the unique initial state is already defined
	 */
	public void setInitial(State s) {
		if (super.getInitialStates().isEmpty())
			super.setInitial(s);
		else
			throw new StateException("initial state already defined");
	}
	/**
	 * @see automata.AbstractNDAutomaton#addTransition(automata.State, java.lang.Character, automata.State)
	 * @throws StateException if transition already defined
	 */
	@Override
	public void addTransition(State from, Character letter, State to) {
		if (super.getTransitionSet(from,letter).isEmpty()) 
			super.addTransition(from,letter, to);
		else
			throw new StateException("transition already defined");
	}



//==========       Graphviz generation methods      ============
	
	
	/*
	 * version avec un placement des nœuds adapté à la structure arborescente
	 * @see automata.AbstractAutomaton#writeGraphvizStates(java.io.Writer, boolean)
	 */
	@Override
	protected Writer writeGraphvizStates(Writer buff, boolean withNames) {
		PrintWriter out = new PrintWriter(buff);
		out.println("  rankdir = LR");
		Iterator<State> it = states.iterator();
		out.print("{rank=same; ");
		out.print(stateToGV(it.next(), withNames)); // root
		int rank = 0;
		while (it.hasNext()) {
			State s = it.next();
			if (s.getName().length() != rank) {
				rank++;
				out.println("}");
				out.print("{rank=same; ");
			}
			out.print(stateToGV(s, withNames));
		}
		out.println("}");
		return buff;
	}

	/*
	 * flèches pointillées pour représenter les replis (autres que ceux vers root)
	 */
	protected Writer writeGraphvizFails(Writer buff) {
		PrintWriter out = new PrintWriter(buff);
		for (Map.Entry<State,State> r : repli.entrySet()) {
			State to = r.getValue(), from= r.getKey();
			if (to != null && to != root)
				out.printf("    %d -> %d [style=dotted]\n",from.getId(), to.getId());
		}
		return buff;
	}
	/*
	 * transitions sans les transitions par défaut
	 * onlySkeleton ne conserve que les transitions du squelette
	 */
	protected Writer writeGraphvizWithoutDefaultTransitions(Writer buff, boolean onlySkeleton) {
		PrintWriter out = new PrintWriter(buff);
		for (State from : getStates()) {
			for (char letter : usedAlphabet()) {
				for (State dest : super.getTransitionSet(from, letter)) { 
					// only super class view of transitions (exclude default transitions)
					if ((!onlySkeleton) || from.equals(root) || dest.getName().length() > from.getName().length()) {
						// is a skeleton transition iff dest name longer than from name
						out.printf("  %d -> %d [label = \"%s\"]\n",from.getId(),dest.getId(),letter);
					}
				}
			}
		}
		return buff;
	}
    
	protected Writer writeGraphvizWithoutDefault(Writer buff, boolean onlySkeleton, boolean withFails, boolean withNames) {
		PrintWriter out = new PrintWriter(buff);
		out.println("digraph Automaton { ");
		writeGraphvizStates(buff, withNames);
		writeGraphvizInitials(buff);
		writeGraphvizWithoutDefaultTransitions(buff, onlySkeleton);
		if (withFails)
			writeGraphvizFails(out);
		out.println("}");
		return buff;
	}
	protected Writer writeGraphvizWithoutDefault(Writer buff, boolean onlySkeleton, boolean withFails) {
		return writeGraphvizWithoutDefault(buff, onlySkeleton, withFails, true);
	}

	/** 
	 * returns graphviz source without default (->root) transitions
	 * @return graphviz source without default transitions
	 */
	public String withoutDefaultToGraphviz() {
		StringWriter graph = new StringWriter();
		writeGraphvizWithoutDefault(graph, false, false);
		return graph.toString();
	}
	
	/**
	 * returns graphviz source of skeleton
	 * @param withNames if false, state names id's are displayed, instead of names
	 * @return graphviz source of skeleton
	 */
	public String skeletonToGraphviz(boolean withNames) {
		StringWriter graph = new StringWriter();
		writeGraphvizWithoutDefault(graph, true, false, withNames);
		return graph.toString();
	}
	/**
	 * returns graphviz source of skeleton, displaying state names
	 * @return graphviz source of skeleton
	 */
	public String skeletonToGraphviz() {
		return skeletonToGraphviz(true);
	}	

	/**
	 * returns graphviz source of skeleton + "fail" epsilon-transitions, displaying state names
	 * @return graphviz source of skeleton + "fail" epsilon-transitions
	 */
	public String failsToGraphviz() {
		StringWriter graph = new StringWriter();
		writeGraphvizWithoutDefault(graph, true, true);
		return graph.toString();
	}
	
	class FifoEmpty extends Exception { }

	class Fifo {
	  final static int SIZE=10 ;
	  private int in, out, nb ;
	  private String [] t ;

	  Fifo () { t = new String[SIZE] ; in = out = nb = 0 ; }

	  /* Increment modulo la taille du tableau t, utilis� partout */
	  private int incr(int i) { return (i+1) % t.length ; }

	  boolean isEmpty() { return nb == 0 ; }

	  String remove() throws FifoEmpty {
	    if (isEmpty()) throw new FifoEmpty () ;
	    String r = t[out] ;
	    out = incr(out) ; nb-- ; // Effectivement enlever
	    return r ;
	  }

	  void add(String x) {
	    if (nb+1 >= t.length) resize() ;
	    t[in] = x ;
	    in = incr(in) ; nb++ ; // Effectivement ajouter
	  }

	  private void resize() {
	    String [] newT = new String[2*t.length] ; // Allouer
	    int i = out ; // indice du parcours de t
	    for (int k = 0 ; k < nb ; k++) {    // Copier
	      newT[k] = t[i] ;
	      i = incr(i) ;
	    }
	    t = newT ; out = 0 ; in = nb ;     // Remplacer
	  }

	/* M�thode toString, donne un exemple de parcours de la file */
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