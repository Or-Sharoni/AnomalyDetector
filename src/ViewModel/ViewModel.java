package ViewModel;

import Model.Model;
import View.JoyStick.JoyStickController;
import View.MainController;
import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Observable;
import java.util.Observer;
import java.util.function.DoublePredicate;

public class ViewModel implements Observer {

    public DoubleProperty aileron,elevator,rudder,throttle;
    public Model model;

    public ViewModel(Model model){
        this.model = model;
        aileron = new SimpleDoubleProperty();
        elevator = new SimpleDoubleProperty();
        rudder = new SimpleDoubleProperty();
        throttle = new SimpleDoubleProperty();
        this.model.addObserver(this);

    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg == "Aileron") {
            this.aileron = this.model.aileron;
//            System.out.println("aileron: " + this.aileron);

        }
        if(arg == "Elevator") {
            this.elevator = this.model.elevator;
            System.out.println("elevator: " + this.elevator);
        }
        if(arg == "Rudder") {
            this.rudder = this.model.rudder;
            System.out.println("rudder: " + this.rudder);
        }
        if(arg == "Throttle") {
            this.throttle = this.model.throttle;
            System.out.println("throttle: " + this.throttle);
        }

    }


}
