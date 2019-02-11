#Makefile

CLASSES =		AbstractNDAutomaton.class \
			AbstractAutomaton.class \
			AutomataUtils.class \
			AutomatonBuilder.class \
			Automaton.class \
			AutomatonStateSet.class \
			NDAutomatonIncomplete.class \
			NDAutomaton.class \
			package-info.class \
			PrintSet.class \
			Recognizer.class \
			StateException.class \
			State.class \
			TestND.class

tout : compil pdf
#Compiling all sources
all: $(CLASSES)


#Compiling source files
%.class: %.java
	javac -sourcepath src -classpath classes src/automata/*.java -d doc/automata

clean:
	rm -rf classes/automata/
	rm -rf *.dot
	rm -rf *.dot.pdf

compil:
	javac -sourcepath src -classpath classes src/automata/*.java -d doc/automata

dot: compil
	java -classpath doc/automata automata.TestND

pdf: dot
	dot -T pdf -O *.dot
