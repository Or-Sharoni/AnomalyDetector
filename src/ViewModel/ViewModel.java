package ViewModel;

import Model.Model;
import View.MainController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Observable;
import java.util.Observer;
import java.util.function.DoublePredicate;

public class ViewModel extends Observable implements Observer {

    public DoubleProperty aileron,elevator,rudder,throttle;


    public ViewModel(){
        aileron = new SimpleDoubleProperty();
        elevator = new SimpleDoubleProperty();
        rudder = new SimpleDoubleProperty();
        throttle = new SimpleDoubleProperty();

    }



    @Override
    public void update(Observable o, Object arg) {}


}
