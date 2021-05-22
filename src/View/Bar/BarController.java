package View.Bar;

import Algorithms.TimeSeries;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;

import javafx.scene.control.ListView;
import java.io.File;
import java.util.ArrayList;

public class BarController {

    @FXML ListView features;
    @FXML Slider timeLine;



    public IntegerProperty TimeStemp;

    public BarController(){
        TimeStemp = new SimpleIntegerProperty();
    }

    public void openHandler(){
//        features.getItems().clear();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        TimeSeries timeSeries = new TimeSeries(file.getPath());
        for(String feature: timeSeries.features){
            features.getItems().add(feature);
        }

    }

    public void initialize() {
        TimeStemp.addListener((observable, oldValue, newValue) -> {
            timeLine.setValue((int)newValue);
        });

        timeLine.valueProperty().addListener((observable, oldValue, newValue) -> {
            TimeStemp.setValue(newValue);
        });

    }

}
