import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import donnees.*;
import donnees.carte.*;
import io.*;
import evenements.*;

import java.awt.Color;

import java.util.LinkedList;
import java.util.ListIterator;

import gui.GUISimulator;
import gui.Simulable;
import gui.ImageElement;

import chemins.*;


public class TestChefPompier1{
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Syntaxe: java TestChefPompier <nomDeFichier>");
            System.exit(1);
        }

        try {

            //Lecture et creation des données
            DonneesSimulation data = LecteurDonnees.creeDonnees(args[0]);

            //Creation du simulateur
            ChefPompier simulation = new ChefPompier(args[0], data);


        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }


}
