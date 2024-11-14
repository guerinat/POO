package strategie;


import java.util.LinkedList;

import chemins.CaseDuree;
import chemins.PlusCoursChemin;
import donnees.DonneesSimulation;
import donnees.Incendie;
import donnees.robots.Robot;


public class ChefPompierEvolue extends ChefPompier {

    public void jouerStrategie(DonneesSimulation data, long date_courante) {

        //Chercher un robot disponible
        for(Robot robot : data.robots) {
            
            if (robot.getEtat() != Etat.DISPONNIBLE) continue; 

            //Si le reservoire du robot est vide, envoyer le robot chercher de l'eau.
            if(!robot.peutFaireIntervention(robot.getQuantEau())) {
                
                envoyerRobotSeRemplir(robot, date_courante, data.carte);

                //Desafectée l'incendie du robot si elle existe
                if (robot.getIncendieAffecte() != null)
                    incendiesAffectes.remove(robot.getIncendieAffecte());

                continue;
            }

            //Sinon, chercher une incendie non-eteinte, non-affectée.
            for(Incendie incendie : data.incendies) {

                if (incendie.getEauNecessaire() == 0 || incendiesAffectes.contains(incendie)) 
                    continue;

                //Si l'incendie est innacessible, continuer.
                LinkedList<CaseDuree> chemin = PlusCoursChemin.djikstra(robot, incendie.getPosition(), data.carte);
                if (chemin.isEmpty()) continue;

                //Sinon, envoyer le robot eteindre l'incendie.
                envoyerRobotEteindreIncendie(robot, incendie, chemin, date_courante, data.carte);

                //Affecte l'incendie concernée
                incendiesAffectes.add(incendie);

                break;   
            } 
        }
    }
}
