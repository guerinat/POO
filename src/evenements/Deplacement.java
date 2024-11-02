package evenements;

import donnees.carte.*;
import donnees.robots.*;

public class Deplacement extends Evenement{

    private Direction dir;
    private Carte carte;
    private Robot robot;

    public Deplacement(long date_depart, Carte carte, Direction dir, Robot robot){
        super(date_depart, date_depart + calcDuree(date_depart, robot, carte, dir));
        this.dir = dir;
        this.carte = carte;
        this.robot = robot; 
    }

    private static long calcDuree(long date_depart, Robot robot, Carte carte, Direction dir) {

        NatureTerrain nature_depart = robot.getPosition().getNature();
        NatureTerrain nature_arrive = carte.getVoisin(robot.getPosition(), dir).getNature();
        double vitesse_moyenne = (robot.getVitesse(nature_depart)+robot.getVitesse(nature_arrive))/2;

        //Duree en seconde
        long duree = (long)(3.6*carte.getTailleCases()/vitesse_moyenne); 

        return duree;
    }

    @Override
    public void execute(){
        Case destination = carte.getVoisin(robot.getPosition(), dir);
        if (robot.getVitesse(destination.getNature()) == 0)
            throw new Error("[!] Le robot ne peut pas aller sur une case "+destination.getNature().toString()+".");
        robot.setPosition(destination);
    }

    @Override
    public String toString() {
        return "Deplacement (robot:"+robot.toString()+", dir:"+dir.toString()+")";
        
    }
}