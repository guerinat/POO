// import java.util.LinkedList;
// import java.util.PriorityQueue;
// import java.util.ArrayList;

// import chemins.*;
// import donnees.*;
// import donnees.robots.*;
// import donnees.carte.*;
// import evenements.*;
// import io.*;

// /* 
// public class ChefPompier1 implements Simulable{
    
//     /* L'interface graphique associée */
//     private GUISimulator gui;

//     private DonneesSimulation data;

//     public static int tailleGui = 850;
//     private int tailleCase;

//     //Evenements

//     private LinkedList<Evenement> evenements;
//     private long date_courante;
//     private String cheminFichier;


//     public ChefPompier1(String cheminFichier, DonneesSimulation data) {
        
//         //Chargement des données
//         this.cheminFichier = cheminFichier;
//         this.data = data;
//         this.tailleCase = tailleGui/data.carte.getNbLignes();

//         //Association au gui
//         this.gui = new GUISimulator(tailleGui, tailleGui, Color.WHITE);
//         gui.setSimulable(this);
//         planCoordinates();

//         //Initialisation des evenements
//         this.evenements = new LinkedList<Evenement>();
//         ajouteEvenements(Finirlejeu(data));
//         this.date_courante = 0;

//         //Affichage
//         draw();
//     }


//     public Incendie detecteIncendie(DonneesSimulation data){
//         for (int incendie=0; incendie<data.incendies.length;incendie++){
//             if (data.incendies[incendie].getEauNecessaire()>0 && data.incendies[incendie].getRobot()==null){
//                 //return data.carte.getCase(data.incendies[incendie].getPosition().getLigne(), data.incendies[incendie].getPosition().getColonne());
//                 return data.incendies[incendie];
//             }
//         }
//         System.out.println("Pas d'incendie détécté");
//         return null;
//     }


//     public Robot robotDispo(DonneesSimulation data, Incendie incendie, long date){
//         if (incendie==null) return null;
//         for (int robot=0; robot<data.robots.length;robot++){
//             if (date >= robot.getDateDispo()){
//                 incendie.changeRobot(data.robots[robot]);
//                 data.robots[robot].changeIncendie(incendie);
//                 return data.robots[robot];
//             }
//         }
//         return null;
//     }

//     public static boolean RobotOccupe(Incendie[] incendies, Robot robot){
//         for (Incendie incendie : incendies){
//             if (incendie.getRobot() == robot)
//                 return true;
//         }
//         return false;
//     }


//     public PriorityQueue<Evenement> EteindreIncendie(DonneesSimulation data, long date_debut, Incendie incendie, Robot robot){
//         long date = date_debut;
//         LinkedList<CaseDuree> chemin = PlusCoursChemin.djikstra(robot, incendie.getPosition(), data.carte);
//         PriorityQueue<Evenement> events = PlusCoursChemin.deplacerRobotChemin(date, chemin, robot, data.carte);
//         date += PlusCoursChemin.duree_chemin(chemin);
//         while (incendie.getEauNecessaire()!=0){
//             while (robot.getQuantEau()>0){
//                 events.add(new Vidage(date_debut, robot, incendie));
//                 date += Vidage.calcDuree(robot);
//             }
//             Case Eau = PlusCoursChemin.PlusProcheEau(data, robot);
//             LinkedList<CaseDuree> cheminEau = PlusCoursChemin.djikstra(robot, incendie.getPosition(), data.carte);
//             Evenement[] eventsEau = PlusCoursChemin.deplacerRobotChemin(date, chemin, robot, data.carte).toArray(new Evenement[0]);
//             for (int i=0; i<eventsEau.length;i++){
//                 events.add(eventsEau[i]);
//                 if (i==eventsEau.length-1 && !robot.getRemplitSurEau())
//                     break;
//             }
//             date += PlusCoursChemin.duree_chemin(cheminEau);
//             events.add(new Remplissage(date, data.carte, robot, robot.getQuantReservoire()));
//             date += Remplissage.calcDuree(robot, robot.getQuantReservoire());
//             LinkedList<CaseDuree> chemin2 = PlusCoursChemin.djikstra(robot, incendie.getPosition(), data.carte);
//             Evenement[] events2 = PlusCoursChemin.deplacerRobotChemin(date, chemin2, robot, data.carte).toArray(new Evenement[0]);
//             for (int i=0; i<events2.length;i++){
//                 events.add(events2[i]);
//             }
//             date += PlusCoursChemin.duree_chemin(chemin);
            
//         }
//         robot.changeDateDispo(date);
//         return events;
//     }

