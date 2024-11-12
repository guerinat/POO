package evenements;

import donnees.carte.*;
import donnees.robots.*;

public class Remplissage extends Evenement{

    private Carte carte;
    private Robot robot;

    public Remplissage(long date_debut, Carte carte, Robot robot, int quantEau){
        
        super(date_debut + calcDuree(robot, quantEau));

        this.carte = carte;
        this.robot = robot;
    }

    //Calcule la durée de remplissage d'un robot à une quantité futur (ou non) d'eau donnée
    private static long calcDuree(Robot robot, int quantEau) {
        
        long duree = (long)(robot.getDureeRemplissage()*(1 - (double)quantEau/robot.getQuantReservoire()));
        return duree;
    }

    //True si de l'eau se trouve dans une des direction à partir de position
    private boolean eauVoisin(Carte carte, Case position) {

        boolean eau_a_proximite = false;

        for(Direction dir : Direction.values()) {
            eau_a_proximite = eau_a_proximite || (carte.getVoisin(position, dir).getNature() == NatureTerrain.EAU);
        }

        return eau_a_proximite;
    }

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