package View.Graphs;

import Algorithms.CorrelatedFeatures;
import Algorithms.Hybrid;
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
        }
        else {
            int correlatedIndex = timeSeries.features.indexOf(correlatedFeature);
            for (int i = 0; i < currentTime; i++) {
                series.getData().add(new XYChart.Data<>(String.valueOf(i), timeSeries.values.get(index).get(i)));
                seriesCorrelated.getData().add(new XYChart.Data<>(String.valueOf(i), timeSeries.values.get(correlatedIndex).get(i)));
            }

            correlatedGraph.getXAxis().setTickLabelsVisible(false);
            correlatedGraph.getXAxis().setTickMarkVisible(false);
            correlatedGraph.setCreateSymbols(false);
            correlatedGraph.getData().add(seriesCorrelated);
        }

        graph.getXAxis().setTickLabelsVisible(false);
        graph.getXAxis().setTickMarkVisible(false);
        graph.setCreateSymbols(false);
        graph.getData().add(series);

    }

    public void setTimeSeiries(TimeSeries timeSeries){ this.timeSeries = timeSeries; }


    public void initialize() {
        features.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedFeature.setValue(newValue.toString());

            //Check the most correlated feature to the new value
            int index = timeSeries.features.indexOf(newValue.toString());
            Hybrid hybrid = new Hybrid();
            CorrelatedFeatures correlatedFeatures = hybrid.maxCorellation(timeSeries,timeSeries.values.get(index),newValue.toString(),index);

            //Clear previous data
            firstCorrelated.getData().clear();
            secondCorrelated.getData().clear();

            //Display the new features with the match correlated feature
            displayGraph(firstCorrelated,secondCorrelated , newValue.toString(),correlatedFeatures.feature2);

        });


    }


}
