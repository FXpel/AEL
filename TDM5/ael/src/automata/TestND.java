package automata;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestND {

	/*
	 * Écriture de la représentation graphviz de l'automate dans un fichier
	 * 
	 */
	private static void dotToFile(Automaton a, String fileName) {
		File f = new File(fileName);
		try {
			PrintWriter sortieDot = new PrintWriter(f);
			sortieDot.println(a.toGraphviz());
			sortieDot.close();
		} catch (IOException e) {
			System.out.println("création du fichier " + fileName + " impossible");
		}
	}

	/*
	 * Test de la méthode accept()
	 */
	private static void testAccept(Automaton a) {
		if (a.accept(""))
			System.out.println("Le mot vide est accepté. ");
		else
			System.out.println("Le mot vide n'est pas accepté. ");

		Scanner in = new Scanner(System.in);
		System.out.println("Mot non vide à tester ? (mot vide pour terminer)");
		String word = in.nextLine();
		while (word.length() != 0) {
			System.out.print("> " + word);
			if (a.accept(word))
				System.out.print(" est accepté. ");
			else
				System.out.print(" n'est pas accepté.");
			System.out.println("Mot non vide à tester ? (mot vide pour terminer)");
			word = in.nextLine();
		}

	}

	public static void main(String[] args) throws StateException {

		/* Fabrication de l'automate */

		AutomatonBuilder a = new NDAutomatonIncomplete();

		/*
		 * Définition des états Notez que les états sont numérotés 0, 1, 2, ... dans
		 * l'ordre de leur création dans l'automate par défaut les états sont nommés
		 * "qi", où i est leur numéro On peut leur choisir un autre nom en le passant en
		 * paramètre de la méthode addNewState
		 */

		a.addNewState();
		a.addNewState();
		a.addNewState();
		a.addNewState();
		// a.addNewState("NomQuiMePlait");

		/*
		 * Définition des états initiaux et des états acceptants Le paramètre est
		 * indifféremment le numéro ou le nom d'un état
		 */
		a.setAccepting("q0");
		a.setInitial("q0");
		a.setAccepting("q2");
		a.setInitial("q2");

		/*
		 * Définition des transitions
		 */
		a.addTransition("q0", 'a', "q1");
		a.addTransition("q1", 'b', "q0");
		a.addTransition("q2", 'b', "q3");
		a.addTransition("q3", 'a', "q2");

		/*
		 * Dessin de l'automate (fabrication d'un fichier Graphviz)
		 */
		dotToFile(a, "automate-test.dot");

		/*
		 * Affichage de l'automate, en mode texte
		 */
		System.out.println(a);

		/*
		 * Test de la méthode accept() à réactiver quand vous aurez développé une classe
		 * avec une vraie méthode accept()
		 */

		testAccept(a);

		String m0 ="";
		String m1 = "a";
		String m2 = "b";
		String m3 = "c";
 		String mm1 = "aa";
		String mm2 = "bb";
 		String mm3 = "cc";
		String mm4 = "ab";
		String motrip = "aaa";
 		String motrip2 = "aba";
 		String motrip3 = "abc";
 		String moquad = "bbc";
 		String moquad2 = "claptrap";
 		String unmot = "Segfaultamer";

 		System.out.println(" \nTests Simples Basiques\n");
 		a.accept(m0);
 		a.accept(m1);
 		a.accept(m2);
 		a.accept(m3);
	  a.accept(mm1);
 		a.accept(mm2);
 		a.accept(mm3);
		a.accept(mm4);
 		a.accept(motrip);
 		a.accept(motrip2);
 		a.accept(motrip3);
 		a.accept(moquad);
		System.out.println("------------------------------------------------");
		System.out.println("\nTests Pour AutomataUtilis\n");

		AutomatonBuilder b = new NDAutomaton();
		AutomatonBuilder c = new NDAutomaton();
		AutomataUtils.addSingleton("un mot",b);
		dotToFile(b,"autosingle.dot");
		System.out.println("\nLe fichier autosingle a ete cree\n");

		AutomataUtils.addSingleton("throw",c);
		dotToFile(c,"autosingle2.dot");
		System.out.println("\nLe fichier autosingle2 a ete cree\n");
		System.out.println("------------------------------------------------");
    System.out.println("\n Tests pour addFiniteSet \n");
		AutomatonBuilder ab = new NDAutomaton();
		List <String> liste = new ArrayList<String>();
		liste.add("aze");
		liste.add("qsd");
		liste.add("wxc");
		AutomataUtils.addFiniteSet(liste,ab);
		dotToFile(ab,"autoFiniteSet.dot");
		System.out.println("------------------------------------------------");
		// testAccept(a);
		System.out.println("\n Tests pour FlatExp \n");
		AutomatonBuilder abc = new NDAutomaton();
		String expression =  "10*1";
		AutomataUtils.addFlatExp(expression,abc);

		dotToFile(abc,"autoFlatExp.dot");
		System.out.println("------------------------------------------------");
		System.out.println("\n Tests pour transpose \n");
		AutomatonBuilder t = new NDAutomaton();
		AutomataUtils.transpose(ab,t);
		dotToFile(t,"autoTranspose.dot");	
		AutomataUtils.minimalise(ab,t);
		dotToFile(ab,"autoTranspose_minim.dot");
		System.out.println("------------------------------------------------");
		System.out.println("\n Tests pour determinize \n");
		AutomatonBuilder create = new NDAutomaton();
		List <String> liste2 = new ArrayList<String>();
		
		liste2.add("create");
		liste2.add("at");
		liste2.add("cry");
		AutomataUtils.addFiniteSet(liste2,create);
		AutomatonBuilder t2 = new NDAutomaton();
		AutomataUtils.determinize(create,t2);
		dotToFile(t2,"autodeterminize.dot");
		AutomataUtils.minimalise(create,t2);
		dotToFile(create,"autodeterminize_minim.dot");
		System.out.println("That's all folks");

	}
}
