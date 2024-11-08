package chemins;

import donnees.carte.*;
import donnees.robots.*;
import evenements.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.LinkedList;



public class PlusCoursChemin {
    
    //Renvoi la durée totale d'un chemin
    public static long duree_chemin(LinkedList<CaseDuree> chemin) {

        //Si le chemin n'existe pas
        if (chemin == null) return Long.MAX_VALUE;

        long duree = 0;
        for(CaseDuree caseDuree : chemin) 
            duree += caseDuree.getDuree();
        
        return duree;
    }

    //Renvoi la suite d'evenement que doit effectuer robot pour suivre le chemin chemin
    public static PriorityQueue<Evenement> deplacerRobotChemin(long date_debut, LinkedList<CaseDuree> chemin, Robot robot, Carte carte) {

        PriorityQueue<Evenement> events = new PriorityQueue<>();

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


    //Renvoi le plus cours chemin entre robot.getPosition() et arrivee (null si il n'existe pas)
    public static LinkedList<CaseDuree> dijkstra(Robot robot, Case arrivee, Carte carte) {

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


    private static long[][] initDureeDistanceCases(Carte carte, Case depart) {

        long[][] dureeCasesAccessible = new long[carte.getNbLignes()][carte.getNbLignes()];

        for(int i = 0; i < dureeCasesAccessible.length; i++) 
            for(int j = 0; j < dureeCasesAccessible[i].length; j++) 
                dureeCasesAccessible[i][j] = Long.MAX_VALUE;
            
        dureeCasesAccessible[depart.getLigne()][depart.getColonne()] = 0;
        return dureeCasesAccessible;
    }


    private static boolean[][] initCaseMarquee(Carte carte) {

        boolean[][] caseMarquee = new boolean[carte.getNbLignes()][carte.getNbLignes()];

        for(int i = 0; i < caseMarquee.length; i++) 
            for(int j = 0; j < caseMarquee[i].length; j++) 
            caseMarquee[i][j] = false;

        return caseMarquee;
    }


    //Renvoi la case non marquée de durée minimale dans dureeDistanceCases
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


    //Renvoi les voisins non marqués de src
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


    //Renvoi la duree entre la case depart et sa case voisine arrive pour le robot robot
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


    //Fonction pour ajouter deux durée potentiellement "infinie"
    private static long ajouterDuree(Long duree1, long duree2) {
        if (duree1 == Long.MAX_VALUE || duree2 == Long.MAX_VALUE)
            return Long.MAX_VALUE;
        return duree1+duree2;

    }


    //Reconstruit le plus cours chemin à partir des predecesseurs (le chemin doit exister).
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

    
    //Renvoi la direction de case1 à case2
    private static Direction getDirection(Case case1, Case case2) {
        int diff_x = case2.getColonne() - case1.getColonne();
        int diff_y = case2.getLigne() - case1.getLigne();

        if (diff_x == 0 && diff_y == 1)       return Direction.SUD;
        else if (diff_x == 0 && diff_y == -1) return Direction.NORD;
        else if (diff_y == 0 && diff_x == 1)  return Direction.EST;
        else if (diff_y == 0 && diff_x == -1) return Direction.OUEST;

        throw new Error("Les cases ne sont pas voisines");
    }


    public static String CheminToString(LinkedList<Case> chemin) {
        String s = "";
        for(Case src : chemin)
            s += src.toString() + ", ";
        return s;
    }
}
