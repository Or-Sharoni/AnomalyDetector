package View;

import Model.Model;
import Model.Server;
import ViewModel.ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import Algorithms.TimeSeries;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxml = new FXMLLoader();
        AnchorPane root = fxml.load(getClass().getResource("MainController.fxml"));
        //MainController controller = fxml.getController();

//        Server server = new Server();
//        server.displaySimulator();
        primaryStage.setTitle("Controller");
        primaryStage.setScene(new Scene(root, 950, 550));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}

