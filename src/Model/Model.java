package Model;

import Algorithms.TimeSeries;
import ViewModel.ViewModel;
import javafx.beans.property.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Model extends Observable {


    public DoubleProperty aileron,elevator,rudder,throttle,speed;
    public IntegerProperty TimeStemp;
    public StringProperty altimeterText,airspeedText,directionText,pitchText,yawText,rollText;
    public Thread clientThread;
    public TimeSeries timeSeries;

    public Model() {
        speed = new SimpleDoubleProperty(1);
        aileron = new SimpleDoubleProperty();
        elevator = new SimpleDoubleProperty();
        rudder = new SimpleDoubleProperty(0);
        throttle = new SimpleDoubleProperty(0);
        TimeStemp = new SimpleIntegerProperty(1);


        altimeterText = new SimpleStringProperty();
        airspeedText = new SimpleStringProperty();
        directionText = new SimpleStringProperty();
        pitchText = new SimpleStringProperty();
        yawText = new SimpleStringProperty();
        rollText = new SimpleStringProperty();

    }

    public void displaySimulator() throws IOException, InterruptedException {
        clientThread = new Thread(() -> {
            try {
                int size=timeSeries.valuesLines.size();
                while(TimeStemp.getValue()<size){
                    ArrayList<Float> array=timeSeries.valuesLines.get(TimeStemp.getValue());
                    setAileron(array.get(0));
                    setElevator(array.get(1));
                    setRudder(array.get(2));
                    setThrottle(array.get(6));
                    setAltimeter(array.get(25).toString());
                    setAirspeed(array.get(24).toString());
                    setDirection(array.get(36).toString());
                    setPitch(array.get(29).toString());
                    setYaw(array.get(20).toString());
                    setRoll(array.get(28).toString());
                    setTimeStemp(TimeStemp.getValue() + 1);
                    Thread.sleep((long) (100 * speed.getValue()));
                }
      //          String[] features = new String[50];
//        Socket fg=new Socket("localhost", 5400);
 //        BufferedReader in = new BufferedReader(new FileReader("src/reg_flight.csv"));
//        PrintWriter out=new PrintWriter(fg.getOutputStream());
//                String line = in.readLine();
//                while ((line = in.readLine()) != null) {
//                    features = line.split(",");
//                    setAileron(features[0]);
//                    setElevator(features[1]);
//                    setRudder(features[2]);
//                    setThrottle(features[6]);
//                    setAltimeter(features[25]);
//                    setAirspeed(features[24]);
//                    setDirection(features[36]);
//                    setPitch(features[29]);
//                    setYaw(features[20]);
//                    setRoll(features[28]);
//                    setTimeStemp(TimeStemp.getValue() + 1);
////            out.println(line);
////            out.flush();
//                    Thread.sleep((long) (100 * speed));
//
//                }
            } catch (Exception e) {
            }
        });
//        out.close();
//        in.close();
//        fg.close();
        clientThread.start();
    }

    public void setAileron(Float newAileron){
        aileron.setValue(newAileron);
        setChanged();
        notifyObservers("Aileron");
    }
    public void setElevator(Float newElevator){
        elevator.setValue(newElevator);
        setChanged();
        notifyObservers("Elevator");
    }
    public void setRudder(Float newRudder){
        rudder.setValue(newRudder);
        setChanged();
        notifyObservers("Rudder");
    }
    public void setThrottle(Float newThrottle){
        throttle.setValue(newThrottle);
        setChanged();
        notifyObservers("Throttle");
    }

    public void setTimeStemp(int timeStemp){
        TimeStemp.setValue(timeStemp);
        setChanged();
        notifyObservers("TimeStemp");
    }

    public void setAltimeter(String Text){
        altimeterText.setValue(Text);
        setChanged();
        notifyObservers("altimeterText");
    }

    public void setAirspeed(String Text){
        airspeedText.setValue(Text);
        setChanged();
        notifyObservers("airspeedText");
    }
    public void setDirection(String Text){
        directionText.setValue(Text);
        setChanged();
        notifyObservers("directionText");
    }

    public void setPitch(String Text){
        pitchText.setValue(Text);
        setChanged();
        notifyObservers("pitchText");
    }
    public void setYaw(String Text){
        yawText.setValue(Text);
        setChanged();
        notifyObservers("yawText");
    }

    public void setRoll(String Text){
        rollText.setValue(Text);
        setChanged();
        notifyObservers("rollText");
    }

    public void Suspend(){
        clientThread.suspend();

    }

    public void Play() throws IOException, InterruptedException {
        if(clientThread.isAlive() == true)
            clientThread.resume();
        else {
            displaySimulator();
        }
    }

    public void Stop(){
        setAileron((float) 0);
        setElevator((float) 0);
        setRudder((float) -1);
        setThrottle((float) -1);
        setTimeStemp(1);
        setAltimeter("0");
        setAirspeed("0");
        setDirection("0");
        setPitch("0");
        setYaw("0");
        setRoll("0");
        clientThread.stop();
    }

//    private void setSpeed(double speed){server.setSpeed(speed);}
}
