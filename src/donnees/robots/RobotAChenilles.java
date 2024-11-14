package donnees.robots;

import donnees.carte.*;

public class RobotAChenilles extends Robot {

    public static final String lienTexture = "ressources/chenilles.png";

/**
 * Constructeur pour initialiser un robot à chenilles avec une position et une vitesse.
 * 
 * @param position La position du robot sur la carte (de type Case).
 * @param vitesse La vitesse du robot sur les terrains (en m/s).
 */
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

/**
 * Méthode pour obtenir une représentation sous forme de chaîne de caractères du robot à chenilles.
 * 
 * @return Une chaîne de caractères représentant l'état du robot, incluant sa position, sa vitesse, 
 *         et l'eau restante dans son réservoir.
 */
    @Override
    public String toString() {
        return "CHENILLES (pos:"+super.getPosition().toString()+
                        ", vitesse:"+super.getVitesse(NatureTerrain.TERRAIN_LIBRE)+
                        ", eau:"+super.getQuantEau()+"/"+super.getQuantReservoire()+
                ")";
    }

/**
 * Méthode pour obtenir le lien vers la texture du robot.
 * 
 * @return Le lien sous forme de chaîne de caractères vers la texture du robot (fichier image).
 */
    @Override
    public String getLienTexture() {
        return lienTexture;
    }
}
