package View.Graphs;

import Algorithms.CorrelatedFeatures;
import Algorithms.Hybrid;
import Algorithms.SimpleAnomalyDetector;
import Algorithms.TimeSeries;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;

import javax.swing.*;
import java.sql.Time;
import java.util.HashMap;

public class GraphsController {

    @FXML LineChart firstCorrelated;
    @FXML LineChart secondCorrelated;
    @FXML ScatterChart anomalies;
    @FXML public ListView features;

    TimeSeries timeSeries;
    StringProperty selectedFeature;
    public IntegerProperty TimeStemp;
    TimeSeries detect;

    public GraphsController() {
        selectedFeature = new SimpleStringProperty();
        TimeStemp = new SimpleIntegerProperty();
    }

    public void displayGraph(LineChart graph, LineChart correlatedGraph, String feature, String correlatedFeature){
        XYChart.Series series = new XYChart.Series();
        XYChart.Series seriesCorrelated = new XYChart.Series();

        int index = timeSeries.features.indexOf(feature);
        int currentTime = TimeStemp.getValue();

        if(correlatedFeature == null){
            for(int i = 0; i < currentTime; i++)
                series.getData().add(new XYChart.Data<>(String.valueOf(i),timeSeries.values.get(index).get(i)));
            graph.getData().add(series);
            anomalies.getData().add(series);

        }
        else {
            int correlatedIndex = timeSeries.features.indexOf(correlatedFeature);

            for (int i = 0; i < currentTime; i++) {
                series.getData().add(new XYChart.Data(String.valueOf(i), timeSeries.values.get(index).get(i)));
                seriesCorrelated.getData().add(new XYChart.Data(String.valueOf(i), timeSeries.values.get(correlatedIndex).get(i)));
            }

            correlatedGraph.getData().add(seriesCorrelated);
            graph.getData().add(series);
            anomalies.getData().addAll(series,seriesCorrelated);

        }



    }

    public void setTimeSeiries(TimeSeries timeSeries){ this.timeSeries = timeSeries; }
    public void changingFeatureHandler(String newValue){
        selectedFeature.setValue(newValue);

        //Check the most correlated feature to the new value
        int index = timeSeries.features.indexOf(newValue);
        Hybrid hybrid = new Hybrid();
        CorrelatedFeatures correlatedFeatures = hybrid.maxCorellation(timeSeries,timeSeries.values.get(index),newValue,index);

        //Clear previous data
        firstCorrelated.getData().clear();
        secondCorrelated.getData().clear();
        anomalies.getData().clear();

        SimpleAnomalyDetector simpleAnomalyDetector = new SimpleAnomalyDetector();
        simpleAnomalyDetector.learnNormal(timeSeries);
        detect = new TimeSeries("src/anomaly_flight.csv");
        simpleAnomalyDetector.detect(detect);

        //Display the new features with the match correlated feature
        displayGraph(firstCorrelated,secondCorrelated , newValue,correlatedFeatures.feature2);

    }

    public void initialize() {
        features.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> changingFeatureHandler(newValue.toString()));
        firstCorrelated.getXAxis().setTickLabelsVisible(false);
        firstCorrelated.getXAxis().setTickMarkVisible(false);
        firstCorrelated.setCreateSymbols(false);
        secondCorrelated.getXAxis().setTickLabelsVisible(false);
        secondCorrelated.getXAxis().setTickMarkVisible(false);
        secondCorrelated.setCreateSymbols(false);
        anomalies.getXAxis().setTickLabelsVisible(false);
        anomalies.getXAxis().setTickMarkVisible(false);
    }


}
