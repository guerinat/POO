package strategie;

import java.util.HashMap;
import java.util.HashSet;

import donnees.Incendie;
import donnees.robots.Robot;

/**
 * Association bi-directionelle entres robots et incendies
 * Une incendie peut avoir plusieurs robots
 * Un robot n'a qu'une seule incendie
 * 
 * La classe gère les association entre robots et incendies (ajout, supression...)
 */
public class AssociationRobotsIncendie {

    //Hashmaps des associations
    private HashMap<Robot, Incendie> RobotVersIncendie = new HashMap<>();
    private HashMap<Incendie, HashSet<Robot>> IncendieVersRobots = new HashMap<>();

    
    /**
     * Associe @param robot avec @param incendie
     */
    public void associer(Robot robot, Incendie incendie) {
        
        // Si le robot est déjà associé à un incendie, on le supprime.
        if (RobotVersIncendie.containsKey(robot)) 
            supprimer(robot);
        
        // Ajouter l'association entre le robot et l'incendie
        RobotVersIncendie.put(robot, incendie);

        if (!IncendieVersRobots.containsKey(incendie)) 
            IncendieVersRobots.put(incendie, new HashSet<>());
        
        IncendieVersRobots.get(incendie).add(robot);
    }


    /**
     * Supprime l'association entre @param robot et l'incendie correspondant
     */
    public void supprimer(Robot robot) {

        Incendie incendie = RobotVersIncendie.remove(robot);
        if (incendie == null) return;

        HashSet<Robot> robots = IncendieVersRobots.get(incendie);
        if (robots == null) return;

        robots.remove(robot);
        if (robots.isEmpty()) IncendieVersRobots.remove(incendie);
    }


    /**
     * @return l'incendie associé à @param robot si elle existe (null sinon)
     */
    public Incendie getIncendie(Robot robot) {
        return RobotVersIncendie.get(robot);
    }


    /**
     * @return les robots associé à @param incendie si ils existent 
     */
    public HashSet<Robot> getRobots(Incendie incendie) {
            return IncendieVersRobots.get(incendie);
    }


    /**
     * @return  true si un incendie et associé à @param robot
     */
    public boolean robotEstAssocie(Robot robot) {
        return RobotVersIncendie.containsKey(robot);
    }


     /**
     * @return true si un @param incendie est associé à un robot
     */
    public boolean incendieEstAssocie(Incendie incendie) {
        return IncendieVersRobots.containsKey(incendie);
    }
}