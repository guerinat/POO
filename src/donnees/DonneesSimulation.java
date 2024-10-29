package donnees;

import donnees.*;
import donnees.carte.*;
import donnees.robots.*;

public class DonneesSimulation {

    private Robot[] robots;
    private Incendie[] incendies;
    private Carte carte;

    public DonneesSimulation(Robot[] robots, Incendie[] incendies, Carte carte) {
        this.carte = carte;
        this.robots = robots;
        this.incendies = incendies;
    }

}
