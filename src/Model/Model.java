package Model;

import java.util.Observable;
import java.util.Observer;

public class Model extends Observable implements Observer {

    Server server;

    public Model(){
        server = new Server();
    }

    @Override
    public void update(Observable o, Object arg) {
    }

    private void setSpeed(double speed){server.setSpeed(speed);}
}
