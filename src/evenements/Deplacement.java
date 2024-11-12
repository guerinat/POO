package evenements;

import donnees.carte.*;
import donnees.robots.*;

public class Deplacement extends Evenement{

    private Direction dir;
    private Carte carte;
    private Robot robot;
    private Case depart;

    public Deplacement(long date_debut, Carte carte, Case depart, Direction dir, Robot robot){
        super(date_debut + calcDuree(carte, depart, robot, dir));
        this.dir = dir;
        this.carte = carte;
        this.robot = robot; 
        this.depart = depart;
    }

    //Calcule la durée d'un robot d'une case future de départ à sa case voisine dans la direction dir
    private static long calcDuree(Carte carte, Case depart, Robot robot, Direction dir) {

        NatureTerrain nature_depart = depart.getNature();
        NatureTerrain nature_arrive = carte.getVoisin(depart, dir).getNature();

        double vitesse_depart = robot.getVitesse(nature_depart);
        double vitesse_arrive = robot.getVitesse(nature_arrive);

        if (vitesse_depart == 0) throw new Error("[!] Position de depart invalide");
        if (vitesse_arrive == 0) throw new Error("[!] Le robot ne peut pas aller sur ce terrain");

        double vitesse_moyenne = (vitesse_depart + vitesse_arrive)/2;

        //Convertion pour duree en secondes
        long duree = (long)(3.6*carte.getTailleCases()/vitesse_moyenne); 

        return duree;
    }

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
    
    public int compareTo(Deplacement other) {
        return Long.compare(this.getdateFin(), other.getdateFin()); // Trie par date croissante
    }
}