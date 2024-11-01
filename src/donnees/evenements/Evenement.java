package donnees.evenements;

public abstract class Evenement{

    private long date;

    public Evenement(long date){
        this.date = date;
    }


    public long getdate () {
        return this.date;
    }

    public void execute(){

    }


}