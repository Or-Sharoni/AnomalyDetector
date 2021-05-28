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
   public void barBinding(){
       BarController.timeText.bindBidirectional(viewModel.timeText);
       BarController.TimeStemp.bindBidirectional(viewModel.TimeStemp);
       BarController.speed.bindBidirectional(viewModel.speed);
       BarController.pause.setOnAction(e -> viewModel.model.Suspend());
       BarController.play.setOnAction(e -> viewModel.model.Play());
       BarController.stop.setOnAction(e -> viewModel.model.Stop());
       BarController.open.setOnAction(e-> openHandler());



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

    public void initialize() {
        BarController.TimeStemp.addListener((observable, oldValue, newValue) -> {
            viewModel.model.TimeStemp.setValue(newValue.intValue());
        });

        BarController.speed.addListener((observable, oldValue, newValue) -> {
            viewModel.model.speed.setValue(newValue.doubleValue());
        });
    }
}
