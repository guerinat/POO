package donnees;
import donnees.carte.*;
import donnees.robots.*;

public class Incendie {

    private Case position;
    private int eau_necessaire;
    

    /**
     * Constructeur pour initialiser un incendie avec sa position et la quantité d'eau nécessaire.
     * 
     * @param position La position de l'incendie sur la carte.
     * @param eau_necessaire La quantité d'eau nécessaire pour éteindre l'incendie (en litres).
     */
    public Incendie(Case position, int eau_necessaire) {
        this.position = position;
        this.eau_necessaire = eau_necessaire;
    }

    /**
     * Retourne la position de l'incendie.
     * 
     * @return La position de l'incendie sous forme d'un objet `Case`.
     */
    public Case getPosition() {
        return this.position;
    }

    /**
     * Retourne la quantité d'eau nécessaire pour éteindre l'incendie.
     * 
     * @return La quantité d'eau nécessaire pour éteindre l'incendie (en litres).
     */
    public int getEauNecessaire() {
        return this.eau_necessaire;
    }

    /**
     * Réduit la quantité d'eau nécessaire pour éteindre l'incendie en fonction du volume d'eau versé.
     * Si le volume est suffisant, l'incendie est éteint.
     * 
     * @param volume La quantité d'eau versée pour éteindre l'incendie (en litres).
     */
    public void eteindre(int volume) {
        eau_necessaire = volume < eau_necessaire ? eau_necessaire-volume : 0;
    }


    @Override
    public String toString() {
        if (eau_necessaire == 0)
            return "(pos:"+position.toString()+", ETEINTE)";
        return "(pos:"+position.toString()+", eau:"+eau_necessaire+")";
    }
}