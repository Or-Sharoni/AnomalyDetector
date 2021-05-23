package View;


import Algorithms.TimeSeries;
import View.Bar.Bar;
import View.Bar.BarController;
import View.Graphs.GraphsController;
import View.JoyStick.JoyStickController;
import ViewModel.ViewModel;
import View.ControlPanel.ControlPanelController;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.IOException;


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

    public void graphBinding(){}
   public void barBinding(){
       BarController.TimeStemp.bindBidirectional(viewModel.TimeStemp);
       BarController.speed.bindBidirectional(viewModel.speed);
       BarController.pause.setOnAction(e -> viewModel.model.Suspend());
       BarController.play.setOnAction(e -> {
           try {
               viewModel.model.Play();
           } catch (IOException ioException) {
               ioException.printStackTrace();
           } catch (InterruptedException interruptedException) {
               interruptedException.printStackTrace();
           }
       });
       BarController.stop.setOnAction(e -> viewModel.model.Stop());
       BarController.open.setOnAction(e-> {
           try {
               openHandler();
           } catch (IOException ioException) {
               ioException.printStackTrace();
           } catch (InterruptedException interruptedException) {
               interruptedException.printStackTrace();
           }
       });



   }
    public void openHandler() throws IOException, InterruptedException {
        BarController.features.getItems().clear();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        viewModel.timeSeries = new TimeSeries(file.getPath());
        for(String feature: viewModel.timeSeries.features){
            BarController.features.getItems().add(feature);
        }
        viewModel.model.timeSeries= viewModel.timeSeries;
        viewModel.model.displaySimulator();

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
