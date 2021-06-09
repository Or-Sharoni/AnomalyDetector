package View.ControlPanel;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class ControlPanelController {

    @FXML TextField altimeter;
    @FXML TextField airspeed;
    @FXML TextField direction;
    @FXML TextField pitch;
    @FXML TextField yaw;
    @FXML TextField roll;

    public StringProperty altimeterText,airspeedText,directionText,pitchText,yawText,rollText;

    public ControlPanelController(){
        altimeterText = new SimpleStringProperty("0");
        airspeedText = new SimpleStringProperty("0");
        directionText = new SimpleStringProperty("0");
        pitchText = new SimpleStringProperty("0");
        yawText = new SimpleStringProperty("0");
        rollText = new SimpleStringProperty("0");
    }

    public void initialize() {
        altimeterText.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Platform.runLater(() -> {altimeter.setText(newValue);});
            }
        });

        airspeedText.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Platform.runLater(() -> {airspeed.setText(newValue);});
            }
        });

        directionText.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Platform.runLater(() -> {direction.setText(newValue);});
            }
        });

        pitchText.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Platform.runLater(() -> {pitch.setText(newValue);});
            }
        });

        yawText.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Platform.runLater(() -> {yaw.setText(newValue);});
            }
        });

        rollText.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Platform.runLater(() -> {roll.setText(newValue);});
            }
        });

    }



}
