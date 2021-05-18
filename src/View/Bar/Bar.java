package View.Bar;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Bar extends AnchorPane {

    public Bar(){
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Bar.fxml"));
                fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
