package View.JoyStick;

import ViewModel.ViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Slider;
import javafx.scene.shape.Circle;

public class JoyStickController {

    public DoubleProperty aileron,elevator,rudder,throttle;

    @FXML Circle joystickBackground;
    @FXML Circle joystick;
    @FXML Slider horizontal;
    @FXML Slider vertical;


    public JoyStickController(){
        aileron = new SimpleDoubleProperty(0);
        elevator = new SimpleDoubleProperty(0);
        rudder = new SimpleDoubleProperty();
        throttle = new SimpleDoubleProperty();

        }

    public void initialize() {
        rudder.addListener((observable, oldValue, newValue) -> {horizontal.setValue((double)newValue);});
        throttle.addListener((observable, oldValue, newValue) -> {vertical.setValue((double)newValue);});
        aileron.addListener((observable, oldValue, newValue) -> {joystick.setCenterX(newValue.doubleValue() * 80);});
        elevator.addListener((observable, oldValue, newValue) -> {joystick.setCenterY(newValue.doubleValue() * 80);});
    }



}
