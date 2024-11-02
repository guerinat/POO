package donnees.robots;

import donnees.carte.Case;
import donnees.carte.NatureTerrain;


public abstract class Robot {

    private Case position;
    private double[] vitesseTerrain;

    private boolean utilisePoudre;
    private boolean remplitSurEau;

    private int quant_reservoire;
    private int quant_eau;
    private int duree_remplissage; 

    private int quant_eau_intervention;
    private int duree_intervention;

    private static final int nb_type_robots = 5;

    //Robots à eau
    public Robot(Case position, double[] vitesseTerrain, boolean utilisePoudre, boolean remplitSurEau, 
    int quant_reservoire, int quant_eau, int duree_remplissage, int quant_eau_intervention, int duree_intervention) {
        this.position = position;
        this.vitesseTerrain = vitesseTerrain;
        this.utilisePoudre = utilisePoudre;
        this.remplitSurEau = remplitSurEau;
        this.quant_reservoire = quant_reservoire;
        this.quant_eau = quant_eau;
        this.duree_remplissage = duree_remplissage;
        this.quant_eau_intervention = quant_eau_intervention;
        this.duree_intervention = duree_intervention;
    }

    //Robots à poudre
    public Robot(Case position, double[] vitesseTerrain, boolean utilisePoudre,
    int quant_eau_intervention, int duree_intervention) {
        this.position = position;
        this.vitesseTerrain = vitesseTerrain;
        this.utilisePoudre = utilisePoudre;
        this.quant_eau_intervention = quant_eau_intervention;
        this.duree_intervention = duree_intervention;
    }


    public Case getPosition() {
        return this.position;
    }

    public void setPosition(Case position) {
        this.position = position;
    }

    public double getVitesse(NatureTerrain nature_terain) {
        return this.vitesseTerrain[nature_terain.ordinal()];
    }

    public int getQuantEau() {
        return this.quant_eau;
    }

    public int getQuantReservoire() {
        return this.quant_reservoire;
    }


    public void derverserEau(int volume) {

        if (utilisePoudre) return;

        if (volume > this.quant_eau) {
            throw new Error("[!] Il n'y a pas assez d'eau dans le reservoire.");
        }
        this.quant_eau -= volume;
    }
    
    public void remplirReservoire() {

        if (utilisePoudre) return;

        this.quant_eau = this.quant_reservoire;
    }

    public static int getNbTypeRobots() {
        return nb_type_robots;
    }

    public boolean getRemplitSurEau() {
        return remplitSurEau;
    }

    abstract public String toString();

    public abstract int getTextureId();
}


