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

/**
 * Retourne la position de la case sur la ligne.
 *
 * @return l'index de la ligne
 */
    public int getLigne () {
        return this.ligne;
    }

/**
 * Retourne la position de la case sur la colonne.
 *
 * @return l'index de la colonne
 */
    public int getColonne () {
        return this.colonne;
    }

/**
 * Retourne le type de terrain de la case.
 *
 * @return la nature du terrain de la case
 */
    public NatureTerrain getNature () {
        return this.nature_terrain;
    }

/**
 * Vérifie si deux cases sont égales.
 * Deux cases sont égales si elles ont la même position (ligne, colonne) et le même type de terrain.
 *
 * @param obj l'objet à comparer avec cette case
 * @return vrai si les cases sont identiques, faux sinon
 */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Case)) return false;
        Case src = (Case) obj;
        return this.colonne == src.getColonne() && this.ligne == src.getLigne() && this.nature_terrain == src.getNature();
    }


/**
 * Retourne une représentation sous forme de chaîne de caractères de la case.
 * Format : "(ligne, colonne, nature)"
 * La nature du terrain est abrégée aux trois premières lettres.
 *
 * @return une chaîne représentant la case
 */
    @Override
    public String toString () {
        return "(" + this.ligne + ", " + this.colonne + ", " + this.nature_terrain.toString().substring(0, 3) + ")" ;
    }
} 
