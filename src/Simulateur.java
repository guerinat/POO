import java.awt.Color;

import gui.ImageElement;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.zip.DataFormatException;

import gui.GUISimulator;
import gui.Simulable;

import donnees.*;
import donnees.carte.*;
import io.*;
import donnees.robots.*;
import evenements.*;


public class Simulateur implements Simulable {

    /* L'interface graphique associée */
    private GUISimulator gui;

    private DonneesSimulation data;

    public static int tailleGui = 850;
    private int tailleCase;

    //Evenements
    private LinkedList<Evenement> evenements;
    private long date_courante;
    private String cheminFichier;


    public Simulateur(String cheminFichier, DonneesSimulation data, Evenement[] evenements) {

        //Chargement des données
        this.cheminFichier = cheminFichier;
        this.data = data;
        this.tailleCase = tailleGui/data.carte.getNbLignes();

        //Association au gui
        this.gui = new GUISimulator(tailleGui, tailleGui, Color.WHITE);
        gui.setSimulable(this);
        planCoordinates();

        //Initialisation des evenements
        this.evenements = new LinkedList<Evenement>();
        this.date_courante = 0;
        ajouteEvenements(evenements);

        //Affichage
        draw();
    }


    @Override
    public void next() {
        incrementeDate();
        draw();
    }


    @Override
    public void restart() {
        try {
            data = LecteurDonnees.creeDonnees(cheminFichier);
            evenements.clear();
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + cheminFichier + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + cheminFichier + " invalide: " + e.getMessage());
        }

        date_courante = 0;

        planCoordinates();
        draw();
    }


    public void ajouteEvenement(Evenement e) {

        ListIterator<Evenement> iterateur = evenements.listIterator();

        while (true) {
            if (!iterateur.hasNext()) {
                iterateur.add(e);
                return;
            }

            Evenement current = iterateur.next();
            if(e.getdateFin() < current.getdateFin()) {
                iterateur.previous();
                iterateur.add(e);
                return;
            }
        }
    }


    public void ajouteEvenements(Evenement[] events) {
        for(Evenement e : events)
            ajouteEvenement(e);
    }

    // Execute tout les évenement de date courante
    private void incrementeDate() {

        ListIterator<Evenement> iterateur = evenements.listIterator();
    
        while (iterateur.hasNext()) {

            Evenement current = iterateur.next(); 

            if (current.getdateFin() >= date_courante + 1)  
                break;

            current.execute();
            System.out.println("[t="+current.getdateFin()+"] "+current.toString()+"\n");
            iterateur.remove();   
        }
        date_courante ++;
    }


    private boolean simulationTerminee() {
        return date_courante > evenements.getLast().getdateFin();
    }


    private void planCoordinates() {
        int xMin = 60;
        int yMin = 40;
        int xMax = gui.getWidth() - xMin - 80;
        xMax -= xMax % 10;
        int yMax = gui.getHeight() - yMin - 120;
        yMax -= yMax % 10;
    }



    private void draw_map() {

        for (int ligne=0; ligne < data.carte.getNbLignes(); ++ligne ){
            for (int colonne=0; colonne < data.carte.getNbColonnes(); ++colonne){

                int xCase = colonne*tailleCase;
                int yCase = ligne*tailleCase;
                String texture = data.carte.getCase(ligne, colonne).getNature().getLienTexture();

                gui.addGraphicalElement(new ImageElement(xCase, yCase, texture, tailleCase, tailleCase, null));
            }
        }
    }


    private void draw_incendies() {

        for(int i = 0; i < data.incendies.length; i++) {
            int xCase = data.incendies[i].getPosition().getColonne()*tailleCase;
            int yCase = data.incendies[i].getPosition().getLigne()*tailleCase;
            String texture = "ressources/incendie.png";

            if (data.incendies[i].getEauNecessaire() != 0) //Si l'incendie n'est pas eteinte
                gui.addGraphicalElement(new ImageElement(xCase, yCase, texture, tailleCase, tailleCase, null));
        }
    }


    private void draw_robots() {

        for(int i = 0; i < data.robots.length; i++) {
            int xCase = data.robots[i].getPosition().getColonne()*tailleCase;
            int yCase = data.robots[i].getPosition().getLigne()*tailleCase;
            String texture = data.robots[i].getLienTexture();
            
            gui.addGraphicalElement(new ImageElement(xCase, yCase, texture, tailleCase, tailleCase, null));
        }
    }


    private void draw() {
        
        gui.reset();

        draw_map();
        draw_incendies();
        draw_robots();
    }


    @Override
    public String toString() {
        String s = "";
        for(Evenement e : evenements) {
            s+=e.toString()+"\n";
        }
        return s;
    }
}
