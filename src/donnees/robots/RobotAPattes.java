package donnees.robots;

import donnees.carte.*;

public class RobotAPattes extends Robot {


    public static final String lienTexture = "ressources/pattes.png";


    /**
     * Constructeur pour initialiser un robot à pattes avec une position sur la carte.
     * 
     * @param position La position du robot sur la carte (de type Case).
     */
    public RobotAPattes(Case position) {
        super(
            position, //position (Case)
            new double[] {0,30,10,30,30}, //vitesseTerrain (EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT)
            true, //utilisePoudre
            10, //quant_eau_intervention (L)
            1 //duree_intervention (secondes)
        );
    }


    @Override
    public String toString() {
        return "PATTES (pos:"+super.getPosition().toString()+
                        ", vitesse:"+super.getVitesse(NatureTerrain.TERRAIN_LIBRE)+
                ")";
    }


    /**
     * Méthode pour obtenir le chemin vers la texture du drone.
     * 
     * @return Le chemin du fichier de texture du drone.
     */
    @Override
    public String getLienTexture() {
        return lienTexture;
    }
}
