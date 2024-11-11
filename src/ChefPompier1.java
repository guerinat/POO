import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.ArrayList;

import chemins.*;
import donnees.*;
import donnees.robots.*;
import donnees.carte.*;
import evenements.*;


public abstract class ChefPompier1{
    
    public Incendie detecteIncendie(DonneesSimulation data){
        for (int incendie=0; incendie<data.incendies.length;incendie++){
            if (data.incendies[incendie].getEauNecessaire()>0 && data.incendies[incendie].getRobot()==null){
                //return data.carte.getCase(data.incendies[incendie].getPosition().getLigne(), data.incendies[incendie].getPosition().getColonne());
                return data.incendies[incendie];
            }
        }
        System.out.println("Pas d'incendie détécté");
        return null;
    }


    public Robot robotDispo(DonneesSimulation data, Incendie incendie){
        if (incendie==null) return null;
        for (int robot=0; robot<data.robots.length;robot++){
            if (data.robots[robot].getIncendie() == null){
                incendie.changeRobot(data.robots[robot]);
                data.robots[robot].changeIncendie(incendie);
                return data.robots[robot];
            }
        }
        return null;
    }

    public static boolean RobotOccupe(Incendie[] incendies, Robot robot){
        for (Incendie incendie : incendies){
            if (incendie.getRobot() == robot)
                return true;
        }
        return false;
    }


    public PriorityQueue<Evenement> EteindreIncendie(DonneesSimulation data, long date_debut){
        long date = date_debut;
        Incendie incendie = detecteIncendie(data);
        Robot robot = robotDispo(data,incendie);
        LinkedList<CaseDuree> chemin = PlusCoursChemin.djikstra(robot, incendie.getPosition(), data.carte);
        PriorityQueue<Evenement> events = PlusCoursChemin.deplacerRobotChemin(date, chemin, robot, data.carte);
        date += PlusCoursChemin.duree_chemin(chemin);
        while (incendie.getEauNecessaire()!=0){
            while (robot.getQuantEau()>0){
                events.add(new Vidage(date_debut, robot, incendie));
                date += Vidage.calcDuree(robot);
            }
            Case Eau = PlusCoursChemin.PlusProcheEau(data, robot);
            LinkedList<CaseDuree> cheminEau = PlusCoursChemin.djikstra(robot, incendie.getPosition(), data.carte);
            Evenement[] eventsEau = PlusCoursChemin.deplacerRobotChemin(date_debut, chemin, robot, data.carte).toArray(new Evenement[0]);
            for (int i=0; i<eventsEau.length;i++){
                events.add(eventsEau[i]);
                if (i==eventsEau.length-1 && !robot.getRemplitSurEau())
                    break;
            }
            date += PlusCoursChemin.duree_chemin(cheminEau);
            events.add(new Remplissage(date, data.carte, robot, robot.getQuantReservoire()));
            date += Remplissage.calcDuree(robot, robot.getQuantReservoire());
            LinkedList<CaseDuree> chemin2 = PlusCoursChemin.djikstra(robot, incendie.getPosition(), data.carte);
            Evenement[] events2 = PlusCoursChemin.deplacerRobotChemin(date, chemin2, robot, data.carte).toArray(new Evenement[0]);
            for (int i=0; i<events2.length;i++){
                events.add(events2[i]);
            }
            date += PlusCoursChemin.duree_chemin(chemin);
        }
        return events;
    }

}
    

