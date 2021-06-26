package View.JoyStick;

import ViewModel.ViewModel;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        aileron = new SimpleDoubleProperty(1);
        elevator = new SimpleDoubleProperty(1);
        rudder = new SimpleDoubleProperty();
        throttle = new SimpleDoubleProperty();

        }

    public void initialize() {

        rudder.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(()->{horizontal.setValue((double)newValue);});
            }
        });

        throttle.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(()->{vertical.setValue((double)newValue);});
            }
        });

        aileron.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(()->{joystick.setCenterX(newValue.doubleValue()*80);});
            }
        });

        elevator.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(()->{joystick.setCenterY(newValue.doubleValue()*80);});
            }
        });



    }



}
