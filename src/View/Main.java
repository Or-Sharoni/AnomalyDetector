package View;

import Algorithms.Algorithms;
import Model.Model;
import ViewModel.ViewModel;
import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
<<<<<<< HEAD
=======
import javafx.event.ActionEvent;
import Algorithms.TimeSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;

>>>>>>> 0ee074c184126907adbd9127ff640fa1e03239ae

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("MainController.fxml"));
        AnchorPane root = fxml.load();
        MainController controller = fxml.getController();
        Model model = new Model();
        ViewModel viewModel = new ViewModel(model);
        controller.setViewModel(viewModel);

        Properties properties = new Properties();
        properties.set();
        properties.createXMLFile();
        model.displaySimulator();

        primaryStage.setTitle("Controller");
        primaryStage.setScene(new Scene(root, 950, 550));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("bye");
            viewModel.model.Stop();
        });

    }


    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        launch(args);
        String input,className;
        className = "algo.ZScore";
        // load class directory
        URL[] url = new URL[1];
        // change this path to your local one
        url[0] = new URL("file:///Users/or.s/IdeaProjects/plugin/out/production/plugin/");
        URLClassLoader urlClassLoader = new URLClassLoader(url);

        Class<?> c =urlClassLoader.loadClass(className);

        // create an Algorithms instance
        Object algor = c.newInstance();
        System.out.println(algor);

    }


}

