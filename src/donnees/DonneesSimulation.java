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

    public int incendie_size(){
        int size=0;
        for (Incendie incendie : this.incendies){
            size+=1;
        }
        return size;
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
