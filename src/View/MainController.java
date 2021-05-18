package View;

import Algorithms.TimeSeries;
import ViewModel.ViewModel;
import javafx.beans.property.*;

import javafx.fxml.FXML;
import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.swing.text.View;

public class MainController {

    private ViewModel viewModel;


    private DoubleProperty simulatorSpeed;
    @FXML private ListView features;
    @FXML private TextField speed;

    public void openHandler() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CSV File");
        File file = fileChooser.showOpenDialog(null);

        TimeSeries timeSeries = new TimeSeries(file.getPath());
        for (String str : timeSeries.features) {
            features.getItems().add(str);
        }
    }

    public void moveForwardHandler() {
//        simulatorSpeed.setValue(10.0);
        speed.appendText("1.5");
    }

    public void moveBackwardHandler() {
//        simulatorSpeed.setValue(0.1);
        speed.appendText("0.5");
    }
}
