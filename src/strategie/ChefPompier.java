package strategie;

import java.util.ArrayList;
import java.util.LinkedList;

import chemins.CaseDuree;
import chemins.PlusCoursChemin;
import donnees.DonneesSimulation;
import donnees.Incendie;
import donnees.robots.Robot;
import evenements.*;
import simulateurs.*;
import donnees.carte.*;


public class ChefPompier {

    Simulateur simulateur;


    public void setSimulateur(Simulateur simulateur) {
        this.simulateur = simulateur;
    }


    public void envoyerRobotIncendie(Robot robot, Incendie incendie, LinkedList<CaseDuree> chemin, long date_courante, Carte carte) {

        //Deplacement
        ArrayList<Evenement> deplacements = PlusCoursChemin.deplacerRobotChemin(date_courante, chemin, robot, carte);
        long dateApresChemin = date_courante + PlusCoursChemin.duree_chemin(chemin);
        simulateur.ajouteEvenements(deplacements);

        //Vidage
        ArrayList<Evenement> vidage = Vidage.viderEntierementRobot(robot, incendie, dateApresChemin);
        long dureeApresVidage = dateApresChemin + vidage.size()*Vidage.calcDuree(robot);
        simulateur.ajouteEvenements(vidage);

        //Changements des etats
        simulateur.ajouteEvenement(new ChangerEtat(date_courante, robot, Etat.DEPLACEMENT));
        simulateur.ajouteEvenement(new ChangerEtat(dateApresChemin, robot, Etat.VIDAGE));
        simulateur.ajouteEvenement(new ChangerEtat(dureeApresVidage, robot, Etat.DISPONNIBLE));
    }




    public void strategieElementaire(DonneesSimulation data, long date_courante) {

        for(Robot robot : data.robots) {
            

            if (robot.getEtat() != Etat.DISPONNIBLE) continue; 

            for(Incendie incendie : data.incendies) {
                if (incendie.getEauNecessaire() == 0 || incendie.estAffecte(data.robots)) 
                    continue;

                LinkedList<CaseDuree> chemin = PlusCoursChemin.djikstra(robot, incendie.getPosition(), data.carte);
                if (chemin.isEmpty()) continue;

                envoyerRobotIncendie(robot, incendie, chemin, date_courante, data.carte);
                robot.setIncendieAffecte(incendie);
                break;   
            } 
        }
    }
}
