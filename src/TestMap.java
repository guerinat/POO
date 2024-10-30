import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;

import donnees.*;
import donnees.carte.*;

public class TestMap{
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
        // crée l'invader, en l'associant à la fenêtre graphique précédente
        Map map = new Map(gui, Color.decode("#f2ff28"));
    }
}


class Map implements Simulable {

    /** L'interface graphique associée */
    private GUISimulator gui;
    
    private Color mapColor;	

    private int x;
    private int y;

    public Map(GUISimulator gui, Color mapColor) {
        this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
        this.mapColor = mapColor;

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

        // let's plan the invader displacement!
        List<Integer> xCoords = new ArrayList<Integer>();
        List<Integer> yCoords = new ArrayList<Integer>();
        // going right
        for (int x = xMin + 10; x <= xMax; x += 10) {
            xCoords.add(x);
            yCoords.add(yMin);
        }
        // going down
        for (int y = yMin + 10; y <= xMin + 150; y += 10) {
            xCoords.add(xMax);
            yCoords.add(y);
        }
        // going left
        for (int x = xMax - 10; x >= xMin; x -= 10) {
            xCoords.add(x);
            yCoords.add(yMin + 170);
        }
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


    private void draw() {
        Couleur[] tabCouleurs = {Couleur.Bleu, Couleur.Vertf, Couleur.Gris, Couleur.Vertc, Couleur.Jaune};
        gui.reset();	// clear the window
        for (int ligne ; ligne < carte.nbLignes ; ++ligne ){
            for (int colonne ; colonne < carte.nbColonnes ; ++colonne){
                gui.addGraphicalElement(new Rectangle(x + carte.getTailleCases()*ligne ,y + carte.getTailleCases()*colonne,Couleur.Blanc.toAwtColor(), tabCouleurs[carte.getCase(ligne,colonne).getNature().ordinal()].toAwtColor(), carte.getTailleCases()));
            }
        }
        

    }
}