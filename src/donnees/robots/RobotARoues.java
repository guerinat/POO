package donnees.robots;

import donnees.carte.Case;
import donnees.carte.NatureTerrain;

public class RobotARoues extends Robot {

    public static final String lienTexture = "ressources/roues.png";

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
