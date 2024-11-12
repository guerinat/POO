# Ensimag 2A POO - TP 2018/19
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est ici d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire bin
#     La hierarchie des sources (par package) est conservee.
#     L'archive lib/gui.jar contient les classes de l'interface graphique
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

all: testInvader testLecture testMap

testInvader:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/TestInvader.java
	java -classpath bin:lib/gui.jar TestInvader

testLecture:
	javac -d bin -sourcepath src src/TestLecteurDonnees.java
	java -classpath bin TestLecteurDonnees cartes/carteSujet.map
	
testExecutionEvenements:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/TestExecutionEvenements.java
	java -classpath bin:lib/gui.jar TestExecutionEvenements cartes/carteSujet.map

testChef:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/TestChefPompier1.java
	java -classpath bin:lib/gui.jar TestChefPompier1 cartes/carteSujet.map

clean:
	rm -rf bin/*
