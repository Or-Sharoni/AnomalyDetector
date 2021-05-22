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
    @FXML public Slider horizontal;
    @FXML Slider vertical;


    public JoyStickController(){
        aileron = new SimpleDoubleProperty();
        elevator = new SimpleDoubleProperty();
        rudder = new SimpleDoubleProperty();
        throttle = new SimpleDoubleProperty();


        }

    public void initialize() {horizontal.valueProperty().bindBidirectional(aileron);}



}
