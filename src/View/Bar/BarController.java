package View.Bar;

import Algorithms.TimeSeries;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javafx.scene.control.ListView;
import java.io.File;
import java.util.ArrayList;

public class BarController {

    @FXML public ListView features;
    @FXML Slider timeLine;


    @FXML public Button pause;
    @FXML public Button play;
    @FXML public Button stop;
    @FXML public Button open;






    public IntegerProperty TimeStemp;

    public BarController(){
        TimeStemp = new SimpleIntegerProperty();
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
