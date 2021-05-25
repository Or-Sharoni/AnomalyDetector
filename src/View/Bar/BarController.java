package View.Bar;

import Algorithms.TimeSeries;
import javafx.beans.property.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javafx.scene.control.ListView;
import java.io.File;
import java.util.ArrayList;

public class BarController {

    @FXML Slider timeLine;

    @FXML public Button pause;
    @FXML public Button play;
    @FXML public Button stop;
    @FXML public Button open;
    @FXML public Button doubleForward;
    @FXML public Button doubleBack;
    @FXML public Button tripleForward;
    @FXML public Button tripleBack;
    @FXML public TextField speedPlay;
    public DoubleProperty speed;






    public IntegerProperty TimeStemp;

    public BarController(){
        TimeStemp = new SimpleIntegerProperty();
        speed=new SimpleDoubleProperty();
    }

    public void changeSpeed(){
        speed.setValue(1/(Double.parseDouble(speedPlay.getText())));
    }
    public void doubleForward(){
        speed.setValue(0.75);
        speedPlay.setText("1.25");
    }
    public void tripleForward(){
        speed.setValue(0.5);
        speedPlay.setText("1.5");
    }
    public void tripleBack(){
        speed.setValue(1.5);
        speedPlay.setText("0.5");
    }
    public void doubleBack(){
        speed.setValue(1.25);
        speedPlay.setText("0.75");
    }
    public void initialize() {
        TimeStemp.addListener((observable, oldValue, newValue) -> {
            timeLine.setValue((int)newValue);
        });

        timeLine.valueProperty().addListener((observable, oldValue, newValue) -> {
            TimeStemp.setValue(newValue.intValue());
        });
   }
}
