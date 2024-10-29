public class Case {
    private int ligne ;
    private int colonne ;
    private NatureTerrain nature_terrain;
    
    public Case (int ligne, int colonne){
        this.ligne = ligne ;
        this.colonne = colonne ;
    }

    public Case (int ligne, int colonne, String nature_terrain) {
        this.ligne = ligne ;
        this.colonne = colonne ;
        this.nature_terrain = nature_terrain;
    }

    public int getLigne () {
        return ligne;
    }

    public int getColonne () {
        return colonne;
    }

    public NatureTerrain getNature () {
        return this.nature_terrain;
    }


    @Override
    public String toString () {
        return " La case de ligne " + ligne + " et de colonne " + colonne + "est constituée de : " + NatureTerrain ;
    }
} 
