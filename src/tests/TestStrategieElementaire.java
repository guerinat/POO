package tests;

import donnees.*;
import io.*;
import simulateur.Simulateur;
import strategie.*;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class TestStrategieElementaire {

    /**
     * Méthode principale du programme. Elle initialise les données de simulation, 
     * applique une stratégie de gestion élémentaire des incendies avec un chef pompier 
     * et crée un simulateur pour exécuter la simulation.
     *
     * @param args Tableau de chaînes de caractères représentant les arguments de la ligne de commande.
     *             args[0] doit contenir le nom du fichier de données à lire.
     */
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Syntaxe: java TestChefPompier <nomDeFichier>");
            System.exit(1);
        }

        try {

            //Lecture et creation des données
            DonneesSimulation data = LecteurDonnees.creeDonnees(args[0]);

            //Creation de la strategie et du simulateur
            ChefPompier chefPompier = new ChefPompierElementaire();
            new Simulateur(args[0], data, chefPompier, null);
            

        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}