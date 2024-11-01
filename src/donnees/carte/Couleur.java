package donnees.carte;

public enum Couleur {
    Bleu(0, 0, 255), 
    Vertf(0,100,0), 
    Gris(128,128,128), 
    Vertc(0,255,0), 
    Jaune(255,255,0),
    Rouge(255,0,0),
    Blanc(255,255,255),
    Noir(0,0,0);

    private final int r;
    private final int g;
    private final int b;

    // Constructeur de l'énumération
    Couleur(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public java.awt.Color toAwtColor() {
        return new java.awt.Color(r, g, b);
    }
};