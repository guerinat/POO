package evenements;

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

    @Override
    public void execute(){

        if (incendie == null) throw new Error("[!] L'incendie est null");

        //Si le robot se trouve sur l'incendie
        if (robot.getPosition().equals(incendie.getPosition())) {
            robot.derverserEau(robot.getQuantEauIntervention());
            incendie.eteindre(robot.getQuantEauIntervention());
            if (incendie.getEauNecessaire()==0){
                incendie.changeRobot(null);
                robot.changeIncendie(null);
            }
            return;
        } 

        throw new Error("[!] Le robot n'est pas sur l'incendie.");
    }

    @Override
    public String toString() {
        return "Vidage (robot: "+robot.toString()+", incendie: "+incendie.toString()+")";
    }
}