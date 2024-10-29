public class Case {
    private int ligne ;
    private int colonne ;
    private enum NatureTerrain {Eau, Forêt, Roche, Terrain_Libre, Habitat};
    
    public Case (int ligne, int colonne){
        this.ligne = ligne ;
        this.colonne = colonne ;
    }

    public CaseN (int ligne, int colonne, String nature) {
        this.ligne = ligne ;
        this.colonne = colonne ;
        this.NatureTerrain = nature;
    }

    public int getLigne () {
        return ligne;
    }

    public int getColonne () {
        return colonne;
    }

    public int getNature () {
        return NatureTerrain;
    }

    @Override
    public String toString () {
        return " La case de ligne " + ligne + " et de colonne " + colonne + "est constituée de : " + NatureTerrain ;
    }
} 
