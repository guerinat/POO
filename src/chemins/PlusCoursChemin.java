package chemins;

import donnees.DonneesSimulation;
import donnees.carte.*;
import donnees.robots.*;
import evenements.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.LinkedList;


/**
 * Classe contenant des méthodes pour calculer le plus court chemin qu'un robot peut emprunter sur une carte,
 * ainsi que des méthodes utilitaires pour générer des événements de déplacement et trouver la source d'eau la plus proche.
 */
public class PlusCoursChemin {
    
    /**
     * Calcule la durée totale d'un chemin donné.
     * 
     * @param chemin une liste de CaseDuree représentant le chemin à suivre
     * @return la durée totale pour parcourir le chemin ou Long.MAX_VALUE si le chemin n'existe pas
     */
    public static long duree_chemin(LinkedList<CaseDuree> chemin) {

        //Si le chemin n'existe pas
        if (chemin == null) return Long.MAX_VALUE;

        long duree = 0;
        for(CaseDuree caseDuree : chemin) 
            duree += caseDuree.getDuree();
        
        return duree;
    }

    /**
     * Génère une séquence d'événements de déplacement pour qu'un robot suive un chemin donné.
     * 
     * @param date_debut la date de début pour le premier événement de déplacement
     * @param chemin la liste de CaseDuree représentant le chemin à suivre
     * @param robot le robot qui se déplace
     * @param carte la carte sur laquelle le robot se déplace
     * @return une liste d'événements que le robot doit effectuer pour suivre le chemin
     */
    public static LinkedList<Evenement> deplacerRobotChemin(long date_debut, LinkedList<CaseDuree> chemin, Robot robot, Carte carte) {

        LinkedList<Evenement> events = new LinkedList<>();

        CaseDuree precedente = chemin.getFirst(); 
        long date = 0; //Date de debut de l'evenements
        
        for (CaseDuree caseDuree : chemin) {

            //Sauter le départ
            if (caseDuree.getSrc().equals(robot.getPosition())) 
                continue;

            //Initialisation de l'evenement
            Evenement event = new Deplacement(date_debut+date, carte, precedente.getSrc(), precedente.getDir(), robot);
            events.add(event);

            //Incrementation
            date += precedente.getDuree();
            precedente = caseDuree;
        }

        return events;

    }


    /**
     * Calcule le plus court chemin entre la position du robot et une case d'arrivée donnée.
     * Utilise l'algorithme de Dijkstra pour trouver le chemin optimal.
     * 
     * @param robot le robot qui effectue le déplacement
     * @param arrivee la case d'arrivée
     * @param carte la carte sur laquelle le robot se déplace
     * @return une liste chaînée de CaseDuree représentant le chemin le plus court ou null si aucun chemin n'existe
     */
    public static LinkedList<CaseDuree> djikstra(Robot robot, Case arrivee, Carte carte) {

        long[][] dureeDistanceCases = initDureeDistanceCases(carte, robot.getPosition());
        boolean[][] casesMarquees = initCaseMarquee(carte);
        Case[][] predecesseurs = new Case[carte.getNbLignes()][carte.getNbColonnes()];

        while(true) { //Tant que arrivée n'est pas atteinte

            //On prend la case de duree minimale non marquée
            Case src = caseNonMarqueeMinDuree(dureeDistanceCases, casesMarquees, carte); 

            //Si on a atteint l'arrivée, le plus cours chemin est trouvé (ou l'absence de chemin)
            if (src.equals(arrivee)) { 

                if (dureeDistanceCases[arrivee.getLigne()][arrivee.getColonne()] == Long.MAX_VALUE)
                    return null; //absence de chemin.
                return reconstruireChemin(predecesseurs, dureeDistanceCases, arrivee, robot.getPosition());
            }

            for(Case voisin : getVoisinsNonMarquee(carte, src, casesMarquees)) { 

                long dureeSource = dureeDistanceCases[src.getLigne()][src.getColonne()];
                long dureeVoisin = dureeDistanceCases[voisin.getLigne()][voisin.getColonne()];
                long dureeEntreCases = getDuree(src, voisin, robot, carte); //duree entre Src et Voisin

                //Si un chemin plus cours est trouvé
                if (dureeVoisin > ajouterDuree(dureeSource, dureeEntreCases)) { 
                    dureeDistanceCases[voisin.getLigne()][voisin.getColonne()] = dureeSource + dureeEntreCases;
                    predecesseurs[voisin.getLigne()][voisin.getColonne()] = src;
                }
            }
            casesMarquees[src.getLigne()][src.getColonne()] = true; //On marque la case source
        }

    }

