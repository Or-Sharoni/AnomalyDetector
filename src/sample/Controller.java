package sample;

import javafx.fxml.FXML;
import ptm1.test.TimeSeries;

import javax.swing.*;
import javax.swing.text.View;
import javax.swing.text.html.ListView;
import java.io.File;
import java.util.ArrayList;

public class Controller {

    @FXML
    private ListView featuresList;
    private void getFile(){
        JFileChooser fc = new JFileChooser();
        File file = null;
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
        }
        openHandler(file);
    }

    public void openHandler(File file){
        TimeSeries timeSeries = new TimeSeries(file.getPath());
        System.out.println(timeSeries.features);

    }
    public void displayFeatures(ArrayList<String> features){

    }

}
