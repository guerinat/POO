package evenements;

import java.util.LinkedList;

import donnees.DonneesSimulation;

/**
 * Interface fonctionelle pour initialiser des evenements dans le simulateur à partir de @param data.
 */
@FunctionalInterface
public interface InitialisateurEvenements {
    LinkedList<Evenement> initialiserEvenements(DonneesSimulation data);
}
