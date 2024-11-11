package donnees;
import donnees.carte.*;
import donnees.robots.*;

public class Incendie {
    private Case position;
    private int eau_necessaire;
    private Robot robot=null;


    public Incendie(Case position, int eau_necessaire) {
        this.position = position;
        this.eau_necessaire = eau_necessaire;
    }

    public Case getPosition() {
        return this.position;
    }

    public int getEauNecessaire() {
        return this.eau_necessaire;
    }

    //renvoi true si l'incendie est eteinte
    public void eteindre(int volume) {
        eau_necessaire = volume < eau_necessaire ? eau_necessaire-volume : 0;
    }

    public Robot getRobot(){
        return this.robot;
    }

    public void changeRobot(Robot robot){
        this.robot = robot;
    }

    @Override
    public String toString() {
        if (eau_necessaire == 0)
            return "(pos:"+position.toString()+", ETEINTE)";
        return "(pos:"+position.toString()+", eau:"+eau_necessaire+")";
    }
}