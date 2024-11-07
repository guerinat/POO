package chemins;

public class CaseDuree {
    
    private Case src;
    private long duree; //duree pour arriver à src depuis la case precedente

    public CaseDuree(Case src, long duree) {
        this.src = src;
        this.duree = duree;
    }
}
