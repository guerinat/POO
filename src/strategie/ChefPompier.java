package strategie;

import java.util.ArrayList;
import java.util.LinkedList;

import chemins.CaseDuree;
import chemins.PlusCoursChemin;
import donnees.DonneesSimulation;
import donnees.Incendie;
import donnees.robots.Robot;
import evenements.*;
import donnees.carte.*;

public class ChefPompier {


    public static ArrayList<Evenement> envoyerRobotIncendie(Robot robot, Incendie incendie, LinkedList<CaseDuree> chemin, long date_courante, Carte carte) {

        //Deplacement
        ArrayList<Evenement> events = PlusCoursChemin.deplacerRobotChemin(date_courante, chemin, robot, carte);
        long dateApresChemin = date_courante + PlusCoursChemin.duree_chemin(chemin);

        //Vidage
        events.addAll(Vidage.viderEntierementRobot(robot, incendie, dateApresChemin)); 

        // //Changement d'etat
        events.add(new ChangerEtat(date_courante, robot, Etat.DEPLACEMENT));

        return events;


    }


    public static ArrayList<Evenement> envoyerRobotsDisponibles(DonneesSimulation data, long date_courante) {

        ArrayList<Evenement> events = new ArrayList<Evenement>();

        for(Robot robot : data.robots) {
            

            if (robot.getEtat() != Etat.DISPONNIBLE) continue; 

           

            for(Incendie incendie : data.incendies) {
                if (incendie.getEauNecessaire() == 0 || incendie.estAffecte(data.robots)) 
                    continue;

                    

                LinkedList<CaseDuree> chemin = PlusCoursChemin.djikstra(robot, incendie.getPosition(), data.carte);
                if (chemin.isEmpty()) continue;

                

                events.addAll(envoyerRobotIncendie(robot, incendie, chemin, date_courante, data.carte));
                robot.setIncendieAffecte(incendie);
                break;   
            } 
        }
        return events;
    }
}
