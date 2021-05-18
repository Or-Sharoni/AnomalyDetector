package View.JoyStick;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class JoyStick extends AnchorPane {

    public JoyStick(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("JoyStick.fxml"));
                fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
