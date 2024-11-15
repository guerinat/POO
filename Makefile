# Carte par défault
DEFAULT_MAP=cartes/carteSujet.map

testLecture:
	javac -d bin -sourcepath src src/tests/TestLecteurDonnees.java
	java -classpath bin tests/TestLecteurDonnees cartes/carteSujet.map
	
testExecutionEvenements:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestExecutionEvenements.java
	java -classpath bin:lib/gui.jar tests/TestExecutionEvenements cartes/carteSujet.map

#Compilation et exécution
compile:
	javac -d bin -classpath lib/gui.jar -sourcepath src $(SRC)

run:
	@if [ -z "$(carte)" ]; then \
		echo "[?] Aucun fichier spécifié : utilisation du fichier par défaut : cartes/carteSujet.map: "; \
		java -classpath bin:lib/gui.jar $(TEST) $(DEFAULT_MAP); \
	else \
		java -classpath bin:lib/gui.jar $(TEST) $(carte); \
	fi


testStrategieElementaire: SRC=src/tests/TestStrategieElementaire.java
testStrategieElementaire: TEST=tests/TestStrategieElementaire
testStrategieElementaire: compile run


testStrategieEvoluee: SRC=src/tests/TestStrategieEvoluee.java
testStrategieEvoluee: TEST=tests/TestStrategieEvoluee
testStrategieEvoluee: compile run


clean:
	rm -rf bin/*
