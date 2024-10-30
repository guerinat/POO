package donnees;
import donnees.carte.*;

public class Incendie {
    private Case position;
    private int eau_necessaire;

    public Incendie(Case position, int eau_necessaire) {
        this.position = position;
        this.eau_necessaire = eau_necessaire;
    }

    public Case getPosition() {
        return this.position;
    }

    public int getEauNecessaire() {
        return this.eau_necessaire;
    }

    @Override
    public String toString() {
        return "(pos:"+position.toString()+", eau:"+eau_necessaire+")";
    }
}