package evenements;

import donnees.carte.*;
import donnees.robots.*;

public class Deplacement extends Evenement{
    private Case destination;
    private Robot robot;

    public Deplacement(long date, Case destination, Robot robot){
        super(date);
        this.destination = destination;
        this.robot = robot;
    }

    @Override
    public void execute(){
        robot.setPosition(destination);
    }
}