    /**
     * Initialise la matrice de distances avec des valeurs infinies sauf pour la case de départ.
     * 
     * @param carte la carte sur laquelle se déplacent les robots
     * @param depart la case de départ
     * @return une matrice contenant les distances initialisées
     */
    private static long[][] initDureeDistanceCases(Carte carte, Case depart) {

        long[][] dureeCasesAccessible = new long[carte.getNbLignes()][carte.getNbLignes()];

        for(int i = 0; i < dureeCasesAccessible.length; i++) 
            for(int j = 0; j < dureeCasesAccessible[i].length; j++) 
                dureeCasesAccessible[i][j] = Long.MAX_VALUE;
            
        dureeCasesAccessible[depart.getLigne()][depart.getColonne()] = 0;
        return dureeCasesAccessible;
    }

    /**
     * Initialise un tableau indiquant si chaque case a été visitée.
     * 
     * @param carte la carte sur laquelle se déplacent les robots
     * @return un tableau de booléens initialisés à false
     */
    private static boolean[][] initCaseMarquee(Carte carte) {

        boolean[][] caseMarquee = new boolean[carte.getNbLignes()][carte.getNbLignes()];

        for(int i = 0; i < caseMarquee.length; i++) 
            for(int j = 0; j < caseMarquee[i].length; j++) 
            caseMarquee[i][j] = false;

        return caseMarquee;
    }


    
    /**
     * Trouve la case non marquée ayant la durée minimale dans la matrice des distances.
     * 
     * @param dureeDistanceCases la matrice des durées
     * @param casesMarquees tableau indiquant si une case a été visitée
     * @param carte la carte sur laquelle se déplacent les robots
     * @return la case non marquée avec la durée minimale
     */
    private static Case caseNonMarqueeMinDuree(long[][] dureeDistanceCases, boolean[][] casesMarquees, Carte carte) {

        long minDuree = Long.MAX_VALUE;
        Case nonMarqueeMinDuree = null;

        for(int i = 0; i < dureeDistanceCases.length; i++) {
            for(int j = 0; j < dureeDistanceCases[i].length; j++) {

                if (casesMarquees[i][j]) continue;

                if (dureeDistanceCases[i][j] <= minDuree) {
                    minDuree = dureeDistanceCases[i][j];
                    nonMarqueeMinDuree = carte.getCase(i, j);
                }
            }
        }
        return nonMarqueeMinDuree;
    }


    /**
     * Obtient les cases voisines non marquées d'une case source donnée.
     * 
     * @param carte la carte sur laquelle se déplacent les robots
     * @param src la case source
     * @param casesMarquees tableau indiquant si une case a été visitée
     * @return une liste de cases voisines non marquées
     */
    private static ArrayList<Case> getVoisinsNonMarquee(Carte carte, Case src, boolean[][] casesMarquees) {

        ArrayList<Case> casesVoisines = new ArrayList<>();

        for(Direction dir : Direction.values()) {

            if (carte.voisinExiste(src, dir)) {

                Case voisin = carte.getVoisin(src, dir);

                if (casesMarquees[voisin.getLigne()][voisin.getColonne()])
                    continue;

                casesVoisines.add(voisin);
            }
        }

        return casesVoisines;
    }


    /**
     * Calcule la durée de déplacement entre deux cases pour un robot donné.
     * 
     * @param depart la case de départ
     * @param arrive la case d'arrivée
     * @param robot le robot qui effectue le déplacement
     * @param carte la carte sur laquelle se déplacent les robots
     * @return la durée de déplacement ou Long.MAX_VALUE si le déplacement est impossible
     */
    public static long getDuree(Case depart, Case arrive, Robot robot, Carte carte) {

        NatureTerrain nature_depart = depart.getNature();
        NatureTerrain nature_arrive = arrive.getNature();

        double vitesse_depart = robot.getVitesse(nature_depart);
        double vitesse_arrive = robot.getVitesse(nature_arrive);

        if (vitesse_depart == 0 || vitesse_arrive == 0) 
            return Long.MAX_VALUE; 

        double vitesse_moyenne = (vitesse_depart + vitesse_arrive)/2;

        long duree = (long)(3.6*carte.getTailleCases()/vitesse_moyenne); 

        return duree;
    }


