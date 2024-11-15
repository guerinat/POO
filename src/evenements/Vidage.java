package evenements;

import java.util.ArrayList;
import java.util.LinkedList;

import donnees.Incendie;
import donnees.robots.*;

public class Vidage extends Evenement{

    private Robot robot;
    private Incendie incendie;

    /**
     * Constructeur pour initialiser un événement de vidage.
     * 
     * @param date_debut La date à laquelle le vidage commence.
     * @param robot Le robot qui effectue le vidage.
     * @param incendie L'incendie que le robot tente d'éteindre.
     */
    public Vidage(long date_debut, Robot robot, Incendie incendie){
        
        super(date_debut + calcDuree(robot));
        this.robot = robot;
        this.incendie = incendie;
    }

    /**
     * Calcule la durée nécessaire pour vider le réservoir du robot pour une intervention.
     * 
     * @param robot Le robot qui effectue l'intervention.
     * 
     * @return La durée de l'intervention en secondes.
     */
    public static long calcDuree(Robot robot) {
        long duree = robot.getDureeIntervention();
        return duree;
    }

    /**
     * Crée une liste d'événements de vidage correspondant aux vidages maximum que peut effectuer un robot,
     * ou que l'incendie peut recevoir.
     * 
     * @param robot Le robot qui effectue l'intervention.
     * @param incendie L'incendie que le robot tente d'éteindre.
     * @param date_debut La date à laquelle le premier vidage commence.
     * 
     * @return La liste d'événements de vidage..
     */
    public static LinkedList<Evenement> viderEntierementRobot(Robot robot, Incendie incendie, long date_debut) {

        LinkedList<Evenement> vidages = new LinkedList<>();
        int eau_necessaire = incendie.getEauNecessaire();
        int volume = robot.getQuantEau();

        //Tant que l'incendie n'est pas eteinte et que le robot à encore de l'eau
        while(eau_necessaire > 0 && robot.peutFaireIntervention(volume)) {

            vidages.add(new Vidage(date_debut, robot, incendie));

            volume -= robot.getQuantEauIntervention();
            eau_necessaire -= robot.getQuantEauIntervention();
            date_debut += Vidage.calcDuree(robot);
        }
        return vidages;
    }


    /**
     * Exécute l'événement de vidage du robot.

     * @throws Error Si le robot n'est pas sur l'incendie ou si l'incendie est null.
     */
    @Override
    public void execute(){

        if (incendie == null) throw new Error("[!] L'incendie est null");

        //Si le robot se trouve sur l'incendie
        if (robot.getPosition().equals(incendie.getPosition())) {
            if (!robot.getUtilisePoudre()) robot.derverserEau(robot.getQuantEauIntervention());
            incendie.eteindre(robot.getQuantEauIntervention());
            return;
        } 

        throw new Error("[!] Le robot n'est pas sur l'incendie.");
    }

    @Override
    public String toString() {
        return "Vidage (robot: "+robot.toString()+", incendie: "+incendie.toString()+")";
    }
}