package ViewModel;

import Model.Model;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Observable;
import java.util.Observer;
import java.util.function.DoublePredicate;

public class ViewModel extends Observable implements Observer {

    Model model;
    public DoubleProperty aileron,elevator,rudder,throttle,simulatorSpeed;

    public ViewModel(Model model){
        this.model = model;
        aileron = new SimpleDoubleProperty();
        elevator = new SimpleDoubleProperty();
        rudder = new SimpleDoubleProperty();
        throttle = new SimpleDoubleProperty();
        simulatorSpeed = new SimpleDoubleProperty();
        simulatorSpeed.setValue(1.0);
    }





    @Override
    public void update(Observable o, Object arg) {}


}