    /**
     * Ajoute deux durées, en prenant en compte les valeurs infinies.
     * 
     * @param duree1 la première durée
     * @param duree2 la seconde durée
     * @return la somme des deux durées, ou Long.MAX_VALUE si l'une d'elles est infinie
     */
    private static long ajouterDuree(Long duree1, long duree2) {
        if (duree1 == Long.MAX_VALUE || duree2 == Long.MAX_VALUE)
            return Long.MAX_VALUE;
        return duree1+duree2;

    }


    /**
     * Reconstruit le chemin le plus court en partant de la case d'arrivée jusqu'à la case de départ.
     * Utilise les prédécesseurs de chaque case pour remonter le chemin trouvé par Dijkstra.
     * 
     * @param predecesseurs tableau des cases prédécesseurs généré par Dijkstra
     * @param dureeDistanceCases matrice des durées pour atteindre chaque case
     * @param arrivee la case de destination
     * @param depart la case de départ
     * @return la liste chaînée de CaseDuree représentant le chemin le plus court
     */
    private static LinkedList<CaseDuree> reconstruireChemin(Case[][] predecesseurs, long[][] dureeDistanceCases, Case arrivee, Case depart) {

        LinkedList<CaseDuree> chemin = new LinkedList<>();

        Case actuelle = arrivee;
        Case precedente = predecesseurs[actuelle.getLigne()][actuelle.getColonne()];

        //Duree et direction de la prochaine case de precedente
        long duree = 0;
        Direction dir = null;

        while (!actuelle.equals(depart)) { //Tant que départ n'est pas atteint

            chemin.addFirst(new CaseDuree(actuelle, duree, dir)); 

            duree = dureeDistanceCases[actuelle.getLigne()][actuelle.getColonne()] - dureeDistanceCases[precedente.getLigne()][precedente.getColonne()];
            dir = getDirection(precedente, actuelle);

            actuelle = precedente;
            precedente = predecesseurs[precedente.getLigne()][precedente.getColonne()];
        }
        
        chemin.addFirst(new CaseDuree(actuelle, duree, dir));
        return chemin;
    }

    
    /**
     * Détermine la direction entre deux cases voisines.
     * 
     * @param case1 la première case (point de départ)
     * @param case2 la seconde case (destination)
     * @return la direction de case1 à case2
     * @throws Error si les cases ne sont pas adjacentes
     */
    private static Direction getDirection(Case case1, Case case2) {
        int diff_x = case2.getColonne() - case1.getColonne();
        int diff_y = case2.getLigne() - case1.getLigne();

        if (diff_x == 0 && diff_y == 1)       return Direction.SUD;
        else if (diff_x == 0 && diff_y == -1) return Direction.NORD;
        else if (diff_y == 0 && diff_x == 1)  return Direction.EST;
        else if (diff_y == 0 && diff_x == -1) return Direction.OUEST;

        throw new Error("Les cases ne sont pas voisines");
    }


    /**
     * Convertit une liste de cases en chaîne de caractères pour l'affichage du chemin.
     * 
     * @param chemin la liste chaînée de cases représentant le chemin
     * @return une chaîne de caractères listant les cases du chemin
     */
    public static String CheminToString(LinkedList<Case> chemin) {
        String s = "";
        for(Case src : chemin)
            s += src.toString() + ", ";
        return s;
    }

    /**
     * Trouve la case d'eau la plus proche de la position d'un robot, basée sur le plus court chemin.
     * 
     * @param data les données de simulation contenant la carte et les informations du terrain
     * @param robot le robot cherchant une source d'eau
     * @return la case contenant de l'eau la plus proche du robot
     * @throws Error si aucune source d'eau n'est disponible
     */
    public static Case chercherPlusProcheEau(Carte carte, Robot robot){

        Case plusProcheEau = null;
        long minDuree = Long.MAX_VALUE;

        for (int ligne = 0; ligne < carte.getNbLignes(); ligne++) {
            for (int colonne = 0; colonne < carte.getNbColonnes(); colonne++) {

                Case src = carte.getCase(ligne,colonne);

                if (src.getNature() != NatureTerrain.EAU)
                    continue;
                    
                LinkedList<CaseDuree> chemin = djikstra(robot, src, carte);
                long dureeChemin = duree_chemin(chemin);

                if (dureeChemin >= minDuree)
                    continue;

                minDuree = dureeChemin;
                plusProcheEau = carte.getCase(ligne, colonne);
            }
        }
        return plusProcheEau;
    }
}
