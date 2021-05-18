package View.Graphs;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Graphs extends AnchorPane {

    public Graphs(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Graphs.fxml"));
                fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
