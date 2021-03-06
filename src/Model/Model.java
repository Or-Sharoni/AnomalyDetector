package Model;

import Algorithms.TimeSeries;
import View.Bar.BarController;
import ViewModel.ViewModel;
import javafx.application.Platform;
import javafx.beans.property.*;

import java.io.*;
import java.net.Socket;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Model extends Observable {


    public DoubleProperty aileron,elevator,rudder,throttle,speed;
    public IntegerProperty TimeStemp;
    public StringProperty altimeterText,airspeedText,directionText,pitchText,yawText,rollText,timeText;
    public Thread clientThread;
    public TimeSeries timeSeries;
    public String ip;
    public String FilePath;
    public int port;

    public Model() {
        speed = new SimpleDoubleProperty(1);
        aileron = new SimpleDoubleProperty(0);
        elevator = new SimpleDoubleProperty(0);
        rudder = new SimpleDoubleProperty(0);
        throttle = new SimpleDoubleProperty(0);

        TimeStemp = new SimpleIntegerProperty(1);

        altimeterText = new SimpleStringProperty("0");
        airspeedText = new SimpleStringProperty("0");
        directionText = new SimpleStringProperty("0");
        pitchText = new SimpleStringProperty("0");
        yawText = new SimpleStringProperty("0");
        rollText = new SimpleStringProperty("0");
        timeText = new SimpleStringProperty("00:00:00");

    }

    public void displaySimulator(){
        clientThread = new Thread(() -> {
            try {
                int size = timeSeries.valuesLines.size();
                Socket fg=new Socket(ip, port); // bind with xml :)
                BufferedReader in=new BufferedReader(new FileReader(FilePath));
                PrintWriter out=new PrintWriter(fg.getOutputStream());
                String line;
                while(TimeStemp.getValue()<size && (line = in.readLine()) != null){
                    setAileron(timeSeries.valuesLines.get(TimeStemp.getValue()).get(0));
                    setElevator(timeSeries.valuesLines.get(TimeStemp.getValue()).get(1));
                    setRudder(timeSeries.valuesLines.get(TimeStemp.getValue()).get(2));
                    setThrottle(timeSeries.valuesLines.get(TimeStemp.getValue()).get(6));
                    setAltimeter(timeSeries.valuesLines.get(TimeStemp.getValue()).get(25).toString());
                    setAirspeed(timeSeries.valuesLines.get(TimeStemp.getValue()).get(24).toString());
                    setDirection(timeSeries.valuesLines.get(TimeStemp.getValue()).get(36).toString());
                    setPitch(timeSeries.valuesLines.get(TimeStemp.getValue()).get(29).toString());
                    setYaw(timeSeries.valuesLines.get(TimeStemp.getValue()).get(20).toString());
                    setRoll(timeSeries.valuesLines.get(TimeStemp.getValue()).get(28).toString());
                    setTimeStemp(TimeStemp.getValue() + 1);
                    if(TimeStemp.getValue() == timeSeries.values.get(0).size()-2)
                        clientThread.stop();
                    setTime(flightTime(TimeStemp.getValue(),timeText.getValue()));

                    out.println(line);
                    out.flush();
                    clientThread.sleep((long) (100 * speed.getValue()));

                }

            } catch (Exception e) {}
        });
        clientThread.start();
    }

    public static String flightTime(int t,String time) {
        String[] newArray = time.split(":");
        int hours = Integer.parseInt(newArray[0]);
        int minutes = Integer.parseInt(newArray[1]);
        int seconds = Integer.parseInt(newArray[2]);
        seconds=t;
        if(seconds>=60) {
            minutes = seconds / 60;
            seconds=seconds%60;
        }
        if(minutes>=60) {
            hours = minutes / 60;
            minutes = minutes % 60;
        }
        if (seconds < 10)
            newArray[2] = "0" +seconds;
        else {
            newArray[2] = Integer.toString(seconds);
        }
        if (minutes < 10)
            newArray[1] = "0" +minutes;
        else {
            newArray[1] = Integer.toString(minutes);
        }
        if (hours < 10)
            newArray[0] = "0" +hours;
        else {
            newArray[0] = Integer.toString(hours);
        }
        return newArray[0] + ":" + newArray[1] + ":" + newArray[2];
    }

    public void setTime(String Text){
        timeText.setValue(Text);
        setChanged();
        notifyObservers("timeText");
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

    public void Play() {
        if(clientThread.isAlive()) {
            setTime("00:00:00");
            clientThread.resume();

        }
        else {
            this.displaySimulator();
        }
    }

    public void Stop(){
        setAileron((float) 0);
        setElevator((float) 0);
        setRudder((float) -1);
        setThrottle((float) -1);
        setTimeStemp(1);
        setTime("00:00:00");
        setAltimeter("0");
        setAirspeed("0");
        setDirection("0");
        setPitch("0");
        setYaw("0");
        setRoll("0");
        clientThread.stop();
    }

}
