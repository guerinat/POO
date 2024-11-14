package donnees.robots;

import donnees.carte.Case;
import donnees.carte.NatureTerrain;
import donnees.*;
import strategie.*;



/**
 * Constructeur pour les robots à poudre (n'ont pas de réservoir d'eau et n'effectuent pas de remplissage).
 * 
 * @param position La position initiale du robot sur la carte.
 * @param vitesseTerrain Les vitesses du robot sur les différents types de terrain (EAU, FORET, etc.).
 * @param utilisePoudre Si le robot utilise de la poudre pour les interventions.
 * @param quant_eau_intervention La quantité d'eau utilisée lors d'une intervention.
 * @param duree_intervention La durée d'une intervention en secondes.
 */
public abstract class Robot {

    private Case position;
    private double[] vitesseTerrain;

    private boolean utilisePoudre;
    private boolean remplitSurEau;

    private int quant_reservoire;
    private int quant_eau;
    private long duree_remplissage; 

    private int quant_eau_intervention; 
    private int duree_intervention;

    private Etat etat;
    private Incendie incendie_affecte;

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
        this.incendie_affecte = null;
        this.etat = Etat.DISPONNIBLE;
    }

    //Robots à poudre
    public Robot(Case position, double[] vitesseTerrain, boolean utilisePoudre,
    int quant_eau_intervention, int duree_intervention) {
        this.position = position;
        this.vitesseTerrain = vitesseTerrain;
        this.utilisePoudre = utilisePoudre;
        this.quant_eau_intervention = quant_eau_intervention;
        this.duree_intervention = duree_intervention;
        this.incendie_affecte = null;
        this.etat = Etat.DISPONNIBLE;
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

        if (utilisePoudre)
            return Integer.MAX_VALUE;

        return this.quant_eau;
    }

    public int getQuantReservoire() {

        if (utilisePoudre)
            return Integer.MAX_VALUE;

        return this.quant_reservoire;
    }

    public long getDureeRemplissage() {
        if (utilisePoudre)
            throw new Error("[!] Le robot est à poudre il n'a pas à se remplir.");

        return this.duree_remplissage;
    }

    public int getQuantEauIntervention() {
        return this.quant_eau_intervention;
    }

    public int getDureeIntervention() {
        return this.duree_intervention;
    }

    public boolean getRemplitSurEau() {
        return remplitSurEau;
    }

    public boolean getUtilisePoudre() {
        return utilisePoudre;
    }

    public abstract String getLienTexture();

    /**
     * Permet de déverser une certaine quantité d'eau du réservoir du robot.
     * 
     * @param volume La quantité d'eau à déverser.
     */
    public void derverserEau(int volume) {

        if (utilisePoudre)
            return;

        if (volume > this.quant_eau) 
            throw new Error("[!] Il n'y a pas assez d'eau dans le reservoire.");
        
        this.quant_eau -= volume;
    }
    
    public void remplirReservoire() {

        if (utilisePoudre)
            throw new Error("[!] Le robot est à poudre il n'a pas de réservoire.");

        this.quant_eau = this.quant_reservoire;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    } 

    public Incendie getIncendieAffecte() {
        return this.incendie_affecte;
    }

    public void setIncendieAffecte(Incendie incendie) {
        this.incendie_affecte = incendie;
    }

    //Renvoi true si le robot peut faire une intervention si il a un volume d'eau donnée dans son revervoire.
    public boolean peutFaireIntervention(int volume) {
        if (utilisePoudre) return true;

        return volume >= getQuantEauIntervention();
    }

    abstract public String toString();

    
}


