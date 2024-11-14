package donnees.carte;

public class Carte {
    private int tailleCases;
    private int nbLignes;
    private int nbColonnes;
    private Case[][] carte;

/**
 * Constructeur de la classe Carte.
 * Initialise la carte avec les dimensions spécifiées.
 * 
 * @param tailleCases la taille d'une case
 * @param nbLignes le nombre de lignes de la carte
 * @param nbColonnes le nombre de colonnes de la carte
 */
    public Carte(int tailleCases, int nbLignes, int nbColonnes) {

        this.tailleCases = tailleCases;
        this.nbLignes = nbLignes ;
        this.nbColonnes = nbColonnes ;
        this.carte = new Case[nbLignes][nbColonnes];
    }

/**
 * Retourne le nombre de lignes dans la carte.
 * 
 * @return le nombre de lignes
 */
    public int getNbLignes () {
        return this.nbLignes;
    }

/**
 * Retourne le nombre de colonnes dans la carte.
 * 
 * @return le nombre de colonnes
 */
    public int getNbColonnes () {
        return this.nbColonnes;
    }

/**
 * Retourne la taille d'une case.
 * 
 * @return la taille d'une case
 */
    public int getTailleCases() {
        return this.tailleCases;
    }

/**
 * Retourne la case située aux coordonnées spécifiées.
 * 
 * @param ligne l'index de la ligne
 * @param colonne l'index de la colonne
 * @return la case aux coordonnées spécifiées
 */
    public Case getCase(int ligne, int colonne) {
        return this.carte[ligne][colonne];
    }

/**
 * Définit une case à une position spécifique dans la carte.
 * 
 * @param src la case à placer
 * @param ligne l'index de la ligne
 * @param colonne l'index de la colonne
 * @return la case nouvellement placée
 */
    public Case setCase(Case src, int ligne, int colonne) {
        return this.carte[ligne][colonne] = src;
    }

/**
 * Vérifie si une case voisine existe dans une direction spécifiée.
 * 
 * @param src la case de référence
 * @param dir la direction du voisin recherché
 * @return vrai si le voisin existe, faux sinon
 */
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
    
/**
 * Retourne la case voisine dans une direction spécifiée.
 * 
 * @param src la case de référence
 * @param dir la direction du voisin
 * @return la case voisine dans la direction donnée
 * @throws Error si le voisin n'existe pas dans cette direction
 */
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

/**
 * Génère une représentation de la carte en chaîne de caractères.
 * 
 * @return une chaîne de caractères représentant la carte
 */
    @Override
    public String toString () {
        String s = "# CARTE ("+nbLignes+", "+nbColonnes+", taille:"+tailleCases+")\n";
        for(int i = 0; i < this.nbLignes; i++) {
            for(int j = 0; j < this.nbColonnes; j++) {
                s += this.carte[i][j].toString() + "  ";
            }
            s+="\n";
        }
        return s;
    } 
} 