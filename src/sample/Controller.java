package sample;
import javafx.fxml.FXML;
import Algorithms.TimeSeries;
import javafx.scene.control.ListView;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import javafx.stage.FileChooser;


public class Controller {

    @FXML
    public ListView featuresList;
    public void getFile(){

        FileChooser fc = new FileChooser();
        File file = null;
        File returnVal = fc.showOpenDialog(null);
        openHandler(returnVal);
    }

    public void openHandler(File file){
        TimeSeries timeSeries = new TimeSeries(file.getPath());
        for(String str: timeSeries.features){
           featuresList.getItems().add(str);
        }
        System.out.println(timeSeries.features);

    }
    public void displayFeatures(ArrayList<String> features){

    }

}
