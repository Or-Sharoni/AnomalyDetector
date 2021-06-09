package View.Graphs;

import Algorithms.*;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.sql.Time;
import java.util.HashMap;

public class GraphsController {

    @FXML LineChart firstCorrelated;
    @FXML LineChart secondCorrelated;
    @FXML Canvas anomalies;
    @FXML public ListView features;


    GraphicsContext gc;
    TimeSeries timeSeries;
    StringProperty selectedFeature;
    public IntegerProperty TimeStemp;
    TimeSeries detect;
    Thread graphThread;

    XYChart.Series series;
    XYChart.Series correlatedSeries;

    int index1;
    int index2;

    public GraphsController() {
        selectedFeature = new SimpleStringProperty();
        TimeStemp = new SimpleIntegerProperty();
    }

    public void displayGraphs() {
         series = new XYChart.Series<>();
         correlatedSeries = new XYChart.Series<>();
        for(Integer time=0;time<TimeStemp.getValue();time++){
            series.getData().add(new XYChart.Data<>(time.toString(), timeSeries.values.get(index1).get(time)));
            correlatedSeries.getData().add(new XYChart.Data<>(time.toString(), timeSeries.values.get(index2).get(time)));
        }
        firstCorrelated.getData().add(series);
        secondCorrelated.getData().add(correlatedSeries);
    }

//    public void updateGraph(){
//        listener = new ChangeListener() {
//            @Override
//            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
////                series.getData().add(new XYChart.Data<>(newValue.toString(), timeSeries.values.get(index1).get((int)newValue)));
////                correlatedSeries.getData().add(new XYChart.Data<>(newValue.toString(), timeSeries.values.get(index2).get((int)newValue)));
//                System.out.println((int)newValue);
//            }
//        };
//    }

    public void setTimeSeiries(TimeSeries timeSeries){ this.timeSeries = timeSeries; }
    public void changingFeatureHandler(String newFeature){
        //Clear the previous data
        firstCorrelated.getData().clear();
        secondCorrelated.getData().clear();

        //Print out the graphs data
        index1 = timeSeries.features.indexOf(newFeature);
        Hybrid hybrid = new Hybrid();
        CorrelatedFeatures correlatedFeatures = hybrid.maxCorellation(timeSeries,timeSeries.values.get(index1),newFeature,index1);
        index2 = (correlatedFeatures.feature2 != null) ?  timeSeries.features.indexOf(correlatedFeatures.feature2) : index1;
        displayGraphs();
    }

    public void initAnomaliesGraph(){
        gc = anomalies.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.beginPath();
        gc.moveTo(0,1);
        gc.lineTo(0,anomalies.getHeight());
        gc.moveTo(0,anomalies.getHeight()-1);
        gc.lineTo(anomalies.getWidth()-1,anomalies.getHeight()-1);
        gc.stroke();
    }

    public void chartGraphsSettings(){
        firstCorrelated.getXAxis().setTickLabelsVisible(false);
        firstCorrelated.getXAxis().setTickMarkVisible(false);
        firstCorrelated.setCreateSymbols(false);
        secondCorrelated.getXAxis().setTickLabelsVisible(false);
        secondCorrelated.getXAxis().setTickMarkVisible(false);
        secondCorrelated.setCreateSymbols(false);
    }

    public void initialize() {
        features.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Platform.runLater(() -> {
                    changingFeatureHandler(newValue.toString());
                });
            }
        });
        chartGraphsSettings();
        initAnomaliesGraph();
        TimeStemp.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(() -> {
                series.getData().add(new XYChart.Data<>(newValue.toString(), timeSeries.values.get(index1).get(newValue.intValue())));
                correlatedSeries.getData().add(new XYChart.Data<>(newValue.toString(), timeSeries.values.get(index2).get(newValue.intValue())));
                });
            }
        });
    }
}
