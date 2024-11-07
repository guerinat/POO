package pluscourschemin;

import donnees.carte.*;
import donnees.robots.*;
import java.util.ArrayList;
import java.util.LinkedList;



public class PlusCoursChemin {
    

    // public static long duree_chemin(Robot robot, Case arrive, Carte carte) {

    //     LinkedList<Case> chemin = dijkstra(robot, arrive, carte);
    //     if (chemin == null) return Long.MAX_VALUE;

    //     ListIterator<Case> iterateur = chemin.listIterator();

    //     Case actuel = iterateur.next();
    //     Case predecesseur = null;
    //     long duree = 0;

    //     while(iterateur.hasNext()) {
        
    //         if (predecesseur != null) 
    //             duree += getDuree(predecesseur, actuel, robot, carte);

    //         predecesseur = actuel;
    //         actuel = iterateur.next();
    //     }

    //     return duree;
    // }


    // public static Evenement[] deplacerRobotChemin(long date_debut, Robot robot, Case arrive, Carte carte) {

    //     LinkedList<Case> chemin = dijkstra(robot, arrive, carte);
    //     ArrayList<Evenement> events = new ArrayList<>();

    //     ListIterator<Case> iterateur = chemin.listIterator();

    //     while(iterateur.hasNext()) {
    //         Case src = iterateur.next();
    //         long duree = getDuree(src, arrive, robot, carte)
    //         Deplacement e = new Deplacement(date_debut, carte, arrivee, null, robot)
    //         events.add(e)
    //     }

    // }







    //Renvoi le plus cours chemin entre robot.getPosition() et arrivee (null si il n'existe pas)
    public static LinkedList<Case> dijkstra(Robot robot, Case arrivee, Carte carte) {

        long[][] dureeDistanceCases = initDureeDistanceCases(carte, robot.getPosition());
        boolean[][] casesMarquees = initCaseMarquee(carte);
        Case[][] predecesseurs = new Case[carte.getNbLignes()][carte.getNbColonnes()];

        while(true) { //Tant que arrivée n'est pas atteinte

            //On prend la case de duree minimale non marquée
            Case src = caseNonMarqueeMinDuree(dureeDistanceCases, casesMarquees, carte); 

            //Si on a atteint l'arrivée, le plus cours chemin est trouvé (ou l'absence de chemin)
            if (src.equals(arrivee)) { 
                if (dureeDistanceCases[arrivee.getLigne()][arrivee.getColonne()] == Long.MAX_VALUE) return null;
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


    //Reconstruit le plus cours chemin à partir des predecesseurs.
    private static LinkedList<Case> reconstruireChemin(Case[][] predecesseurs, long[][] dureeDistanceCases, Case arrivee, Case depart) {

        LinkedList<Case> chemin = new LinkedList<>();

        Case current = arrivee;
        while (current != null && !current.equals(depart)) {
            chemin.addFirst(current); 
            current = predecesseurs[current.getLigne()][current.getColonne()]; 
        }

        chemin.addFirst(depart);
        return chemin;
    }


    public static String CheminToString(LinkedList<Case> chemin) {
        String s = "";
        for(Case src : chemin)
            s += src.toString() + ", ";
        return s;
    }
}
