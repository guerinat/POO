package donnees.carte;

public class Case {
    private int ligne ;
    private int colonne ;
    private NatureTerrain nature_terrain;
    
    public Case (int ligne, int colonne){
        this.ligne = ligne ;
        this.colonne = colonne ;
    }

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
        return " La case de ligne " + this.ligne + " et de colonne " + this.colonne + "est constituée de : " + this.nature_terrain ;
    }
} 
