package View;


import Algorithms.TimeSeries;
import View.Bar.Bar;
import View.Bar.BarController;
import View.Graphs.GraphsController;
import View.JoyStick.JoyStickController;
import ViewModel.ViewModel;
import View.ControlPanel.ControlPanelController;

import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


public class MainController {

    private ViewModel viewModel;
    @FXML BarController BarController;
    @FXML GraphsController GraphsController;
    @FXML JoyStickController JoyStickController;
    @FXML ControlPanelController ControlPanelController;


    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
        joyStickBinding();
        barBinding();
        controlPanelBinding();
        graphsBinding();


    }

    public void joyStickBinding(){
        JoyStickController.aileron.bindBidirectional(viewModel.aileron);
        JoyStickController.elevator.bindBidirectional(viewModel.elevator);
        JoyStickController.rudder.bindBidirectional(viewModel.rudder);
        JoyStickController.throttle.bindBidirectional(viewModel.throttle);
   }
   public void controlPanelBinding() {
       ControlPanelController.altimeterText.bindBidirectional(viewModel.altimeterText);
       ControlPanelController.airspeedText.bindBidirectional(viewModel.airspeedText);
       ControlPanelController.directionText.bindBidirectional(viewModel.directionText);
       ControlPanelController.pitchText.bindBidirectional(viewModel.pitchText);
       ControlPanelController.yawText.bindBidirectional(viewModel.yawText);
       ControlPanelController.rollText.bindBidirectional(viewModel.rollText);
   }

    public void graphsBinding(){
        GraphsController.TimeStemp.bindBidirectional(viewModel.TimeStemp);
    }
   public void barBinding() {
       BarController.timeText.bindBidirectional(viewModel.timeText);
       BarController.TimeStemp.bindBidirectional(viewModel.TimeStemp);
       BarController.speed.bindBidirectional(viewModel.speed);
       BarController.pause.setOnAction(e -> viewModel.model.Suspend());
       BarController.play.setOnAction(e -> {
           BarController.playNormal();
           viewModel.model.Play();
       });
       BarController.stop.setOnAction(e -> viewModel.model.Stop());
       BarController.open.setOnAction(e -> openHandler());
       BarController.select.setOnAction(e -> {
           try {
               loadAlgorithmHandler();
           } catch (MalformedURLException malformedURLException) {
               malformedURLException.printStackTrace();
           } catch (ClassNotFoundException classNotFoundException) {
               classNotFoundException.printStackTrace();
           } catch (InstantiationException instantiationException) {
               instantiationException.printStackTrace();
           } catch (IllegalAccessException illegalAccessException) {
               illegalAccessException.printStackTrace();
           }
       });

   }
    public void openHandler(){

        GraphsController.features.getItems().clear();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files only", "*.csv"));
        File file = fileChooser.showOpenDialog(null);
        if(file == null)
            return;
        viewModel.timeSeries = new TimeSeries(file.getPath());
        for(String feature: viewModel.timeSeries.features){
            GraphsController.features.getItems().add(feature);
        }
        viewModel.model.timeSeries= viewModel.timeSeries;
        viewModel.model.displaySimulator();

        GraphsController.setTimeSeiries(viewModel.timeSeries);
    }

    public void loadAlgorithmHandler() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Object algor = null;
        BarController.result.setText("");
        String className;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Class Files only", "*.class"));
        File file = fileChooser.showOpenDialog(null);
        if(file == null) {
            BarController.result.setText("Failed to load");
            BarController.result.setTextFill(Color.web("red"));
            return;
        }

        // load class directory
        className = file.getParentFile().getName() + "." + file.getName().substring(0,file.getName().indexOf("."));
        URL[] url = new URL[1];
        // change this path to your local one
        url[0] = new URL("file://" + file.getParentFile().getParent() + "/");
        URLClassLoader urlClassLoader = new URLClassLoader(url);
        Class<?> c = null;
            c = urlClassLoader.loadClass(className);
        // create an Algorithms instance
            algor = c.newInstance();
        BarController.result.setText("Load Successfully!");
        BarController.result.setTextFill(Color.web("green"));

        System.out.println(algor);

    }

    public void initialize() {
        BarController.TimeStemp.addListener((observable, oldValue, newValue) -> {
            viewModel.model.TimeStemp.setValue(newValue.intValue());
        });

        BarController.speed.addListener((observable, oldValue, newValue) -> {
            viewModel.model.speed.setValue(newValue.doubleValue());
        });
    }
}
