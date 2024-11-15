package donnees.carte;

public enum NatureTerrain {

    EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT;


    private static String[] textures = {
        "ressources/eau.png", 
        "ressources/foret.png",
        "ressources/roche.png",
        "ressources/terrain_libre.png",
        "ressources/habitat.png"
    }; 

    
    /**
     * @return Le chemin de la texture du terrain sous forme de chaîne de caractères.
     */
    public String getLienTexture() {
        return textures[this.ordinal()];
    }
};

