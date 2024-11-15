package evenements;

import donnees.Incendie;
import donnees.robots.*;
import strategie.*;

public class ChangerEtat extends Evenement{

    private Robot robot;
    private Etat etat;
    private Incendie incendie_affecte;


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
        incendie_affecte = null;
    }

    /**
     * Constructeur pour initialiser un événement qui modifie l'état d'un robot,
     * avec un incendie associé.
     * 
     * @param date_debut La date de début de l'événement.
     * @param robot Le robot dont l'état va être modifié.
     * @param etat Le nouvel état que le robot doit adopter.
     * @param incendie_affecte L'incendie auquel le robot est affecté.
     */
    public ChangerEtat(long date_debut, Robot robot, Etat etat, Incendie incendie_affecte){
        super(date_debut);
        this.robot = robot;
        this.etat = etat;
        this.incendie_affecte = incendie_affecte;
    }


    @Override
    public void execute(){
        robot.setEtat(etat);
        if (incendie_affecte != null)
            robot.setIncendieAffecte(incendie_affecte);
    }

    @Override
    public String toString() {
        return "Changer Etat (robot:"+robot.toString()+", "+etat+")";
    }
}