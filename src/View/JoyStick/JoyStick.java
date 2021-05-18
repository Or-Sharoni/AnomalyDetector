package View.JoyStick;

import ViewModel.ViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import javax.swing.text.View;
import java.io.IOException;

public class JoyStick extends AnchorPane {

    public DoubleProperty aileron,elevator,rudder,throttle;

    public JoyStick(){
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("JoyStick.fxml"));
        JoyStickController joyStickController = new JoyStickController();
        fxmlLoader.setController(joyStickController);

        aileron = joyStickController.aileron;
        elevator = joyStickController.elevator;
        rudder = joyStickController.elevator;
        throttle = joyStickController.throttle;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
