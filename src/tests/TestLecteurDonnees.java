package tests;

import io.LecteurDonnees;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import donnees.DonneesSimulation;

public class TestLecteurDonnees {
    
    /**
     * Point d'entrée principal du programme. Cette méthode vérifie si un fichier de données a été
     * spécifié en argument, tente de lire les données à partir de ce fichier, et affiche les données
     * de simulation en cas de succès. En cas d'erreurs, elle gère les exceptions associées.
     *
     * @param args Tableau de chaînes de caractères représentant les arguments de la ligne de commande.
     *             args[0] doit contenir le chemin vers le fichier de données.
     * @throws FileNotFoundException Si le fichier spécifié est introuvable ou illisible.
     * @throws DataFormatException Si le format du fichier de données est invalide.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            DonneesSimulation data = LecteurDonnees.creeDonnees(args[0]);
            System.err.println(data);
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }

}

