package evenements;

public abstract class Evenement {

    private long date_fin; 

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
