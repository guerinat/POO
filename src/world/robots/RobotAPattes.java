public class RobotAPattes extends Robots {

    public RobotAPattes(position) {
        super(
            position,                           //position (Case)
            new double{0,30,10,30,30},          //vitesseTerrain (EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT)
            true,                               //utilisePoudre
            false,                              //remplitSurEau
            2000,                               //quant_reservoire (L)
            2000,                               //quant_eau (L)
            5*60,                               //duree_remplissage (secondes)
            100,                                //quant_eau_intervention (L)
            8                                   //duree_intervention (secondes)
        );
    }
}
