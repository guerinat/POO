package strategie;

import java.util.HashSet;
import java.util.LinkedList;

import chemins.CaseDuree;
import chemins.PlusCoursChemin;
import donnees.DonneesSimulation;
import donnees.Incendie;
import donnees.robots.Robot;
import evenements.*;
import simulateur.*;
import donnees.carte.*;


public abstract class ChefPompier {

    Simulateur simulateur;
    HashSet<Incendie> incendiesAffectes;


    public ChefPompier() {
        this.incendiesAffectes = new HashSet<>();
    }


    public void setSimulateur(Simulateur simulateur) {
        this.simulateur = simulateur;
    }


    public void envoyerRobotEteindreIncendie(Robot robot, Incendie incendie, LinkedList<CaseDuree> chemin, long date_courante, Carte carte) {

        //Deplacement
        LinkedList<Evenement> deplacements = PlusCoursChemin.deplacerRobotChemin(date_courante, chemin, robot, carte);
        long dateApresChemin = date_courante + PlusCoursChemin.duree_chemin(chemin);
        simulateur.ajouteEvenements(deplacements);

        //Vidage
        LinkedList<Evenement> vidages = Vidage.viderEntierementRobot(robot, incendie, dateApresChemin);
        long dureeApresVidage = dateApresChemin + vidages.size()*Vidage.calcDuree(robot);
        simulateur.ajouteEvenements(vidages);

        //Changements des etats
        simulateur.ajouteEvenement(new ChangerEtat(date_courante, robot, Etat.DEPLACEMENT, incendie));
        simulateur.ajouteEvenement(new ChangerEtat(dateApresChemin, robot, Etat.VIDAGE));
        simulateur.ajouteEvenement(new ChangerEtat(dureeApresVidage, robot, Etat.DISPONNIBLE, null));
    }



    public void envoyerRobotSeRemplir(Robot robot, long date_courante, Carte carte) {

        //Chercher l'eau la plus proche si elle existe
        Case plusProcheEau = PlusCoursChemin.chercherPlusProcheEau(carte, robot);
        if (plusProcheEau == null) return;

        //Deplacements vers l'eau
        LinkedList<CaseDuree> chemin = PlusCoursChemin.djikstra(robot, plusProcheEau, carte);
        LinkedList<Evenement> deplacements = PlusCoursChemin.deplacerRobotChemin(date_courante, chemin, robot, carte);
        long dateApresChemin = date_courante + PlusCoursChemin.duree_chemin(chemin);
        simulateur.ajouteEvenements(deplacements);

        //Remplissage
        Evenement remplissage = new Remplissage(dateApresChemin, carte, robot, robot.getQuantEau());
        long dateApresRemplissage = remplissage.getdateFin();
        simulateur.ajouteEvenement(remplissage);

        //Changements des etats
        simulateur.ajouteEvenement(new ChangerEtat(date_courante, robot, Etat.DEPLACEMENT));
        simulateur.ajouteEvenement(new ChangerEtat(dateApresChemin, robot, Etat.REMPLISSAGE));
        simulateur.ajouteEvenement(new ChangerEtat(dateApresRemplissage, robot, Etat.DISPONNIBLE));
    }


    //Stratégie des sous-classes
    abstract public void jouerStrategie(DonneesSimulation data, long date_courante);
}
