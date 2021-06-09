package View;

import Algorithms.Algorithms;
import Model.Model;
import ViewModel.ViewModel;
import Algorithms.Hybrid;
import Algorithms.SimpleAnomalyDetector;
import Algorithms.AnomalyReport;
import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
import java.util.ArrayList;
import java.util.List;

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
     //   properties.set();
        model.displaySimulator();

        primaryStage.setTitle("Controller");
        primaryStage.setScene(new Scene(root, 950, 550));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("bye");
            viewModel.model.Stop();
        });

    }


    public static void main(String[] args) {
        launch(args);
//        TimeSeries ts1 = new TimeSeries("/Users/royna/AnomalyDetector/src/reg_flight.csv");
//        TimeSeries ts2 = new TimeSeries("/Users/royna/AnomalyDetector/src/anomaly_flight.csv");
//
//        SimpleAnomalyDetector simpleAnomalyDetector = new SimpleAnomalyDetector();
//        simpleAnomalyDetector.learnNormal(ts1);
//        List<AnomalyReport> anomalyReportList = simpleAnomalyDetector.detect(ts2);
//        for(AnomalyReport anomalyReport: anomalyReportList){
//            System.out.println(anomalyReport.timeStep +  "        " +anomalyReport.description);
//        Hybrid hybrid = new Hybrid();
//        hybrid.learnNormal(ts1);
//        List<AnomalyReport> anomalyReportList = hybrid.detect(ts2);
//        for(AnomalyReport anomalyReport: anomalyReportList){
//            System.out.println(anomalyReport.description + "  " + anomalyReport.timeStep);
//        }

    }


}

