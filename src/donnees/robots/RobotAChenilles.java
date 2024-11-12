package donnees.robots;

import donnees.carte.*;

public class RobotAChenilles extends Robot {

    public static final String lienTexture = "ressources/chenilles.png";

    public RobotAChenilles(Case position, double vitesse) {
        super(
            position, //position (Case)

            //vitesseTerrain (EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT)
            new double[]{0, vitesse/2, 0, vitesse, vitesse},
            
            false, //utilisePoudre
            false, //remplitSurEau
            2000, //quant_reservoire (L)
            2000, //quant_eau (L)
            5*60, //duree_remplissage (secondes)
            100, //quant_eau_intervention (L)
            8 //duree_intervention (secondes)
        );   
    }

    @Override
    public String toString() {
        return "CHENILLES (pos:"+super.getPosition().toString()+
                        ", vitesse:"+super.getVitesse(NatureTerrain.TERRAIN_LIBRE)+
                        ", eau:"+super.getQuantEau()+"/"+super.getQuantReservoire()+
                ")";
    }

    @Override
    public String getLienTexture() {
        return lienTexture;
    }
}
