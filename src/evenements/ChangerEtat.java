package evenements;

import donnees.Incendie;
import donnees.robots.*;
import strategie.*;

public class ChangerEtat extends Evenement{

    private Robot robot;
    private Etat etat;
    private Incendie incendie_affecte;



    public ChangerEtat(long date_debut, Robot robot, Etat etat){
        super(date_debut);
        this.robot = robot;
        this.etat = etat;
        incendie_affecte = null;
    }

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