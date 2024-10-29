public class RobotARoue extends Robot {

    public RobotARoue(Case position) {
        super(
            position, //position (Case)
            new double[]{0,0,0,100,100}, //vitesseTerrain (EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT)
            false, //utilisePoudre
            false, //remplitSurEau
            5000, //quant_reservoire (L)
            5000, //quant_eau (L)
            10*60, //duree_remplissage (secondes)
            100, //quant_eau_intervention (L)
            5 //duree_intervention (secondes)
        );
    }
}
