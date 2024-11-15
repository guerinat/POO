package strategie;


import java.util.LinkedList;

import chemins.CaseDuree;
import chemins.PlusCoursChemin;
import donnees.DonneesSimulation;
import donnees.Incendie;
import donnees.robots.Robot;


/**
 * Stratégie évoluée
**/
public class ChefPompierEvolue extends ChefPompier {
   

    /**
     * Exécute la stratégie évoluée
     * Plusieurs robots peuvent être sur le même incendie
     * Et l'affectation des robots est calculé par rapport aux robots les plus proches des incendies
     *
     * @param data          Données de la simulation.
     * @param date_courante La date actuelle dans la simulation.
     */
    public void jouerStrategie(DonneesSimulation data, long date_courante) {

        //Pour chaque robot
        for(Robot robot : data.robots) {

            //Si le robot est prêt à se vider, on l'envoi se remplir
            if (robot.getEtat() == Etat.PRET_POUR_VIDAGE) {
                viderRobot(robot, affectes.getIncendie(robot), date_courante, data.carte);
                continue;
            }

            //On skip les robots INUTILES
            if (robot.getEtat() != Etat.DISPONIBLE) 
                continue; 

            //On envoi les robots vides se remplir
            if (!robot.peutFaireIntervention(robot.getQuantEau())) {
                envoyerRobotSeRemplir(robot, date_courante, data.carte);
                affectes.supprimer(robot);
            }

        }

        //Chercher une incendie non-eteinte.
        for(Incendie incendie : data.incendies) {

            if (incendie.getEauNecessaire() == 0) continue;

            //Chercher le robot le plus proche non-affectés, disponible et avec de l'eau
            //On doit verifier que le robot a bien de l'eau car si on vient de l'envoyer se remplir il est encore en etat disponnible
            Robot plusProcheRobot = PlusCoursChemin.robotPlusProcheIncendie(data.carte, data.robots, incendie, affectes);

            //Si un tel robot n'existe pas on passe au prochain incendie
            if (plusProcheRobot == null) continue;

            //Envoyer le robot sur l'incendie
            LinkedList<CaseDuree> chemin = PlusCoursChemin.djikstra(plusProcheRobot, incendie.getPosition(), data.carte, false);
            envoyerRobotSurIncendie(plusProcheRobot, incendie, chemin, date_courante, data.carte);
            affectes.associer(plusProcheRobot, incendie); //On associe le robot à l'incendie
        }
    }
}
