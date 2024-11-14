package tests;

import io.LecteurDonnees;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import donnees.DonneesSimulation;

public class TestLecteurDonnees {
    
    /**
     * Méthode principale du programme. Elle vérifie si un fichier a été spécifié en argument,
     * tente de lire les données à partir de ce fichier et affiche les données de simulation 
     * ou gère les erreurs de format et de fichier introuvable.
     *
     * @param args Tableau de chaînes de caractères représentant les arguments de la ligne de commande.
     *             args[0] doit contenir le nom du fichier de données.
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

