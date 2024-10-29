public class RobotAChenille extends Robot {

    public RobotAChenille(Case position) {
        super(
            position, //position (Case)
            new double[]{0,30,0,60,60}, //vitesseTerrain (EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT)
            false, //utilisePoudre
            false, //remplitSurEau
            2000, //quant_reservoire (L)
            2000, //quant_eau (L)
            5*60, //duree_remplissage (secondes)
            100, //quant_eau_intervention (L)
            8 //duree_intervention (secondes)
        );
    }
}
