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

    public void displayGraph(LineChart graph, String feature){
        XYChart.Series series = new XYChart.Series();
        int index = timeSeries.features.indexOf(feature);
        int currentTime = TimeStemp.getValue();

        for(int i = 0; i < currentTime; i++)
            series.getData().add(new XYChart.Data<>(String.valueOf(i),timeSeries.values.get(index).get(i)));

        graph.getXAxis().setTickLabelsVisible(false);
        graph.getXAxis().setTickMarkVisible(false);
        graph.setCreateSymbols(false);
        graph.getData().add(series);
    }

    public void setTimeSeiries(TimeSeries timeSeries){ this.timeSeries = timeSeries; }


    public void initialize() {
        features.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            firstCorrelated.getData().clear();
            System.out.println(newValue.toString());
            selectedFeature.setValue(newValue.toString());
            displayGraph(firstCorrelated, selectedFeature.getValue());

            secondCorrelated.getData().clear();
            int index = timeSeries.features.indexOf(selectedFeature.getValue());
            Hybrid hybrid = new Hybrid();
            CorrelatedFeatures correlatedFeatures = hybrid.maxCorellation(timeSeries,timeSeries.values.get(index),selectedFeature.getValue(),index);
            displayGraph(secondCorrelated, correlatedFeatures.feature2);
        });


    }


}
