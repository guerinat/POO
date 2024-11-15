package io;


import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;

import donnees.*;
import donnees.carte.*;
import donnees.robots.*;

/**
 * Lecteur de cartes au format spectifié dans le sujet.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * puis simplement affichées.
 */
public class LecteurDonnees {


    /**
     * Lit et affiche le contenu d'un fichier de donnees (cases,
     * robots et incendies).
     * Ceci est méthode de classe; utilisation:
     * LecteurDonnees.lire(fichierDonnees)
     * @param fichierDonnees nom du fichier à lire
     */
    public static void lire(String fichierDonnees)
        throws FileNotFoundException, DataFormatException {
        System.out.println("\n == Lecture du fichier" + fichierDonnees);
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        lecteur.lireCarte();
        lecteur.lireIncendies();
        lecteur.lireRobots();
        scanner.close();
        System.out.println("\n == Lecture terminee");
    }


    private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }


    /**
     * Lit les données de la carte depuis le fichier de données et affiche les informations
     * relatives à la carte (dimensions, taille des cases, etc.).
     * 
     * @throws DataFormatException Si le format des données est incorrect
     */
    private void lireCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();	// en m
            System.out.println("Carte " + nbLignes + "x" + nbColonnes
                    + "; taille des cases = " + tailleCases);

            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    lireCase(lig, col);
                }
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
    }

    
    /**
     * Crée une instance de la carte en lisant le fichier de données.
     * Retourne un objet Carte contenant toutes les cases.
     * 
     * @throws DataFormatException Si le format des données est incorrect
     */
    private Carte creerCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();
            
            Carte carte = new Carte(tailleCases, nbLignes, nbColonnes);

            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    Case src = creerCase(lig, col);
                    carte.setCase(src, lig, col);
                }
            }

            return carte;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
    }


    /**
     * Lit les informations d'une case et les affiche.
     * 
     * @param lig La ligne de la case
     * @param col La colonne de la case
     * @throws DataFormatException Si le format des données est incorrect
     */
    private void lireCase(int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Case (" + lig + "," + col + "): ");
        String chaineNature = new String();
        //		NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            // si NatureTerrain est un Enum, vous pouvez recuperer la valeur
            // de l'enum a partir d'une String avec:
            //			NatureTerrain nature = NatureTerrain.valueOf(chaineNature);

            verifieLigneTerminee();

            System.out.print("nature = " + chaineNature);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }

        System.out.println();
    }


    /**
     * Crée une instance de la case à partir des données lues dans le fichier.
     * 
     * @param lig La ligne de la case
     * @param col La colonne de la case
     * @return La case créée
     * @throws DataFormatException Si le format des données est incorrect
     */
    private Case creerCase(int lig, int col) throws DataFormatException {

        ignorerCommentaires();
        String chaineNature = new String();
        NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            nature = NatureTerrain.valueOf(chaineNature);
            verifieLigneTerminee();

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }
        return new Case(lig, col, nature);
    }


    /**
     * Lit et affiche les incendies à partir des données du fichier.
     * 
     * @throws DataFormatException Si le format des données est incorrect
     */
    private void lireIncendies() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            System.out.println("Nb d'incendies = " + nbIncendies);
            for (int i = 0; i < nbIncendies; i++) {
                lireIncendie(i);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }


    /**
     * Crée un tableau d'incendies à partir des données lues dans le fichier.
     * 
     * @param carte La carte de simulation
     * @return Le tableau des incendies créés
     * @throws DataFormatException Si le format des données est incorrect
     */
    private Incendie[] creerIncendies(Carte carte) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            Incendie[] incendies = new Incendie[nbIncendies];
            
            for (int i = 0; i < nbIncendies; i++) {
                incendies[i] = creerIncendie(i, carte);
            }

            return incendies;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }


    /**
     * Lit et affiche les données d'un incendie.
     * 
     * @param i L'indice de l'incendie
     * @throws DataFormatException Si le format des données est incorrect
     */
    private void lireIncendie(int i) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Incendie " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();

            System.out.println("position = (" + lig + "," + col
                    + ");\t intensite = " + intensite);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Crée un incendie à partir des données lues dans le fichier.
     * 
     * @param i L'indice de l'incendie
     * @param carte La carte où se trouve l'incendie
     * @return L'incendie créé
     * @throws DataFormatException Si le format des données est incorrect
     */
    private Incendie creerIncendie(int i, Carte carte) throws DataFormatException {
        ignorerCommentaires();

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();

            Incendie incendie = new Incendie(carte.getCase(lig, col), intensite);

            return incendie;
            
        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Lit les robots à partir des données du fichier.
     * 
     * @throws DataFormatException Si le format des données est incorrect
     */
    private void lireRobots() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            System.out.println("Nb de robots = " + nbRobots);
            for (int i = 0; i < nbRobots; i++) {
                lireRobot(i);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }


    /**
     * Crée une simulation de robots à partir des données lues.
     * 
     * @param carte La carte de simulation
     * @return Un tableau des robots créés
     * @throws DataFormatException Si le format des données est incorrect
     */
    private Robot[] creerRobots(Carte carte) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            Robot[] robots = new Robot[nbRobots];

            for (int i = 0; i < nbRobots; i++) {
                robots[i] = creerRobot(i, carte);
            }

            return robots;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }


    /**
     * Lit et affiche les données d'un robot.
     * 
     * @param i L'indice du robot
     * @throws DataFormatException Si le format des données est incorrect
     */
    private void lireRobot(int i) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Robot " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            System.out.print("position = (" + lig + "," + col + ");");
            String type = scanner.next();

            System.out.print("\t type = " + type);


            // lecture eventuelle d'une vitesse du robot (entier)
            System.out.print("; \t vitesse = ");
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
            // pour lire un flottant:    ("(\\d+(\\.\\d+)?)");

            if (s == null) {
                System.out.print("valeur par defaut");
            } else {
                int vitesse = Integer.parseInt(s);
                System.out.print(vitesse);
            }
            verifieLigneTerminee();

            System.out.println();

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }


    /**
     * Crée un robot à partir des données lues dans le fichier.
     * 
     * @param i L'indice du robot
     * @param carte La carte où se trouve le robot
     * @return Le robot créé
     * @throws DataFormatException Si le format des données est incorrect
     */
    private Robot creerRobot(int i, Carte carte) throws DataFormatException {
        ignorerCommentaires();

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();

            String type = scanner.next();
            String s = scanner.findInLine("(\\d+(\\.\\d+)?)");	 

            verifieLigneTerminee();

            Robot robot;

            switch (type) {

                case "DRONE":
                    if (s == null) robot = new Drone(carte.getCase(lig, col), 100);
                    else           robot = new Drone(carte.getCase(lig, col), Math.min(Double.valueOf(s), 150));
                break;

                case "ROUES":
                    if (s == null) robot = new RobotARoues(carte.getCase(lig, col), 80);
                    else           robot = new RobotARoues(carte.getCase(lig, col), Double.valueOf(s));
                break;

                case "PATTES":
                    robot = new RobotAPattes(carte.getCase(lig, col));
                break;

                case "CHENILLES":
                    if (s == null) robot = new RobotAChenilles(carte.getCase(lig, col), 60);
                    else           robot = new RobotAChenilles(carte.getCase(lig, col), Math.min(Double.valueOf(s), 80));
                break;

                default: throw new NoSuchElementException();
            }
            
            return robot;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }


    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }


    /**
     * Vérifie si la ligne actuelle dans le fichier est terminée et ne contient
     * pas de données supplémentaires.
     * 
     * @throws DataFormatException Si des données supplémentaires sont présentes
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }


    /**
     * Li et creer les données de la simulation.
     * @throws ExceptionFormatDonnees
     */
    public static DonneesSimulation creeDonnees(String fichierDonnees) throws FileNotFoundException, DataFormatException {

        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        
        Carte carte = lecteur.creerCarte();
        Incendie[] incendies = lecteur.creerIncendies(carte);
        Robot[] robots = lecteur.creerRobots(carte);

        scanner.close();

        return new DonneesSimulation(robots, incendies, carte);
    }
}
