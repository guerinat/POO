package donnees.carte;

public class Carte {
    private int tailleCases;
    private int nbLignes;
    private int nbColonnes;
    private Case[][] carte;

    public Carte(int tailleCases, int nbLignes, int nbColonnes) {

        this.tailleCases = tailleCases;
        this.nbLignes = nbLignes ;
        this.nbColonnes = nbColonnes ;
        this.carte = new Case[nbLignes][nbColonnes];

        for (int ligne=0; ligne < nbLignes; ++ligne){
            for (int colonne=0; colonne < nbColonnes; ++colonne){
                this.carte[ligne][colonne] = new Case(ligne, colonne);
            }
        }
    }

    public int getNbLignes () {
        return this.nbLignes;
    }

    public int getNbColonnes () {
        return this.nbColonnes;
    }

    public int getTailleCases() {
        return this.tailleCases;
    }

    public Case getCase(int ligne, int colonne) {
        return this.carte[ligne][colonne];
    }

    public boolean voisinExiste(Case src, Direction dir) {

        switch(dir) {
            case NORD:
                return (src.getLigne() != 0);
            case SUD:
                return (src.getLigne() != this.nbLignes - 1);
            case OUEST:
                return (src.getColonne() != 0);
            case EST:
                return (src.getColonne() != this.nbColonnes - 1);
            default:
                throw new Error("[!] Direction inconnue.");
        }
    }
    
    public Case getVoisin(Case src, Direction dir) {

        if (!voisinExiste(src, dir))
            throw new Error("[!] Le voisin n'existe pas dans cette direction");
        
        switch(dir) {
            case NORD:
                return this.carte[src.getLigne()-1][src.getColonne()];
            case SUD:
                return this.carte[src.getLigne()+1][src.getColonne()];
            case OUEST:
                return this.carte[src.getLigne()][src.getColonne()-1];
            case EST:
                return this.carte[src.getLigne()][src.getColonne()+1];
            default:
                throw new Error("[!] Direction inconnue.");
        }
    }

    @Override
    public String toString () {
        return " La carte" ;
    } 
} 