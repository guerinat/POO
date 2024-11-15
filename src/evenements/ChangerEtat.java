package evenements;

import donnees.robots.*;
import strategie.*;


public class ChangerEtat extends Evenement{

    
    private Robot robot;
    private Etat etat;


    /**
     * Constructeur pour initialiser un événement qui modifie l'état d'un robot.
     * 
     * @param date_debut La date de début de l'événement. 
     * @param robot Le robot dont l'état va être modifié.
     * @param etat Le nouvel état que le robot doit adopter.
     */
    public ChangerEtat(long date_debut, Robot robot, Etat etat){
        super(date_debut);
        this.robot = robot;
        this.etat = etat;
    }


    @Override
    public void execute(){
        robot.setEtat(etat);

    }


    @Override
    public String toString() {
        return "Changer Etat (robot:"+robot.toString()+", "+etat+")";
    }
}