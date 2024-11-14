package strategie;


import java.util.LinkedList;

import chemins.CaseDuree;
import chemins.PlusCoursChemin;
import donnees.DonneesSimulation;
import donnees.Incendie;
import donnees.robots.Robot;


public class ChefPompierEvolue extends ChefPompier {

    /**
     * Méthode principale qui exécute la stratégie avancée de gestion des incendies.
     * Envoie chaque robot disponible soit pour se remplir d'eau (si le réservoir est vide),
     * soit pour éteindre un incendie non affecté, en calculant un chemin d'accès optimal.
     *
     * @param data          Données de la simulation contenant la carte, la liste des robots, et les incendies.
     * @param date_courante La date actuelle dans la simulation, utilisée pour planifier les événements.
     */
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
