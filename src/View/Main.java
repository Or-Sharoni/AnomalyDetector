package View;

import Algorithms.Algorithms;
import Model.Model;
import ViewModel.ViewModel;
import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import Algorithms.TimeSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("MainController.fxml"));
        AnchorPane root = fxml.load();
        MainController controller = fxml.getController();
        Model model = new Model();
        ViewModel viewModel = new ViewModel(model);
        controller.setViewModel(viewModel);

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
//        launch(args);
        String input,className;
        System.out.println("enter a class directory");
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        input=in.readLine(); // get user input
        System.out.println("enter the class name");
        className=in.readLine();
        in.close();
        // load class directory
        URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] {
                new URL(input)
        });
        Class<?> c = urlClassLoader.loadClass(className);
        // create a StringConverter instance
        Algorithms algo=(Algorithms) c.newInstance();
        System.out.println(algo);
    }


}

