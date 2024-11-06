package donnees;

import donnees.carte.*;
import donnees.robots.*;

public class DonneesSimulation {

    public Carte carte;
    public Robot[] robots;
    public Incendie[] incendies;
    

    public DonneesSimulation(Robot[] robots, Incendie[] incendies, Carte carte) {
        this.carte = carte;
        this.robots = robots;
        this.incendies = incendies;
    }

    //Renvoi l'incendie à une position donnée (nulle si elle n'existe pas)
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
