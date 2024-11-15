package evenements;

public abstract class Evenement {

    private long date_fin; 

    /**
     * Constructeur pour initialiser un événement avec une date de fin donnée.
     * 
     * @param date_fin La date de fin (d'éxécution) de l'événement.
     */
    public Evenement(long date_fin){
        this.date_fin = date_fin;
    }


    public long getdateFin() {
        return this.date_fin;
    }


    abstract public void execute();

    
    @Override
    public String toString() {
        return "("+this.getClass().toString()+", date_fin:"+this.date_fin+")";
    }
}
