package evenements;

import java.util.LinkedList;
import java.util.ListIterator;

public class Simulateur {

    LinkedList<Evenement> evenements;
    private long date_courante;
    private long etape;

    public Simulateur(long etape) {
        this.evenements = new LinkedList<Evenement>();
        this.date_courante = 0;
        this.etape = etape;
    }

    public void ajouteEvenement(Evenement e) {
        
        ListIterator<Evenement> iterateur = evenements.listIterator();

        while (true) {
            if (!iterateur.hasNext()) {
                iterateur.add(e);
                return;
            }

            Evenement current = iterateur.next();
            if(e.getdate() > current.getdate()) {
                iterateur.previous();
                iterateur.add(current);
                return;
            }
        }
    }


    // Execute tout les évenement de date courante (compris) à date-courante + etape (non-compris)
    public void incrementeDate() {

        ListIterator<Evenement> iterateur = evenements.listIterator();
    
        while (iterateur.hasNext()) {
            Evenement current = iterateur.next(); 
    

            if (current.getdate() >= date_courante + etape)  
                break;

            if (current.getdate() >= date_courante) 
                current.execute();
            
        }
        date_courante += etape;
    }

    public boolean simulationTerminee() {
        return date_courante > evenements.getLast().getdate();
    }
}