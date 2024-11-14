package tests;

import donnees.*;
import io.*;
import simulateur.Simulateur;
import strategie.*;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class TestStrategieElementaire {
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