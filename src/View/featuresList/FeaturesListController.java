package View.featuresList;
import Algorithms.TimeSeries;
import javafx.scene.control.ListView;

import javafx.fxml.FXML;

import java.util.ArrayList;

public class FeaturesListController {

    ArrayList<String> features;

    @FXML
    public ListView featuresList;

    public void displayFeatures(ArrayList<String> features){
        for(String str: features){
            featuresList.getItems().add(str);
        }

    }

}