//     public Evenement[] Finirlejeu(DonneesSimulation data){
//         long date = 0;
//         Evenement[] events;
//         while (detecteIncendie(data) != null){
//             Incendie incendie = detecteIncendie(data);
//             Robot robot = robotDispo(data, incendie, date);
//             if (robot != null){
//                 PriorityQueue<Evenement> events2 = EteindreIncendie(data, date, incendie, robot);
//                 for (Evenement evenement:events2){
//                     events.add(evenement);
//                 }
//             }
//             else{
//                 date += 1;    
//             }
//         }
//     }

//     @Override
//     public void next() {
//         incrementeDate();
//         draw();
//     }


//     @Override
//     public void restart() {

//         try {
//             data = LecteurDonnees.creeDonnees(cheminFichier);
//             evenements.clear();
//         } catch (FileNotFoundException e) {
//             System.out.println("fichier " + cheminFichier + " inconnu ou illisible");
//         } catch (DataFormatException e) {
//             System.out.println("\n\t**format du fichier " + cheminFichier + " invalide: " + e.getMessage());
//         }

//         date_courante = 0;

//         evenements.clear();
//         ajouteEvenements(Finirlejeu(data));

//         System.out.println(evenements.getFirst());

//         planCoordinates();
//         draw();
//     }

//     public void ajouteEvenement(Evenement e) {
// Finirlejeu(data)
//         ListIterator<Evenement> iterateur = evenements.listIterator();

//         while (true) {
//             if (!iterateur.hasNext()) {
//                 iterateur.add(e);
//                 return;
//             }

//             Evenement current = iterateur.next();
//             if(e.getdateFin() < current.getdateFin()) {
//                 iterateur.previous();
//                 iterateur.add(e);
//                 return;
//             }
//         }
//     }


//     public void ajouteEvenements(Evenement[] events) {
//         for(Evenement e : events)
//             ajouteEvenement(e);
//     }

//     // Execute tout les évenement de date courante (compris) à date-courante + 1 (non-compris)
//     private void incrementeDate() {

//         ListIterator<Evenement> iterateur = evenements.listIterator();
    
//         while (iterateur.hasNext()) {
//             Evenement current = iterateur.next(); 

//             if (current.getdateFin() >= date_courante + 1)  
//                 break;

//             if (current.getdateFin() >= date_courante) {
//                 current.execute();
//                 System.out.println("[t="+current.getdateFin()+"] "+current.toString()+"\n");
//             }
            
//         }
//         date_courante ++;
//     }import java.util.LinkedList;
// import java.util.PriorityQueue;
// import java.util.ArrayList;

// import chemins.*;
// import donnees.*;
// import donnees.robots.*;
// import donnees.carte.*;
// import evenements.*;
// import io.*;

// /* 
// public class ChefPompier1 implements Simulable{
    
//     /* L'interface graphique associée */
//     private GUISimulator gui;

//     private DonneesSimulation data;

//     public static int tailleGui = 850;
//     private int tailleCase;

//     //Evenements

//     private LinkedList<Evenement> evenements;
//     private long date_courante;
//     private String cheminFichier;


//     public ChefPompier1(String cheminFichier, DonneesSimulation data) {
        
//         //Chargement des données
//         this.cheminFichier = cheminFichier;
//         this.data = data;
//         this.tailleCase = tailleGui/data.carte.getNbLignes();

//         //Association au gui
//         this.gui = new GUISimulator(tailleGui, tailleGui, Color.WHITE);
//         gui.setSimulable(this);
//         planCoordinates();

//         //Initialisation des evenements
//         this.evenements = new LinkedList<Evenement>();
//         ajouteEvenements(Finirlejeu(data));
//         this.date_courante = 0;

//         //Affichage
//         draw();
//     }


//     public Incendie detecteIncendie(DonneesSimulation data){
//         for (int incendie=0; incendie<data.incendies.length;incendie++){
//             if (data.incendies[incendie].getEauNecessaire()>0 && data.incendies[incendie].getRobot()==null){
//                 //return data.carte.getCase(data.incendies[incendie].getPosition().getLigne(), data.incendies[incendie].getPosition().getColonne());
//                 return data.incendies[incendie];
//             }
//         }
//         System.out.println("Pas d'incendie détécté");
//         return null;
//     }


//     public Robot robotDispo(DonneesSimulation data, Incendie incendie, long date){
//         if (incendie==null) return null;
//         for (int robot=0; robot<data.robots.length;robot++){
//             if (date >= robot.getDateDispo()){
//                 incendie.changeRobot(data.robots[robot]);
//                 data.robots[robot].changeIncendie(incendie);
//                 return data.robots[robot];
//             }
//         }
//         return null;
//     }

