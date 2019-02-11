package automata;

import java.util.Iterator;
import java.util.Set;

/**
 * 
 * Implémentation d'un automate non déterministe.
 * Version incomplète.
 * 
 * @author KArti/Pelage (at) univ-lille1.fr
 *
 */
public class NDAutomaton extends AbstractNDAutomaton implements Recognizer, AutomatonBuilder {
	/**
	 * Fake implementation : always return false.
	 */
	public boolean accept(String word) {
		int i;
		Set<State> nextStates = this.getInitialStates();
		for (i = 0 ; i < word.length() ; i++)  
		{
			if (nextStates.isEmpty()) 
			{
				System.out.println(word.toString() + " : DENIED");
				return false;
			}
		nextStates = this.getTransitionSet(nextStates,word.charAt(i));  
		}

		Iterator <State> itNextStates = nextStates.iterator();  
		{
			while (itNextStates.hasNext())
			{
				if (this.isAccepting(itNextStates.next()))
				{
					System.out.println(word.toString() + " : ACCEPTED");
					return true;
				}
			}
		}
		System.out.println(word.toString() + " : DENIED");
		return false;

		
	}

	public Set<State> getTransitionSet(Set<State> fromSet, char letter) {
		Set <State> resultat = new PrintSet<State>();
		Iterator <State> itFromSet = fromSet.iterator();
		while (itFromSet.hasNext())
		{
			Set <State> getTransitionSetOfEachState  = this.getTransitionSet(itFromSet.next() , letter); 
			Iterator <State> itGetTransitionSetOfEachState = getTransitionSetOfEachState.iterator();

		while (itGetTransitionSetOfEachState.hasNext())
		{
			resultat.add(itGetTransitionSetOfEachState.next()); 
		}

	  }

		return resultat;
	}

}
