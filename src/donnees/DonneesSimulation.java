package donnees;

import donnees.carte.*;
import donnees.robots.*;
import strategie.Etat;

public class DonneesSimulation {

    public Carte carte;
    public Robot[] robots;
    public Incendie[] incendies;
    
    /**
     * Constructeur pour initialiser les données de la simulation.
     * 
     * @param robots Le tableau de robots présents dans la simulation.
     * @param incendies Le tableau d'incendies présents dans la simulation.
     * @param carte La carte lue de la simulation.
     */
    public DonneesSimulation(Robot[] robots, Incendie[] incendies, Carte carte) {
        this.carte = carte;
        this.robots = robots;
        this.incendies = incendies;
        
        //On initialise les robotos avec un etat inutile si il ne peuvent acceder à aucun incendies.
        for(Robot robot : robots)
            if (!robot.estUtile(incendies, carte))
                robot.setEtat(Etat.INUTILE);
    }

    
    /**
     * Renvoie l'incendie situé à la position donnée, ou null si aucun incendie n'est trouvé à cette position.
     * 
     * @param src La position de la case à vérifier.
     * @return L'incendie à la position spécifiée, ou null si aucun incendie n'existe à cette position.
     */
    public Incendie IncendiePos(Case src) {
        for(Incendie incendie : incendies) {
            if (incendie.getPosition().equals(src)) {
                return incendie;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        String s = "";

        s += carte.toString() + "\n";
        s+= "# ROBOTS\n";
        for(int i = 0; i < robots.length; i++)  s +=  robots[i].toString() + "\n";
        s+="\n# INCENDIES\n";
        for(int i = 0; i < incendies.length; i++) s += incendies[i].toString() + "\n";

        return s;
        
    }

}
