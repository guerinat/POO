package donnees.carte;

public class Case {
    private int ligne ;
    private int colonne ;
    private NatureTerrain nature_terrain;

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


    @Override
    public String toString () {
        return "(" + this.ligne + ", " + this.colonne + ", " + this.nature_terrain.toString().substring(0, 3) + ")" ;
    }
} 
