package evenements;

import donnees.carte.*;
import donnees.robots.*;

/**
 * Représente un événement de déplacement d'un robot.
 **/
public class Deplacement extends Evenement{


    private Direction dir;
    private Carte carte;
    private Robot robot;
    private Case depart;


    /**
     * Constructeur pour initialiser un événement de déplacement d'un robot.
     * 
     * @param date_debut La date de début de l'événement.
     * @param carte La carte sur laquelle le robot évolue.
     * @param depart La case de départ du robot.
     * @param dir La direction dans laquelle le robot se déplace.
     * @param robot Le robot qui va se déplacer.
     */
    public Deplacement(long date_debut, Carte carte, Case depart, Direction dir, Robot robot){
        super(date_debut + calcDuree(carte, depart, robot, dir));
        this.dir = dir;
        this.carte = carte;
        this.robot = robot; 
        this.depart = depart;
    }


    /**
     * Calcule la durée du déplacement du robot entre la case de départ et la case voisine dans la direction spécifiée.
     * 
     * @param carte La carte sur laquelle le robot se déplace.
     * @param depart La case de départ du robot.
     * @param robot Le robot qui se déplace.
     * @param dir La direction dans laquelle le robot va se déplacer.
     * @return La durée du déplacement en secondes.
     * @throws Error Si la position de départ ou la destination n'est pas valide.
     */
    private static long calcDuree(Carte carte, Case depart, Robot robot, Direction dir) {

        NatureTerrain nature_depart = depart.getNature();

        NatureTerrain nature_arrive = carte.getVoisin(depart, dir).getNature();

        double vitesse_depart = robot.getVitesse(nature_depart);
        double vitesse_arrive = robot.getVitesse(nature_arrive);

        //Si le depart ou l'arrivé ne sont pas accessibles.
        if (vitesse_depart == 0) throw new Error("[!] Position de depart invalide");
        if (vitesse_arrive == 0) throw new Error("[!] Le robot ne peut pas aller sur ce terrain");

        double vitesse_moyenne = (vitesse_depart + vitesse_arrive)/2;

        //Convertion pour que la duree soit en secondes
        long duree = (long)(3.6*carte.getTailleCases()/vitesse_moyenne); 

        return duree;
    }

    
    /**
     * Exécute le déplacement.
     * 
     * @throws Error Si le robot n'est pas sur la case de départ ou si le robot ne peut pas se déplacer sur la case voisine.
     */
    @Override
    public void execute(){

        if (!depart.equals(robot.getPosition()))
            throw new Error("[!] Deplacement interdit: le robot n'est pas sur la case départ de l'evenement"+robot.toString()+depart.toString());

        Case destination = carte.getVoisin(depart, dir);
        
        if (robot.getVitesse(destination.getNature()) == 0)
            throw new Error("[!] Le robot ne peut pas aller sur une case "+destination.getNature().toString()+".");

        robot.setPosition(destination);
    }


    @Override
    public String toString() {
        return "Deplacement (robot:"+robot.toString()+", dir:"+dir.toString()+")";
    }

}