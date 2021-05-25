package View.Graphs;

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

        //LineChart Setup
//        firstCorrelated.getXAxis().setTickLabelsVisible(false);
//        firstCorrelated.getXAxis().setTickMarkVisible(false);
//        firstCorrelated.setCreateSymbols(false);
//
//        secondCorrelated.getXAxis().setTickLabelsVisible(false);
//        secondCorrelated.getXAxis().setTickMarkVisible(false);
//        secondCorrelated.setCreateSymbols(false);
    }

    public void displayGraph(){
        XYChart.Series series = new XYChart.Series();
        int index = timeSeries.features.indexOf(selectedFeature.getValue());
        int currentTime = TimeStemp.getValue();

        for(int i = 0; i < currentTime; i++)
            series.getData().add(new XYChart.Data<>(String.valueOf(i),timeSeries.values.get(index).get(i)));

        firstCorrelated.getXAxis().setTickLabelsVisible(false);
        firstCorrelated.getXAxis().setTickMarkVisible(false);
        firstCorrelated.setCreateSymbols(false);
        firstCorrelated.getData().add(series);
    }

    public void setTimeSeiries(TimeSeries timeSeries){ this.timeSeries = timeSeries; }


    public void initialize() {
        features.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                firstCorrelated.getData().clear();
                System.out.println(newValue.toString());
                selectedFeature.setValue(newValue.toString());
                displayGraph();
            });


    }


}
