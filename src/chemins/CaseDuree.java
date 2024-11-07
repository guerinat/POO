package chemins;

import donnees.carte.*;

public class CaseDuree {
    
    private Case src;
    private long duree; //duree pour aller à la prochaine case (0 si elle n'existe pas)
    Direction dir; //directon de la prochaine case (null si elle n'existe pas)

    public CaseDuree(Case src, long duree, Direction dir) {
        this.src = src;
        this.duree = duree;
        this.dir = dir;
    }

    public Case getSrc() {
        return src;
    }

    public long getDuree() {
        return duree;
    }

    public Direction getDir() {
        return dir;
    }
}
