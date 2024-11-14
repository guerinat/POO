package evenements;

import donnees.carte.*;
import donnees.robots.*;

public class Remplissage extends Evenement{

    private Carte carte;
    private Robot robot;

    /**
     * Constructeur pour initialiser un événement de remplissage.
     * 
     * @param date_debut La date à laquelle le remplissage commence.
     * @param carte La carte sur laquelle l'événement de remplissage se produit.
     * @param robot Le robot qui se remplit.
     * @param quantEau La quantité d'eau avec laquelle le robot doit se remplir.
     */
    public Remplissage(long date_debut, Carte carte, Robot robot, int quantEau){
        
        super(date_debut + calcDuree(robot, quantEau));

        this.carte = carte;
        this.robot = robot;
    }

    /**
     * Calcule la durée nécessaire pour remplir le réservoir d'un robot avec une quantité d'eau donnée.
     * 
     * @param robot Le robot qui se remplit d'eau.
     * @param quantEau La quantité d'eau avec laquelle le robot doit se remplir.
     * 
     * @return La durée nécessaire pour le remplissage en millisecondes.
     */
    public static long calcDuree(Robot robot, int quantEauRobot) {
        
        long duree = (long)(robot.getDureeRemplissage()*(1 - (double)quantEauRobot/robot.getQuantReservoire()));
        return duree;
    }

    /**
     * Vérifie si une case voisine de la position actuelle du robot contient de l'eau.
     * 
     * @param carte La carte sur laquelle le robot se trouve.
     * @param position La position actuelle du robot.
     * 
     * @return true si une case voisine contient de l'eau, sinon false.
     */
    private boolean eauVoisin(Carte carte, Case position) {

        boolean eau_a_proximite = false;

        for(Direction dir : Direction.values()) {
            eau_a_proximite = eau_a_proximite || (carte.getVoisin(position, dir).getNature() == NatureTerrain.EAU);
        }

        return eau_a_proximite;
    }

    /**
     * Exécute l'événement de remplissage du robot. Selon les conditions, le robot se remplit soit directement
     * sur une case d'eau, soit à côté d'une case d'eau. Si ces conditions ne sont pas remplies, une erreur est lancée.
     * 
     * @throws Error Si le robot n'est pas à côté ou sur de l'eau.
     */
    @Override
    public void execute(){

        //Robot qui se remplissent sur de l'eau
        if (robot.getRemplitSurEau() && robot.getPosition().getNature() == NatureTerrain.EAU) {
            robot.remplirReservoire();
            return;
        } 

        //Robot qui se remplissent à coté de l"eau
        if (!robot.getRemplitSurEau() && eauVoisin(carte, robot.getPosition())) {
            robot.remplirReservoire();
            return; 
        }

        throw new Error("[!] Le robot n'est pas à coté (ou sur) de l'eau.");
    }

    @Override
    public String toString() {
        return "Remplissage (robot:"+robot.toString()+")";
    }
}