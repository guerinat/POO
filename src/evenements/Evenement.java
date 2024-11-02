package evenements;

public abstract class Evenement {

    private long date_debut;
    private long date_fin; 

    public Evenement(long date_debut, long date_fin){
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public long getdateFin() {
        return this.date_fin;
    }

    public long getdateDebut() {
        return this.date_debut;
    }

    public long getDuree() {
        return this.date_fin - this.date_debut;
    }


    abstract public void execute();

    @Override
    public String toString() {
        return "("+this.getClass().toString()+", date_fin:"+this.date_fin+")";
    }
}
