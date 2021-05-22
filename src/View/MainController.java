package View;


import View.Bar.Bar;
import View.Bar.BarController;
import View.Graphs.GraphsController;
import View.JoyStick.JoyStickController;
import ViewModel.ViewModel;
import View.ControlPanel.ControlPanelController;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;


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



   }


//    EventHandler<ActionEvent> pause = new EventHandler<ActionEvent>() {
//        @Override
//        public void handle(ActionEvent t) {
//            viewModel.model.Suspend();
//        }
//    };




}
