package donnees.carte;

public class Case {

    
    private int ligne ;
    private int colonne ;
    private NatureTerrain nature_terrain;

/**
 * Constructeur de la classe Case.
 * Initialise une case avec sa position et son type de terrain.
 *
 * @param ligne position de la case dans la ligne
 * @param colonne position de la case dans la colonne
 * @param nature_terrain nature du terrain de la case
 */
    public Case (int ligne, int colonne, NatureTerrain nature_terrain) {
        this.ligne = ligne ;
        this.colonne = colonne ;
        this.nature_terrain = nature_terrain;
    }


    public int getLigne () {
        return this.ligne;
    }


    public int getColonne () {
        return this.colonne;
    }


    public NatureTerrain getNature () {
        return this.nature_terrain;
    }

/**
 * Vérifie si deux cases sont égales.
 * @param obj l'objet à comparer avec cette case
 * @return vrai si les cases sont identiques, faux sinon
 */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Case)) return false;
        Case src = (Case) obj;
        return this.colonne == src.getColonne() && this.ligne == src.getLigne() && this.nature_terrain == src.getNature();
    }


    @Override
    public String toString () {
        return "(" + this.ligne + ", " + this.colonne + ", " + this.nature_terrain.toString().substring(0, 3) + ")" ;
    }
} 
