package View.Bar;

import Algorithms.TimeSeries;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import javafx.scene.control.ListView;
import java.io.File;

public class BarController {

    @FXML ListView features;

    public void openHandler(){
        features.getItems().clear();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        TimeSeries timeSeries = new TimeSeries(file.getPath());
        for(String feature: timeSeries.features){
            features.getItems().add(feature);
        }

    }

}
