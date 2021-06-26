package ViewModel;

import Algorithms.TimeSeries;
import Model.Model;
import View.JoyStick.JoyStickController;
import View.MainController;
import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.beans.property.*;

import javax.swing.text.View;
import java.util.Observable;
import java.util.Observer;
import java.util.function.DoublePredicate;

public class ViewModel implements Observer {

    public DoubleProperty aileron,elevator,rudder,throttle,speed;
    public StringProperty altimeterText,airspeedText,directionText,pitchText,yawText,rollText,timeText;
    public IntegerProperty TimeStemp;
    public Model model;
    public TimeSeries timeSeries;


    public ViewModel(Model model){
        this.model = model;

        //Double Properties
        aileron = new SimpleDoubleProperty(0);
        elevator = new SimpleDoubleProperty(0);
        rudder = new SimpleDoubleProperty(0);
        throttle = new SimpleDoubleProperty(0);
        speed = new SimpleDoubleProperty(1);

        //Strings Properties
        altimeterText = new SimpleStringProperty("0");
        airspeedText = new SimpleStringProperty("0");
        directionText = new SimpleStringProperty("0");
        pitchText = new SimpleStringProperty("0");
        yawText = new SimpleStringProperty("0");
        rollText = new SimpleStringProperty("0");
        timeText = new SimpleStringProperty("00:00:00");


        //Integer Properties
        TimeStemp = new SimpleIntegerProperty(1);
        this.model.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        switch (arg.toString()) {
            case ("Aileron"):
                this.aileron.setValue(this.model.aileron.getValue());
                break;
            case ("Elevator"):
                this.elevator.setValue(this.model.elevator.getValue());
                break;
            case ( "Rudder") :
                this.rudder.setValue(this.model.rudder.getValue());
                break;

            case ("Throttle"):
                this.throttle.setValue(this.model.throttle.getValue());
                break;

            case ("TimeStemp") :
                this.TimeStemp.setValue(this.model.TimeStemp.getValue());
                break;
            case("altimeterText"):
                this.altimeterText.setValue(this.model.altimeterText.getValue());
                break;
            case("airspeedText"):
                this.airspeedText.setValue(this.model.airspeedText.getValue());
                break;
            case("directionText"):
                this.directionText.setValue(this.model.directionText.getValue());
                break;
            case("pitchText"):
                this.pitchText.setValue(this.model.pitchText.getValue());
                break;
            case("yawText"):
                this.yawText.setValue(this.model.yawText.getValue());
                break;
            case("rollText"):
                this.rollText.setValue(this.model.rollText.getValue());
                break;
            case("timeText"):
                this.timeText.setValue((this.model.timeText.getValue()));
                break;
        }

    }

    public DoubleProperty getAileron(){return this.aileron;}


}
