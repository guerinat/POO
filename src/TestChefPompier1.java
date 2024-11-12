import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import donnees.*;
import donnees.carte.*;
import io.*;
import evenements.*;

import java.awt.Color;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.ListIterator;
import java.lang.Comparable;

import gui.GUISimulator;
import gui.Simulable;
import gui.ImageElement;

import chemins.*;
import donnees.robots.*;
import java.util.PriorityQueue;

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
            ChefPompier1 simulation = new ChefPompier1(args[0], data);


        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}


class ChefPompier1 implements Simulable{
    
    /* L'interface graphique associée */
    private GUISimulator gui;

    private DonneesSimulation data;

    public static int tailleGui = 850;
    private int tailleCase;

    //Evenements

    private LinkedList<Evenement> evenements;
    private long date_courante;
    private String cheminFichier;


    public ChefPompier1(String cheminFichier, DonneesSimulation data) {
        
        //Chargement des données
        this.cheminFichier = cheminFichier;
        this.data = data;
        this.tailleCase = tailleGui/data.carte.getNbLignes();

        //Association au gui
        this.gui = new GUISimulator(tailleGui, tailleGui, Color.WHITE);
        gui.setSimulable(this);

        //Initialisation des evenements
        this.evenements = new LinkedList<Evenement>();
        ajouteEvenements(Finirlejeu(data));
        this.date_courante = 0;

        //Affichage

        planCoordinates();
        draw();
    }


    public Incendie detecteIncendie(DonneesSimulation data){
        for (Incendie incendie : data.incendies){
            if (incendie.getEauNecessaire()>0 && incendie.getRobot()==null){
                return incendie;
            }
        }
        System.out.println("Pas d'incendie détécté");
        return null;
    }


    public Robot robotDispo(DonneesSimulation data, Incendie incendie, long date){
        if (incendie==null) return null;
        for (Robot robot : data.robots){
            if (date >= robot.getDateDispo()){
                incendie.changeRobot(robot);
                robot.changeIncendie(incendie);
                return robot;
            }
        }
        return null;
    }


    public ArrayList<Evenement> EteindreIncendie(DonneesSimulation data, long date_debut, Incendie incendie, Robot robot){
        long date = date_debut;
        
        LinkedList<CaseDuree> chemin = PlusCoursChemin.djikstra(robot, incendie.getPosition(), data.carte);
        PriorityQueue<Deplacement> evenements = PlusCoursChemin.deplacerRobotChemin(date, chemin, robot, data.carte);
        ArrayList<Evenement> events = new ArrayList<>(evenements);
        date += PlusCoursChemin.duree_chemin(chemin);
        
        while (robot.getQuantEau()>0){
            events.add(new Vidage(date, robot, incendie));
            date += Vidage.calcDuree(robot);
        }
        incendie.changeRobot(null);
        robot.changeIncendie(null);
        Case Eau = PlusCoursChemin.PlusProcheEau(data, robot);
        LinkedList<CaseDuree> cheminEau = PlusCoursChemin.djikstra(robot, incendie.getPosition(), data.carte);
        Evenement[] eventsEau = PlusCoursChemin.deplacerRobotChemin(date, chemin, robot, data.carte).toArray(new Evenement[0]);
        for (int i=0; i<eventsEau.length;i++){
            events.add(eventsEau[i]);
            if (i==eventsEau.length-1 && !robot.getRemplitSurEau())
                break;
        }
        date += PlusCoursChemin.duree_chemin(cheminEau);
        events.add(new Remplissage(date, data.carte, robot, robot.getQuantReservoire()));
        date += Remplissage.calcDuree(robot, robot.getQuantReservoire());
        
            
        }
        robot.changeDateDispo(date);
        return events;
    }

    public Evenement[] Finirlejeu(DonneesSimulation data){
        long date = 0;
        ArrayList<Evenement> events = new ArrayList<Evenement>(0);
        while (detecteIncendie(data) != null){
            Incendie incendie = detecteIncendie(data);
            Robot robot = robotDispo(data, incendie, date);
            System.out.println("b");
            if (robot != null){
                System.out.println("dd");
                ArrayList<Evenement> events2 = EteindreIncendie(data, date, incendie, robot);
                System.out.println("aaaaa");
                for (Evenement evenement:events2){
                    events.add(evenement);
                }
                System.out.println("c");
            }
            else{
                date += 1;    
            }
        }
        return events.toArray(new Evenement[0]);
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

        evenements.clear();
        ajouteEvenements(Finirlejeu(data));

        System.out.println(evenements.getFirst());

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
    

