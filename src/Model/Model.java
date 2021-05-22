package Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Model extends Observable {


    private double speed = 1;
    public DoubleProperty aileron,elevator,rudder,throttle;
    public Thread clientThread;

    public Model() {
        aileron = new SimpleDoubleProperty();
        elevator = new SimpleDoubleProperty();
        rudder = new SimpleDoubleProperty();
        throttle = new SimpleDoubleProperty();
    }

    public void displaySimulator() throws IOException, InterruptedException {
        clientThread = new Thread(() -> {
            try {
                String[] features = new String[50];
//        Socket fg=new Socket("localhost", 5400);
                BufferedReader in = new BufferedReader(new FileReader("src/reg_flight.csv"));
//        PrintWriter out=new PrintWriter(fg.getOutputStream());
                String line = in.readLine();
                while ((line = in.readLine()) != null) {
                    features = line.split(",");
                    setAileron(features[0]);
                    setElevator(features[1]);
                    setRudder(features[2]);
                    setThrottle(features[6]);
//            out.println(line);
//            out.flush();
                    Thread.sleep((long) (100 * speed));

                }
            } catch (Exception e) {
            }
        });
//        out.close();
//        in.close();
//        fg.close();


        clientThread.start();

    }

    public void setAileron(String newAileron){
        aileron.setValue(Double.parseDouble(newAileron));
        setChanged();
        notifyObservers("Aileron");
    }
    public void setElevator(String newElevator){
        elevator.setValue(Double.parseDouble(newElevator));
        setChanged();
        notifyObservers("Elevator");
    }
    public void setRudder(String newRudder){
        rudder.setValue(Double.parseDouble(newRudder));
        setChanged();
        notifyObservers("Rudder");
    }
    public void setThrottle(String newThrottle){
        throttle.setValue(Double.parseDouble(newThrottle));
        setChanged();
        notifyObservers("Throttle");
    }

//    private void setSpeed(double speed){server.setSpeed(speed);}
}
