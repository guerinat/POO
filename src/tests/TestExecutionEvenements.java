package tests;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import donnees.*;
import donnees.carte.*;
import io.*;
import evenements.*;

import java.util.LinkedList;
import simulateur.*;



public class TestExecutionEvenements{

    /**
     * Méthode statique initialisant une séquence d'événements pour un test de simulation.
     * Crée une liste d'événements de type Deplacement, Vidage, et Remplissage.
     *
     * @param data Données de simulation contenant les informations sur la carte, les robots, et les incendies.
     * @return LinkedList<Evenement> une liste d'événements ordonnés dans le temps pour simuler des actions d'un robot.
     */
    public static LinkedList<Evenement> initialisateurEvenements1(DonneesSimulation data) {

        LinkedList<Evenement> evenementsDepart = new LinkedList<>();

        evenementsDepart.add(new Deplacement(0, data.carte, data.robots[1].getPosition(),Direction.NORD, data.robots[1]));
        for(int i = 0; i < 50; i++) evenementsDepart.add(new Vidage(450 + i*5, data.robots[1], data.IncendiePos(data.carte.getCase(5, 5))));
        evenementsDepart.add(new Deplacement(700, data.carte, data.carte.getCase(5, 5), Direction.OUEST, data.robots[1]));
        evenementsDepart.add(new Deplacement(1150, data.carte, data.carte.getCase(5, 4), Direction.OUEST, data.robots[1]));
        evenementsDepart.add(new Remplissage(1600, data.carte, data.robots[1],0));
        evenementsDepart.add(new Deplacement(2200, data.carte, data.carte.getCase(5, 3), Direction.EST, data.robots[1]));
        evenementsDepart.add(new Deplacement(2650, data.carte, data.carte.getCase(5, 4), Direction.EST, data.robots[1]));
        for(int i = 0; i < 30; i++) evenementsDepart.add(new Vidage(3100 + i*5, data.robots[1], data.IncendiePos(data.carte.getCase(5, 5))));

        return evenementsDepart;
    }
    
    /**
     * Point d'entrée principal pour exécuter le test des événements.
     * Elle lit le fichier de données de simulation, initialise les données, puis crée un simulateur
     * en utilisant la méthode d'initialisation d'événements.
     *
     * @param args Tableau de chaînes de caractères, où args[0] doit être le nom du fichier de données.
     */
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            //Lecture et creation des données
            DonneesSimulation data = LecteurDonnees.creeDonnees(args[0]);    

            //Creation du simulateur, on passe notre methode d'initialisateur en argument
            new Simulateur(args[0], data, null, TestExecutionEvenements::initialisateurEvenements1);

        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}