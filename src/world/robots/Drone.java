public class Drone extends Robot {

    public Drone(Case position) {
        super(
            position, //position (Case)
            new double[]{100,100,100,100,100}, //vitesseTerrain (EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT)
            false, //utilisePoudre
            true, //remplitSurEau
            10000, //quant_reservoire (L)
            10000, //quant_eau (L)
            30*60, //duree_remplissage (secondes)
            10000, //quant_eau_intervention (L)
            30 //duree_intervention (secondes)
        );
    }

    
}
