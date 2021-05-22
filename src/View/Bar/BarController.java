package View.Bar;

import Algorithms.TimeSeries;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import javafx.scene.control.ListView;
import java.io.File;
import java.util.ArrayList;

public class BarController {

    @FXML ListView features;

    public ArrayList<StringProperty> featuresList;

    public BarController(){}

    public void openHandler(){
//        features.getItems().clear();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        TimeSeries timeSeries = new TimeSeries(file.getPath());
        for(String feature: timeSeries.features){
            features.getItems().add(feature);
        }

    }

}
