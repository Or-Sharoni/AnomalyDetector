package ViewModel;

import Model.Model;
import View.JoyStick.JoyStickController;
import View.MainController;
import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Observable;
import java.util.Observer;
import java.util.function.DoublePredicate;

public class ViewModel implements Observer {

    public DoubleProperty aileron,elevator,rudder,throttle;
    public IntegerProperty TimeStemp;
    public Model model;

    public ViewModel(Model model){
        this.model = model;
        aileron = new SimpleDoubleProperty();
        elevator = new SimpleDoubleProperty();
        rudder = new SimpleDoubleProperty();
        throttle = new SimpleDoubleProperty();

        TimeStemp = new SimpleIntegerProperty();
        aileron.setValue(1);
        this.model.addObserver(this);

    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg == "Aileron") {
            this.aileron.setValue(this.model.aileron.getValue());
//            System.out.println("aileron: " + this.aileron);
        }
        if(arg == "Elevator") {
            this.elevator.setValue(this.model.elevator.getValue());
//            System.out.println("elevator: " + this.elevator);
        }
        if(arg == "Rudder") {
            this.rudder.setValue(this.model.rudder.getValue());
//            System.out.println("rudder: " + this.rudder);
        }
        if(arg == "Throttle") {
            this.throttle.setValue(this.model.throttle.getValue());
//            System.out.println("throttle: " + this.throttle);
        }

        if(arg == "TimeStemp"){
            this.TimeStemp.setValue(this.model.TimeStemp.getValue());
            System.out.println("TimeStemp: " + this.TimeStemp);

        }

    }


    public DoubleProperty getAileron(){return this.aileron;}


}
