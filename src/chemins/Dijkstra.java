package chemins;

import donnees.robots.*;

import java.util.ArrayList;

import donnees.*;
import donnees.carte.*;
import java.util.ArrayList;

public class Dijkstra {
    


    public long tempsArriverA(Robot robot, Case arrive, Carte carte) {
;
        long[][] dureeDistanceCases = initDureeDistanceCases(carte, robot.getPosition());
        boolean[][] casesMarquees = initCaseMarquee(carte);

        int nbCasesMarquees = 0;

        while(nbCasesMarquees != carte.getNbColonnes()*carte.getNbLignes()) { //Tant que toute les cases ne sont pas marquée

            Case src = caseNonMarqueeMinDuree(dureeDistanceCases, casesMarquees, carte); //On prend la case de duree minimale non marquée

            for(Case voisin : getVoisinsNonMarquee(carte, src, casesMarquees)) { 

                long dureeSource = dureeDistanceCases[src.getLigne()][src.getColonne()];
                long dureeVoisin = dureeDistanceCases[voisin.getLigne()][voisin.getColonne()];
                long dureeEntreCases = getDuree(src, voisin, robot, carte); //duree entre Src et Voisin

                if (dureeVoisin > ajouterDuree(dureeSource, dureeEntreCases)) {
                    dureeDistanceCases[voisin.getLigne()][voisin.getColonne()] = dureeSource + dureeEntreCases;
                }
            }

            casesMarquees[src.getLigne()][src.getColonne()] = true; // On marque la case source
            nbCasesMarquees++;
        }

    }


    private long[][] initDureeDistanceCases(Carte carte, Case depart) {

        long[][] dureeCasesAccessible = new long[carte.getNbLignes()][carte.getNbLignes()];

        for(int i = 0; i < dureeCasesAccessible.length; i++) 
            for(int j = 0; j < dureeCasesAccessible[i].length; j++) 
                dureeCasesAccessible[i][j] = Long.MAX_VALUE;
            
        dureeCasesAccessible[depart.getLigne()][depart.getColonne()] = 0;
        return dureeCasesAccessible;
    }


    private boolean[][] initCaseMarquee(Carte carte) {

        boolean[][] caseMarquee = new boolean[carte.getNbLignes()][carte.getNbLignes()];

        for(int i = 0; i < caseMarquee.length; i++) 
            for(int j = 0; j < caseMarquee[i].length; j++) 
            caseMarquee[i][j] = false;

        return caseMarquee;
    }


    //Renvoi la case non marquée de durée minimale dans dureeDistanceCases
    private Case caseNonMarqueeMinDuree(long[][] dureeDistanceCases, boolean[][] casesMarquees, Carte carte) {

        long minDuree = Long.MAX_VALUE;
        Case nonMarqueeMinDuree = null;

        for(int i = 0; i < dureeDistanceCases.length; i++) {
            for(int j = 0; j < dureeDistanceCases[i].length; j++) {

                if (casesMarquees[i][j]) continue;

                if (dureeDistanceCases[i][j] < minDuree) {
                    minDuree = dureeDistanceCases[i][j];
                    nonMarqueeMinDuree = carte.getCase(i, j);
                }
            }
        }
        return nonMarqueeMinDuree;
    }


    //Renvoi les voisins non marqués de src
    private ArrayList<Case> getVoisinsNonMarquee(Carte carte, Case src, boolean[][] casesMarquees) {

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
    long getDuree(Case depart, Case arrive, Robot robot, Carte carte) {

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
    private long ajouterDuree(Long duree1, long duree2) {
        if (duree1 == Long.MAX_VALUE || duree2 == Long.MAX_VALUE)
            return Long.MAX_VALUE;
        return duree1+duree2;

    }
}
