package evenements;

import donnees.robots.*;
import strategie.*;

public class ChangerEtat extends Evenement{

    private Robot robot;
    private Etat etat;



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