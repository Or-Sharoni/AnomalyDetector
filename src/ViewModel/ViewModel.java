package ViewModel;

import com.sun.tools.internal.ws.processor.model.Model;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Observable;
import java.util.Observer;
import java.util.function.DoublePredicate;

public class ViewModel implements Observer {

    Model model;
    public DoubleProperty aileron,elevator,rudder,throttle;

    public ViewModel(Model model){
        this.model = model;
      //  model.addObserver(this);
        aileron = new SimpleDoubleProperty();
        elevator = new SimpleDoubleProperty();
        rudder = new SimpleDoubleProperty();
        throttle = new SimpleDoubleProperty();
    }


    @Override
    public void update(Observable o, Object arg) {}
}
