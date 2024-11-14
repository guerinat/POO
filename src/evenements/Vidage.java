package evenements;

import java.util.ArrayList;
import java.util.LinkedList;

import donnees.Incendie;
import donnees.robots.*;

public class Vidage extends Evenement{

    private Robot robot;
    private Incendie incendie;

    
    public Vidage(long date_debut, Robot robot, Incendie incendie){
        
        super(date_debut + calcDuree(robot));
        this.robot = robot;
        this.incendie = incendie;
    }


    public static long calcDuree(Robot robot) {
        long duree = robot.getDureeIntervention();
        return duree;
    }


    public static LinkedList<Evenement> viderEntierementRobot(Robot robot, Incendie incendie, long date_debut) {

        LinkedList<Evenement> vidages = new LinkedList<>();
        int eau_necessaire = incendie.getEauNecessaire();
        int volume = robot.getQuantEau();

        while(eau_necessaire > 0 && robot.peutFaireIntervention(volume)) {

            vidages.add(new Vidage(date_debut, robot, incendie));

            volume -= robot.getQuantEauIntervention();
            eau_necessaire -= robot.getQuantEauIntervention();
            date_debut += Vidage.calcDuree(robot);
        }
        return vidages;
    }


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