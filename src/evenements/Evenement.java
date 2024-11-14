package evenements;

import java.lang.Long;

public abstract class Evenement {

    private long date_fin; 

/**
 * Constructeur pour initialiser un événement avec une date de fin donnée.
 * 
 * @param date_fin La date de fin de l'événement, c'est-à-dire le moment où l'événement doit se terminer.
 */
    public Evenement(long date_fin){
        this.date_fin = date_fin;
    }

/**
 * Retourne la date de fin de l'événement.
 * 
 * @return La date de fin de l'événement en millisecondes (ou une autre unité de temps utilisée dans le système).
 */
    public long getdateFin() {
        return this.date_fin;
    }

    abstract public void execute();

/**
 * Retourne une chaîne représentant l'événement. Cette méthode fournit une représentation lisible de
 * l'événement sous la forme de son nom de classe et de sa date de fin.
 * 
 * @return Une chaîne qui décrit l'événement sous la forme "(NomDeClasse, date_fin:[date_fin])".
 */
    @Override
    public String toString() {
        return "("+this.getClass().toString()+", date_fin:"+this.date_fin+")";
    }

    
}
