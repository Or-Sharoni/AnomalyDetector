package View.ControlPanel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
        altimeterText = new SimpleStringProperty();
        airspeedText = new SimpleStringProperty();
        directionText = new SimpleStringProperty();
        pitchText = new SimpleStringProperty();
        yawText = new SimpleStringProperty();
        rollText = new SimpleStringProperty();
    }

    public void initialize() {
        altimeterText.addListener((observable, oldValue, newValue) -> {altimeter.setText(newValue);});
        airspeedText.addListener((observable, oldValue, newValue) -> {airspeed.setText(newValue);});
        directionText.addListener((observable, oldValue, newValue) -> {direction.setText(newValue);});
        pitchText.addListener((observable, oldValue, newValue) -> {pitch.setText(newValue);});
        yawText.addListener((observable, oldValue, newValue) -> {yaw.setText(newValue);});
        rollText.addListener((observable, oldValue, newValue) -> {roll.setText(newValue);});


    }



}
