package chemins;

import donnees.carte.*;

public class CaseDuree {
    
    private Case src;
    private long duree; //duree pour aller à la prochaine case (0 si elle n'existe pas)
    Direction dir; //directon de la prochaine case (null si elle n'existe pas)

/**
 * Constructeur pour créer une instance de CaseDuree.
 * 
 * @param src la case concernée
 * @param duree la durée pour atteindre la prochaine case (0 si elle n'existe pas)
 * @param dir la direction de la prochaine case (null si elle n'existe pas)
 */
    public CaseDuree(Case src, long duree, Direction dir) {
        this.src = src;
        this.duree = duree;
        this.dir = dir;
    }

/**
 * Obtient la case source.
 * 
 * @return la case source
 */
    public Case getSrc() {
        return src;
    }

/**
 * Obtient la durée pour atteindre la case adjacente.
 * 
 * @return la durée pour atteindre la prochaine case ou 0 si elle n'existe pas
 */
    public long getDuree() {
        return duree;
    }

/**
 * Obtient la direction de la case adjacente.
 * 
 * @return la direction vers la prochaine case ou null si elle n'existe pas
 */
    public Direction getDir() {
        return dir;
    }
}
