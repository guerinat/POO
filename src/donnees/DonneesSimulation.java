package donnees;

import donnees.carte.*;
import donnees.robots.*;

public class DonneesSimulation {

    public Carte carte;
    public Robot[] robots;
    public Incendie[] incendies;
    
/**
 * Constructeur pour initialiser les données de la simulation.
 * 
 * @param robots Un tableau de robots présents dans la simulation.
 * @param incendies Un tableau d'incendies présents dans la simulation.
 * @param carte La carte représentant l'environnement de simulation.
 */
    public DonneesSimulation(Robot[] robots, Incendie[] incendies, Carte carte) {
        this.carte = carte;
        this.robots = robots;
        this.incendies = incendies;
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

/**
 * Retourne une représentation sous forme de chaîne de caractères des données de la simulation, incluant la carte, les robots et les incendies.
 * 
 * @return Une chaîne de caractères représentant l'état complet de la simulation (carte, robots et incendies).
 */
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
