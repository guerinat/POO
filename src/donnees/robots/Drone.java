package donnees.robots;

import donnees.carte.Case;
import donnees.carte.NatureTerrain;

public class Drone extends Robot {

    public static final String lienTexture = "ressources/drone.png";

/**
 * Constructeur du Drone.
 * Initialise un drone avec une position, une vitesse, et des caractéristiques spécifiques de comportement.
 * 
 * @param position La position initiale du drone (de type Case).
 * @param vitesse La vitesse du drone sur tous les types de terrain (en km/h).
 */
    public Drone(Case position, double vitesse) {

        super(
            position, //position (Case)

            //vitesseTerrain (EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT)
            new double[]{vitesse,vitesse,vitesse,vitesse,vitesse}, 
            
            false, //utilisePoudre
            true, //remplitSurEau
            10000, //quant_reservoire (L)
            10000, //quant_eau (L)
            30*60, //duree_remplissage (secondes)
            10000, //quant_eau_intervention (L)
            30 //duree_intervention (secondes)
        );
    }


    @Override
    public String toString() {
        return "DRONE (pos:"+super.getPosition().toString()+
                        ", vitesse:"+super.getVitesse(NatureTerrain.TERRAIN_LIBRE)+
                        ", eau:"+super.getQuantEau()+"/"+super.getQuantReservoire()+
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
