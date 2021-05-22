package View.ControlPanel;

import View.Graphs.GraphsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ControlPanel extends AnchorPane {

    public ControlPanel() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ControlPanel.fxml"));
        ControlPanelController controlPanelController = new ControlPanelController();
        fxmlLoader.setController(controlPanelController);


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

}
