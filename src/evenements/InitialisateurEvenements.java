package evenements;

import java.util.LinkedList;

import donnees.DonneesSimulation;

@FunctionalInterface
public interface InitialisateurEvenements {
    LinkedList<Evenement> initialiserEvenements(DonneesSimulation data);
}
