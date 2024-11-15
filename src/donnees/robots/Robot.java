package donnees.robots;

import donnees.carte.Carte;
import donnees.carte.Case;
import donnees.carte.NatureTerrain;
import chemins.PlusCoursChemin;
import donnees.*;
import strategie.*;


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

    /**
     * Constructeur pour les robots utilisant de l'eau.
     * 
     * @param position La position initiale du robot sur la carte.
     * @param vitesseTerrain Les vitesses du robot sur différents types de terrain.
     * @param utilisePoudre Indique si le robot utilise de la poudre.
     * @param remplitSurEau Indique si le robot peut se remplir directement sur une source d'eau.
     * @param quant_reservoire La capacité maximale du réservoir d'eau du robot.
     * @param quant_eau La quantité d'eau actuelle dans le réservoir.
     * @param duree_remplissage La durée nécessaire pour remplir complètement le réservoir (en millisecondes).
     * @param quant_eau_intervention La quantité d'eau utilisée lors d'une intervention.
     * @param duree_intervention La durée d'une intervention en secondes.
     */
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

    
    /**
     * Constructeur pour les robots utilisant de la poudre.
     * 
     * @param position La position initiale du robot sur la carte.
     * @param vitesseTerrain Les vitesses du robot sur différents types de terrain.
     * @param utilisePoudre Indique si le robot utilise de la poudre.
     * @param quant_eau_intervention La quantité nécessaire pour une intervention (toujours utilisée pour poudre).
     * @param duree_intervention La durée d'une intervention en secondes.
     */
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

    /**
     * @param nature_terain Le type de terrain.
     * @return La vitesse du robot sur le terrain donné.
     */
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

    /**
     * @return La durée nécessaire pour remplir complètement le réservoir.
     * @throws Error Si le robot utilise de la poudre et n'a pas de réservoir.
     */
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
     * Déverse une certaine quantité d'eau du réservoir.
     * 
     * @param volume La quantité d'eau à déverser.
     * @throws Error Si le volume demandé est supérieur à la quantité d'eau disponible dans le réservoir.
     */
    public void derverserEau(int volume) {

        if (utilisePoudre)
            return;

        if (volume > this.quant_eau) 
            throw new Error("[!] Il n'y a pas assez d'eau dans le reservoire.");
        
        this.quant_eau -= volume;
    }
        
    /**
     * Remplit le réservoir d'eau à sa capacité maximale.
     * 
     * @throws Error Si le robot utilise de la poudre et n'a pas de réservoir.
     */
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

    
    /**
     * Vérifie si le robot peut effectuer une intervention avec le volume d'eau donné.
     * 
     * @param volume Le volume d'eau disponible.
     * @return `true` si le robot peut effectuer une intervention, sinon `false`.
     */
    public boolean peutFaireIntervention(int volume) {
        if (utilisePoudre) return true;

        return volume >= getQuantEauIntervention();
    }
    

    /**
     * @return `true` si le @param robot peut au moins acceder à un incendie, false sinon.
     */
    public boolean estUtile( Incendie[] incendies, Carte carte) {
        
        for(Incendie incendie : incendies) 
            if (PlusCoursChemin.djikstra(this, incendie.getPosition(), carte, false) != null)
                return true;

        return false;
    }


    abstract public String toString();

    
}


