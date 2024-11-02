package evenements;

import donnees.carte.*;
import donnees.robots.*;

public class Deplacement extends Evenement{
    private Direction dir;
    private Carte carte;
    private Robot robot;

    public Deplacement(long date, Carte carte, Direction dir, Robot robot){
        super(date);

        this.dir = dir;
        this.carte = carte;
        this.robot = robot; 
    }

    @Override
    public void execute(){
        Case destination = carte.getVoisin(robot.getPosition(), dir);
        robot.setPosition(destination);
    }

    @Override
    public String toString() {
        return "Deplacement (date:"+super.getdate()+", robot:"+robot.toString()+", dir:"+dir.toString()+")";
        
    }
}