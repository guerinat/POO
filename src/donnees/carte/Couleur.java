package donnees.carte;

public enum Couleur {
    Bleu(0, 0, 255), 
    Vertf(0,100,0), 
    Gris(128,128,128), 
    Vertc(0,255,0), 
    Jaune(255,255,0),
    Rouge(255,0,0),
    Blanc(255,255,255);

    public java.awt.Color value;

    // Constructeur de l'énumération
    Couleur(int r, int g, int b) {
        this.value = new java.awt.Color(r, g, b);
    }
};