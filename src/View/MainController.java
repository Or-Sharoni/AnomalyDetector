package View;


import View.Bar.Bar;
import View.Bar.BarController;
import View.Graphs.GraphsController;
import View.JoyStick.JoyStick;
import View.JoyStick.JoyStickController;
import ViewModel.ViewModel;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import View.Bar.Bar;
import View.Graphs.Graphs;
import View.JoyStick.JoyStick;



public class MainController {

    private ViewModel viewModel;
    @FXML BarController BarController;
    @FXML GraphsController GraphsController;
    @FXML JoyStickController JoyStickController;


    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
        joyStickBinding();

    }

    public void joyStickBinding(){
        JoyStickController.aileron.bindBidirectional(viewModel.aileron);
        JoyStickController.elevator.bindBidirectional(viewModel.elevator);
        JoyStickController.rudder.bindBidirectional(viewModel.rudder);
        JoyStickController.throttle.bindBidirectional(viewModel.throttle);
        BarController.TimeStemp.bindBidirectional(viewModel.TimeStemp);
   }

   public void graphBinding(){}
   public void barBinding(){}



}
