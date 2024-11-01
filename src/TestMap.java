import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;

import donnees.*;
import donnees.carte.*;
import io.*;
import donnees.robots.*;


public class TestMap{
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {

            DonneesSimulation data = LecteurDonnees.creeDonnees(args[0]);
            GUISimulator gui = new GUISimulator(Map.tailleGui, Map.tailleGui, Color.WHITE);
            Map map = new Map(gui, data);

        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}


class Map implements Simulable {

    /** L'interface graphique associée */
    private GUISimulator gui;

    private DonneesSimulation data;

    private Couleur[] tabCouleurs = {Couleur.Bleu, Couleur.Vertf, Couleur.Gris, Couleur.Vertc, Couleur.Jaune};
    public static int tailleGui = 850;
    private int tailleCase;

    public Map(GUISimulator gui, DonneesSimulation data) {
        this.gui = gui;
        gui.setSimulable(this);				// association au gui!
        this.data = data;
        this.tailleCase = tailleGui/data.carte.getNbLignes();

        planCoordinates();
        draw();
    }

    private void planCoordinates() {
        int xMin = 60;
        int yMin = 40;
        int xMax = gui.getWidth() - xMin - 80;
        xMax -= xMax % 10;
        int yMax = gui.getHeight() - yMin - 120;
        yMax -= yMax % 10;
    }

    @Override
    public void next() {	
        draw();
    }

    @Override
    public void restart() {
        planCoordinates();
        draw();
    }

    private void draw_map() {
        for (int ligne=0; ligne < data.carte.getNbLignes(); ++ligne ){
            for (int colonne=0; colonne < data.carte.getNbColonnes(); ++colonne){

                int xCase = colonne*tailleCase;
                int yCase = ligne*tailleCase;
                Color couleurCase = tabCouleurs[data.carte.getCase(ligne,colonne).getNature().ordinal()].value;

                gui.addGraphicalElement(new Rectangle(xCase ,yCase, couleurCase, couleurCase, tailleCase));
            }
        }
    }


    private void draw_incendies() {

        for(int i = 0; i < data.incendies.length; i++) {
            int xCase = data.incendies[i].getPosition().getColonne()*tailleCase;
            int yCase = data.incendies[i].getPosition().getLigne()*tailleCase;
            Color couleurCase = Couleur.Rouge.value;

            gui.addGraphicalElement(new Rectangle(xCase ,yCase, couleurCase, couleurCase, tailleCase));
        }
    }


    private void draw_robots() {

        for(int i = 0; i < data.robots.length; i++) {
            int xCase = data.robots[i].getPosition().getColonne()*tailleCase;
            int yCase = data.robots[i].getPosition().getLigne()*tailleCase;
            Color couleurCase = Couleur.Noir.value;

            gui.addGraphicalElement(new Rectangle(xCase ,yCase, couleurCase, couleurCase, tailleCase));
        }
    }


    private void draw() {
        
        gui.reset();

        draw_map();
        draw_incendies();
        draw_robots();
    }
}
