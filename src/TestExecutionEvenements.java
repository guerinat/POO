import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import donnees.*;
import donnees.carte.*;
import donnees.robots.*;
import io.*;
import evenements.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

import gui.GUISimulator;
import gui.Simulable;



public class TestExecutionEvenements{
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {

            //Lecture et creation des données
            DonneesSimulation data = LecteurDonnees.creeDonnees(args[0]);

            

            //Creation du simulateur
            ExecutionEvenements simulation = new ExecutionEvenements(args[0], data);


        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }


}


class ExecutionEvenements implements Simulable {

    /* L'interface graphique associée */
    private GUISimulator gui;

    private DonneesSimulation data;

    /* Textures des objets */
    private BufferedImage[] terrainTextures, robotTextures;
    private BufferedImage incendieTexture;

    public static int tailleGui = 850;
    private int tailleCase;

    //Evenements

    private LinkedList<Evenement> evenements;
    private long date_courante;
    private String cheminFichier;


    public ExecutionEvenements(String cheminFichier, DonneesSimulation data) {

        //Chargement des données
        this.cheminFichier = cheminFichier;
        this.data = data;
        this.tailleCase = tailleGui/data.carte.getNbLignes();

        //Association au gui
        this.gui = new GUISimulator(tailleGui, tailleGui, Color.WHITE);
        gui.setSimulable(this);
        planCoordinates();

        //Chargement des textures
        this.terrainTextures = new BufferedImage[NatureTerrain.values().length];
        this.robotTextures = new BufferedImage[Robot.getNbTypeRobots()];
        load_images();

        //Initialisation des evenements
        this.evenements = new LinkedList<Evenement>();
        ajouteEvenements(loadEvenements(data));
        this.date_courante = 0;

        //Affichage
        draw();
    }


    static Evenement[] loadEvenements(DonneesSimulation data) {

        Evenement[] events = new Evenement[86];

        events[0] = new Deplacement(0, data.carte, data.robots[1].getPosition(),Direction.NORD, data.robots[1]);
        for(int i = 0; i < 50; i++) events[1 + i] = new Vidage(450 + i*600, data.robots[1], data.IncendiePos(data.carte.getCase(5, 5)));
        events[51] = new Deplacement(30450, data.carte, data.carte.getCase(5, 5), Direction.OUEST, data.robots[1]);
        events[52] = new Deplacement(30900, data.carte, data.carte.getCase(5, 4), Direction.OUEST, data.robots[1]);
        events[53] = new Remplissage(31350, data.carte, data.robots[1],0);
        events[54] = new Deplacement(31950, data.carte, data.carte.getCase(5, 3), Direction.EST, data.robots[1]);
        events[55] = new Deplacement(32400, data.carte, data.carte.getCase(5, 4), Direction.EST, data.robots[1]);
        for(int i = 0; i < 30; i++) events[56 + i] = new Vidage(32850 + i*600, data.robots[1], data.IncendiePos(data.carte.getCase(5, 5)));

        return events;
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
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + cheminFichier + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + cheminFichier + " invalide: " + e.getMessage());
        }

        //Reload des evenements
        date_courante = 0;
        evenements.clear();
        ajouteEvenements(loadEvenements(data));

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

    // Execute tout les évenement de date courante (compris) à date-courante + 1 (non-compris)
    private void incrementeDate() {

        ListIterator<Evenement> iterateur = evenements.listIterator();
    
        while (iterateur.hasNext()) {
            Evenement current = iterateur.next(); 

            if (current.getdateFin() >= date_courante + 1)  
                break;

            if (current.getdateFin() >= date_courante) {
                current.execute();
                System.out.println("[t="+current.getdateFin()+"] "+current.toString()+"\n");
            }
            
        }
        date_courante ++;
    }


    private void planCoordinates() {
        int xMin = 60;
        int yMin = 40;
        int xMax = gui.getWidth() - xMin - 80;
        xMax -= xMax % 10;
        int yMax = gui.getHeight() - yMin - 120;
        yMax -= yMax % 10;
    }


    //Charge les sprites de la carte et des données
    private void load_images() {
        try {
            //Carte
            terrainTextures[NatureTerrain.EAU.ordinal()] = ImageIO.read(new File("ressources/eau.png"));
            terrainTextures[NatureTerrain.FORET.ordinal()] = ImageIO.read(new File("ressources/foret.png"));
            terrainTextures[NatureTerrain.HABITAT.ordinal()] = ImageIO.read(new File("ressources/habitat.png"));
            terrainTextures[NatureTerrain.ROCHE.ordinal()] = ImageIO.read(new File("ressources/roche.png"));
            terrainTextures[NatureTerrain.TERRAIN_LIBRE.ordinal()] = ImageIO.read(new File("ressources/terrain_libre.png"));
            
            //Robots
            robotTextures[Drone.texture_id] = ImageIO.read(new File("ressources/drone.png"));
            robotTextures[RobotAChenille.texture_id] = ImageIO.read(new File("ressources/chenilles.png"));
            robotTextures[RobotAPattes.texture_id] = ImageIO.read(new File("ressources/pattes.png"));
            robotTextures[RobotARoue.texture_id] = ImageIO.read(new File("ressources/roues.png"));

            //Incendie
            incendieTexture = ImageIO.read(new File("ressources/incendie.png"));

        } catch (IOException e) {
            System.out.println("Error loading images: " + e.getMessage());
            return;
        }
    }


    private void draw_map() {

        for (int ligne=0; ligne < data.carte.getNbLignes(); ++ligne ){
            for (int colonne=0; colonne < data.carte.getNbColonnes(); ++colonne){

                int xCase = colonne*tailleCase;
                int yCase = ligne*tailleCase;
                BufferedImage texture = terrainTextures[data.carte.getCase(ligne, colonne).getNature().ordinal()];

                gui.addGraphicalElement(new ImageElement(xCase, yCase, tailleCase, tailleCase, texture));
            }
        }
    }


    private void draw_incendies() {

        for(int i = 0; i < data.incendies.length; i++) {
            int xCase = data.incendies[i].getPosition().getColonne()*tailleCase;
            int yCase = data.incendies[i].getPosition().getLigne()*tailleCase;

            if (data.incendies[i].getEauNecessaire() != 0) //Si l'incendie n'est pas eteinte
                gui.addGraphicalElement(new ImageElement(xCase, yCase, tailleCase, tailleCase, incendieTexture));
        }
    }


    private void draw_robots() {

        for(int i = 0; i < data.robots.length; i++) {
            int xCase = data.robots[i].getPosition().getColonne()*tailleCase;
            int yCase = data.robots[i].getPosition().getLigne()*tailleCase;
            BufferedImage texture = robotTextures[data.robots[i].getTextureId()];
            
            gui.addGraphicalElement(new ImageElement(xCase, yCase, tailleCase, tailleCase, texture));
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
