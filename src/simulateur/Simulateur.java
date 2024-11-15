package simulateur;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import donnees.*;
import io.*;
import strategie.ChefPompier;
import evenements.*;

import java.awt.Color;

import java.util.LinkedList;
import java.util.ListIterator;

import gui.GUISimulator;
import gui.Simulable;
import gui.ImageElement;


public class Simulateur implements Simulable{
    

    //Lecture et affichage
    private GUISimulator gui;
    private String cheminFichier;
    public static int tailleGui = 850;
    private int tailleCase;

    //Données
    private DonneesSimulation data;

    //Evenements et strategie
    private LinkedList<Evenement> evenements;
    private long date_courante;
    private InitialisateurEvenements initialisateur;
    ChefPompier chefPompier;

    
    /**
     * Constructeur de la classe Simulateur.
     * 
     * @param cheminFichier Chemin vers la carte de la simulation.
     * @param data Données de simulation.
     * @param chefPompier Le chef pompier responsable de la stratégie.
     * @param initialisateur Initialisateur des événements initiaux.
     */
    public Simulateur(String cheminFichier, DonneesSimulation data, ChefPompier chefPompier, InitialisateurEvenements initialisateur) {
        
        //Chargement des données
        this.cheminFichier = cheminFichier;
        this.data = data;
        this.tailleCase = tailleGui/data.carte.getNbLignes();

        //Association au gui
        this.gui = new GUISimulator(tailleGui, tailleGui, Color.WHITE);
        gui.setSimulable(this);

        //Initialisation des evenements
        this.evenements = new LinkedList<Evenement>();
        this.date_courante = 0;

        //Si des evenements initiaux sont spécifiés on les initialise.
        this.initialisateur = initialisateur;
        if (initialisateur != null) 
            ajouteEvenements(initialisateur.initialiserEvenements(data));
        
        //Affichage
        planCoordinates();
        draw();

        //Si un chef pompier decidant une stratégie est specifié on l'initialise.
        this.chefPompier = chefPompier;
        if (chefPompier != null) chefPompier.setSimulateur(this);
    }


    /**
     * Appelé à chaque étape de la simulation
     */
    @Override
    public void next() {

        //Si un chef pompier existe et que les incendies ne sont pas toutes eteintes, jouer la stratégie
        if (!simulationTerminee() && chefPompier != null)
            chefPompier.jouerStrategie(data, date_courante);

        incrementeDate();
        draw();
    }


    /**
     * Appelé quand la simulation est réinitialisée
     */
    @Override
    public void restart() {

        //Relecture des données
        try {
            data = LecteurDonnees.creeDonnees(cheminFichier);
            evenements.clear();
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + cheminFichier + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + cheminFichier + " invalide: " + e.getMessage());
        }

        //Re-initialisation des evenements
        date_courante = 0;
        evenements.clear();
        if (initialisateur != null) 
            ajouteEvenements(initialisateur.initialiserEvenements(data));

        planCoordinates();
        draw();
    }


    /**
     * Ajoute un événement à la liste des événements en le maintenant trié par date de fin.
     * @param e L'événement à ajouter
     */
    public void ajouteEvenement(Evenement e) {

        ListIterator<Evenement> iterateur = evenements.listIterator();

        //Insertion trié par date de fin
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


    /**
     * Ajoute plusieurs événements à la liste des événements.
     * @param events Liste d'événements à ajouter
     */
    public void ajouteEvenements(LinkedList<Evenement> evenements) {

        if (evenements == null) return;

        for(Evenement e : evenements)
            ajouteEvenement(e);
    }


    /**
     * Execute tout les évenement de date courante en incrementant la date
     */
    private void incrementeDate() {

        ListIterator<Evenement> iterateur = evenements.listIterator();
    
        while (iterateur.hasNext()) {
            Evenement current = iterateur.next(); 

            if (current.getdateFin() >= date_courante + 1)  
                break;

            if (current.getdateFin() >= date_courante) {
                System.out.println("[t="+current.getdateFin()+"] "+current.toString());
                current.execute();
            }
            
        }
        date_courante ++;
    }

    
    /**
     * @return true si toute les incendies sont eteintes 
     **/
    private boolean simulationTerminee() {

        boolean incendiesToutesEteintes = true;

        for(Incendie incendie : data.incendies) 
            incendiesToutesEteintes = incendiesToutesEteintes && (incendie.getEauNecessaire() == 0);

        return incendiesToutesEteintes;
    }


    /**
     * Determine les coordonées du gui
     **/
    private void planCoordinates() {
        int xMin = 60;
        int yMin = 40;
        int xMax = gui.getWidth() - xMin - 80;
        xMax -= xMax % 10;
        int yMax = gui.getHeight() - yMin - 120;
        yMax -= yMax % 10;
    }


    /**
     * Affiche la carte
     **/
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


    /**
     * Affiche les incendies
     **/
    private void draw_incendies() {

        for(int i = 0; i < data.incendies.length; i++) {
            int xCase = data.incendies[i].getPosition().getColonne()*tailleCase;
            int yCase = data.incendies[i].getPosition().getLigne()*tailleCase;
            String texture = "ressources/incendie.png";

            if (data.incendies[i].getEauNecessaire() != 0) //Si l'incendie n'est pas eteinte
                gui.addGraphicalElement(new ImageElement(xCase, yCase, texture, tailleCase, tailleCase, null));
        }
    }


    /**
     * Affiche les robots
     **/
    private void draw_robots() {

        for(int i = 0; i < data.robots.length; i++) {
            int xCase = data.robots[i].getPosition().getColonne()*tailleCase;
            int yCase = data.robots[i].getPosition().getLigne()*tailleCase;
            String texture = data.robots[i].getLienTexture();
            
            gui.addGraphicalElement(new ImageElement(xCase, yCase, texture, tailleCase, tailleCase, null));
        }
    }


    /**
     * Affiche loutes les instances de la simulations
     **/
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
    