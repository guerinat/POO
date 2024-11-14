package donnees.robots;

import donnees.carte.Case;
import donnees.carte.NatureTerrain;

public class RobotARoues extends Robot {

    public static final String lienTexture = "ressources/roues.png";

/**
 * Constructeur pour initialiser un robot à roues avec une position sur la carte et une vitesse donnée.
 * 
 * @param position La position du robot sur la carte (de type Case).
 * @param vitesse La vitesse du robot sur terrain libre et habitat (en unités adaptées).
 */
    public RobotARoues(Case position, double vitesse) {
        super(
            position, //position (Case)
            new double[]{0,0,0,vitesse,vitesse}, //vitesseTerrain (EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT)
            false, //utilisePoudre
            false, //remplitSurEau
            5000, //quant_reservoire (L)
            5000, //quant_eau (L)
            10*60, //duree_remplissage (secondes)
            100, //quant_eau_intervention (L)
            5 //duree_intervention (secondes)
        );
    }

    public String toString() {
        return "ROUES (pos:"+super.getPosition().toString()+
                        ", vitesse:"+super.getVitesse(NatureTerrain.TERRAIN_LIBRE)+
                        ", eau:"+super.getQuantEau()+"/"+super.getQuantReservoire()+
                ")";
    }

    @Override
    public String getLienTexture() {
        return lienTexture;
    }
}