//     public static boolean RobotOccupe(Incendie[] incendies, Robot robot){
//         for (Incendie incendie : incendies){
//             if (incendie.getRobot() == robot)
//                 return true;
//         }
//         return false;
//     }


//     public PriorityQueue<Evenement> EteindreIncendie(DonneesSimulation data, long date_debut, Incendie incendie, Robot robot){
//         long date = date_debut;
//         LinkedList<CaseDuree> chemin = PlusCoursChemin.djikstra(robot, incendie.getPosition(), data.carte);
//         PriorityQueue<Evenement> events = PlusCoursChemin.deplacerRobotChemin(date, chemin, robot, data.carte);
//         date += PlusCoursChemin.duree_chemin(chemin);
//         while (incendie.getEauNecessaire()!=0){
//             while (robot.getQuantEau()>0){
//                 events.add(new Vidage(date_debut, robot, incendie));
//                 date += Vidage.calcDuree(robot);
//             }
//             Case Eau = PlusCoursChemin.PlusProcheEau(data, robot);
//             LinkedList<CaseDuree> cheminEau = PlusCoursChemin.djikstra(robot, incendie.getPosition(), data.carte);
//             Evenement[] eventsEau = PlusCoursChemin.deplacerRobotChemin(date, chemin, robot, data.carte).toArray(new Evenement[0]);
//             for (int i=0; i<eventsEau.length;i++){
//                 events.add(eventsEau[i]);
//                 if (i==eventsEau.length-1 && !robot.getRemplitSurEau())
//                     break;
//             }
//             date += PlusCoursChemin.duree_chemin(cheminEau);
//             events.add(new Remplissage(date, data.carte, robot, robot.getQuantReservoire()));
//             date += Remplissage.calcDuree(robot, robot.getQuantReservoire());
//             LinkedList<CaseDuree> chemin2 = PlusCoursChemin.djikstra(robot, incendie.getPosition(), data.carte);
//             Evenement[] events2 = PlusCoursChemin.deplacerRobotChemin(date, chemin2, robot, data.carte).toArray(new Evenement[0]);
//             for (int i=0; i<events2.length;i++){
//                 events.add(events2[i]);
//             }
//             date += PlusCoursChemin.duree_chemin(chemin);
            
//         }
//         robot.changeDateDispo(date);
//         return events;
//     }

//     public Evenement[] Finirlejeu(DonneesSimulation data){
//         long date = 0;
//         Evenement[] events;
//         while (detecteIncendie(data) != null){
//             Incendie incendie = detecteIncendie(data);
//             Robot robot = robotDispo(data, incendie, date);
//             if (robot != null){
//                 PriorityQueue<Evenement> events2 = EteindreIncendie(data, date, incendie, robot);
//                 for (Evenement evenement:events2){
//                     events.add(evenement);
//                 }
//             }
//             else{
//                 date += 1;    
//             }
//         }
//     }

//     @Override
//     public void next() {
//         incrementeDate();
//         draw();
//     }


//     @Override
//     public void restart() {

//         try {
//             data = LecteurDonnees.creeDonnees(cheminFichier);
//             evenements.clear();
//         } catch (FileNotFoundException e) {
//             System.out.println("fichier " + cheminFichier + " inconnu ou illisible");
//         } catch (DataFormatException e) {
//             System.out.println("\n\t**format du fichier " + cheminFichier + " invalide: " + e.getMessage());
//         }

//         date_courante = 0;

//         evenements.clear();
//         ajouteEvenements(Finirlejeu(data));

//         System.out.println(evenements.getFirst());

//         planCoordinates();
//         draw();
//     }

//     public void ajouteEvenement(Evenement e) {
// Finirlejeu(data)
//         ListIterator<Evenement> iterateur = evenements.listIterator();

//         while (true) {
//             if (!iterateur.hasNext()) {
//                 iterateur.add(e);
//                 return;
//             }

//             Evenement current = iterateur.next();
//             if(e.getdateFin() < current.getdateFin()) {
//                 iterateur.previous();
//                 iterateur.add(e);
//                 return;
//             }
//         }
//     }


//     public void ajouteEvenements(Evenement[] events) {
//         for(Evenement e : events)
//             ajouteEvenement(e);
//     }

//     // Execute tout les évenement de date courante (compris) à date-courante + 1 (non-compris)
//     private void incrementeDate() {

//         ListIterator<Evenement> iterateur = evenements.listIterator();
    
//         while (iterateur.hasNext()) {
//             Evenement current = iterateur.next(); 

//             if (current.getdateFin() >= date_courante + 1)  
//                 break;

//             if (current.getdateFin() >= date_courante) {
//                 current.execute();
//                 System.out.println("[t="+current.getdateFin()+"] "+current.toString()+"\n");
//             }
            
