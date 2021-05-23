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
    public StringProperty altimeterText,airspeedText,directionText,pitchText,yawText,rollText;
    public IntegerProperty TimeStemp;
    public Model model;
    public TimeSeries timeSeries;


    public ViewModel(Model model){
        this.model = model;

        //Double Properties
        aileron = new SimpleDoubleProperty();
        elevator = new SimpleDoubleProperty();
        rudder = new SimpleDoubleProperty();
        throttle = new SimpleDoubleProperty();
        speed = new SimpleDoubleProperty();

        //Strings Properties
        altimeterText = new SimpleStringProperty();
        airspeedText = new SimpleStringProperty();
        directionText = new SimpleStringProperty();
        pitchText = new SimpleStringProperty();
        yawText = new SimpleStringProperty();
        rollText = new SimpleStringProperty();

        //Integer Properties
        TimeStemp = new SimpleIntegerProperty();
        aileron.setValue(1);
        this.model.addObserver(this);

    }

    @Override
    public void update(Observable o, Object arg) {
        switch (arg.toString()) {
            case ("Aileron"):
                this.aileron.setValue(this.model.aileron.getValue());
                break;
//            System.out.println("aileron: " + this.aileron);

            case ("Elevator"):
                this.elevator.setValue(this.model.elevator.getValue());
                break;
//            System.out.println("elevator: " + this.elevator);

            case ( "Rudder") :
                this.rudder.setValue(this.model.rudder.getValue());
                break;
//            System.out.println("rudder: " + this.rudder);

            case ("Throttle"):
                this.throttle.setValue(this.model.throttle.getValue());
                break;
//            System.out.println("throttle: " + this.throttle);

            case ("TimeStemp") :
                this.TimeStemp.setValue(this.model.TimeStemp.getValue());
                System.out.println("TimeStemp: " + this.TimeStemp);
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
        }

    }

    public DoubleProperty getAileron(){return this.aileron;}


}
