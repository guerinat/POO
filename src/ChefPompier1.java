import donnees.*;
import donnees.robots.Robot;
import donnees.carte.*;
import evenements.*;


public abstract class ChefPompier1{
    
    public Case detecteIncendie(DonneesSimulation data){
        for (int incendie=0; incendie<data.incendies.length;incendie++){
            if (data.incendies[incendie].getEauNecessaire()>0){
                return data.carte.getCase(data.incendies[incendie].getPosition().getLigne(), data.incendies[incendie].getPosition().getColonne());
            }
        }
        System.out.println("Pas d'incendie détécté");
        return null;
    }


    public Robot robotDispo(DonneesSimulation data){
        Case incendie = detecteIncendie(data);
        if (incendie==null) return null;
        for (int robot=0; robot<data.robots.length;robot++){
            if (data.robots[robot].getIncendie() == null){
                
            }
        }
    }


}
    

