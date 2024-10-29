public class Carte {
    private int tailleCases;
    private int nbLignes;
    private int nbColonnes;
    private Case[nbLignes][nbColonnes] carte;

    public Carte (int tailleCases, int nbLignes, int nbColonnes) {
        this.tailleCases = taillesCases;
        this.nbColonnes = nbColonnes ;
        this.nbLignes = nbLignes ;
        for (int ligne=0, ligne<nbLignes, ++ligne){
            for (int colonne=0, colonne<nbColonnes, ++colonne){
                this.carte[ligne][colonne] = new Case(ligne,colonne);
            }
        }
    }

    public int getnbColonnes () {
        return nbColonne;
    }

    public int getnbLignes () {
        return nbLignes;
    }

    public int TailleCases() {
        return tailleCases;
    }

    public Case getCase (int ligne, int colonne)
    

    @Override
    public String toString () {
        return " La carte" ;
    } 
} 