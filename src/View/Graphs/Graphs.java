package View.Graphs;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Graphs extends AnchorPane {

    public Graphs(){
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Graphs.fxml"));
        GraphsController graphsController = new GraphsController();
        fxmlLoader.setController(graphsController);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
