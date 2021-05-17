package View.ControllBar;

import Algorithms.TimeSeries;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllBar extends AnchorPane{

    @FXML private ListView features;
    @FXML private TextField speed;

    public ControllBar() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ControllBar.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException exc) {
        }
    }


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