//         }
//         date_courante ++;
//     }


//     private boolean simulationTerminee() {
//         return date_courante > evenements.getLast().getdateFin();
//     }


//     private void planCoordinates() {
//         int xMin = 60;
//         int yMin = 40;
//         int xMax = gui.getWidth() - xMin - 80;
//         xMax -= xMax % 10;
//         int yMax = gui.getHeight() - yMin - 120;
//         yMax -= yMax % 10;
//     }



//     private void draw_map() {

//         for (int ligne=0; ligne < data.carte.getNbLignes(); ++ligne ){
//             for (int colonne=0; colonne < data.carte.getNbColonnes(); ++colonne){

//                 int xCase = colonne*tailleCase;
//                 int yCase = ligne*tailleCase;
//                 String texture = data.carte.getCase(ligne, colonne).getNature().getLienTexture();

//                 gui.addGraphicalElement(new ImageElement(xCase, yCase, texture, tailleCase, tailleCase, null));
//             }
//         }
//     }

//     private void draw_incendies() {

//         for(int i = 0; i < data.incendies.length; i++) {
//             int xCase = data.incendies[i].getPosition().getColonne()*tailleCase;
//             int yCase = data.incendies[i].getPosition().getLigne()*tailleCase;
//             String texture = "ressources/incendie.png";

//             if (data.incendies[i].getEauNecessaire() != 0) //Si l'incendie n'est pas eteinte
//                 gui.addGraphicalElement(new ImageElement(xCase, yCase, texture, tailleCase, tailleCase, null));
//         }
//     }


//     private void draw_robots() {

//         for(int i = 0; i < data.robots.length; i++) {
//             int xCase = data.robots[i].getPosition().getColonne()*tailleCase;
//             int yCase = data.robots[i].getPosition().getLigne()*tailleCase;
//             String texture = data.robots[i].getLienTexture();
            
//             gui.addGraphicalElement(new ImageElement(xCase, yCase, texture, tailleCase, tailleCase, null));
//         }
//     }


//     private void draw() {
        
//         gui.reset();

//         draw_map();
//         draw_incendies();
//         draw_robots();
//     }


//     @Override
//     public String toString() {
//         String s = "";
//         for(Evenement e : evenements) {
//             s+=e.toString()+"\n";
//         }
//         return s;
//     }

// }
    

// */


//     private boolean simulationTerminee() {
//         return date_courante > evenements.getLast().getdateFin();
//     }


//     private void planCoordinates() {
//         int xMin = 60;
//         int yMin = 40;
//         int xMax = gui.getWidth() - xMin - 80;
//         xMax -= xMax % 10;
//         int yMax = gui.getHeight() - yMin - 120;
//         yMax -= yMax % 10;
//     }



//     private void draw_map() {

//         for (int ligne=0; ligne < data.carte.getNbLignes(); ++ligne ){
//             for (int colonne=0; colonne < data.carte.getNbColonnes(); ++colonne){

//                 int xCase = colonne*tailleCase;
//                 int yCase = ligne*tailleCase;
//                 String texture = data.carte.getCase(ligne, colonne).getNature().getLienTexture();

//                 gui.addGraphicalElement(new ImageElement(xCase, yCase, texture, tailleCase, tailleCase, null));
//             }
//         }
//     }

//     private void draw_incendies() {

//         for(int i = 0; i < data.incendies.length; i++) {
//             int xCase = data.incendies[i].getPosition().getColonne()*tailleCase;
//             int yCase = data.incendies[i].getPosition().getLigne()*tailleCase;
//             String texture = "ressources/incendie.png";

//             if (data.incendies[i].getEauNecessaire() != 0) //Si l'incendie n'est pas eteinte
//                 gui.addGraphicalElement(new ImageElement(xCase, yCase, texture, tailleCase, tailleCase, null));
//         }
//     }


//     private void draw_robots() {

//         for(int i = 0; i < data.robots.length; i++) {
//             int xCase = data.robots[i].getPosition().getColonne()*tailleCase;
//             int yCase = data.robots[i].getPosition().getLigne()*tailleCase;
//             String texture = data.robots[i].getLienTexture();
            
//             gui.addGraphicalElement(new ImageElement(xCase, yCase, texture, tailleCase, tailleCase, null));
//         }
//     }


//     private void draw() {
        
//         gui.reset();

//         draw_map();
//         draw_incendies();
//         draw_robots();
//     }


//     @Override
//     public String toString() {
//         String s = "";
//         for(Evenement e : evenements) {
//             s+=e.toString()+"\n";
//         }
//         return s;
//     }

// }
    

// */