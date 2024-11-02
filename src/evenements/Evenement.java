package evenements;

public abstract class Evenement {

    private long date;

    public Evenement(long date){
        this.date = date;
    }


    public long getdate () {
        return this.date;
    }

    abstract public void execute();
}
