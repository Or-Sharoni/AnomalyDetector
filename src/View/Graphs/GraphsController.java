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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GraphsController {

    @FXML LineChart firstCorrelated;
    @FXML LineChart secondCorrelated;
    @FXML Canvas anomalies;
    @FXML public ListView features;
    List<AnomalyReport> anomaliesReports;

    GraphicsContext gc;
    TimeSeries timeSeries;
    StringProperty selectedFeature;
    StringProperty correlatedFeature;
    Object algorithm;
    public IntegerProperty TimeStemp;
    ChangeListener listener;

    XYChart.Series series;
    XYChart.Series correlatedSeries;

    int index1;
    int index2;
    float maxX;
    float maxY;

    public GraphsController() {
        selectedFeature = new SimpleStringProperty();
        correlatedFeature = new SimpleStringProperty();
        TimeStemp = new SimpleIntegerProperty();
        series = new XYChart.Series<>();
        correlatedSeries = new XYChart.Series<>();
        listener = null;
        anomaliesReports = new ArrayList<>();
    }

    public void setAlgorithm(Object algorithm){
        this.algorithm = algorithm;
        if(algorithm instanceof SimpleAnomalyDetector)
            anomaliesReports = ((SimpleAnomalyDetector) algorithm).anomalyReportList;

        if(algorithm instanceof Hybrid)
            anomaliesReports = ((Hybrid) algorithm).anomalyReportList;

        if(algorithm instanceof ZScore)
            anomaliesReports = ((ZScore) algorithm).anomalyReportList;
    }


    public void displayGraphs() {
        series = new XYChart.Series<>();
        correlatedSeries = new XYChart.Series<>();

        maxX = Math.max(Math.abs(timeSeries.getMaxVal(selectedFeature.getValue())) , Math.abs(timeSeries.getMinVal(selectedFeature.getValue())));
        maxY = Math.max(Math.abs(timeSeries.getMaxVal(correlatedFeature.getValue())) , Math.abs(timeSeries.getMinVal(correlatedFeature.getValue())));
        gc.setFill(Color.GRAY);

        boolean flag = false;
        for(Integer time=0;time<TimeStemp.getValue();time++){
            flag = false;
            double pos1 = timeSeries.values.get(index1).get(time);
            double pos2 = timeSeries.values.get(index2).get(time);
            double X = pos1/maxX*(anomalies.getWidth()/2) + (anomalies.getWidth() / 2);
            double Y = (anomalies.getHeight()/2) - pos2/maxY*(anomalies.getHeight()/2);
            series.getData().add(new XYChart.Data<>(time.toString(), pos1));
            correlatedSeries.getData().add(new XYChart.Data<>(time.toString(), pos2));

            for(AnomalyReport anomalyReport: anomaliesReports) {
                if (anomalyReport.timeStep == (long)time && (anomalyReport.description.equals(selectedFeature.getValue() + "-" + correlatedFeature.getValue())  || anomalyReport.description.equals(correlatedFeature.getValue() + "-" + selectedFeature.getValue()))) {
                    gc.setFill(Color.RED);
                    gc.fillOval(X, Y, 4, 4);
                    flag = true;
                }
            }
            if(!flag) {
                gc.setFill(Color.GRAY);
                gc.fillOval(X , Y, 3, 3);
            }
            gc.stroke();
        }
        firstCorrelated.getData().add(series);
        secondCorrelated.getData().add(correlatedSeries);

    }


    public void setTimeSeiries(TimeSeries timeSeries){ this.timeSeries = timeSeries; }
    public void changingFeatureHandler(String newFeature){
        if(listener != null)
            TimeStemp.removeListener(listener);
        selectedFeature.setValue(newFeature);
        //Clear the previous data
        firstCorrelated.getData().clear();
        secondCorrelated.getData().clear();
        gc.clearRect(0,0,anomalies.getWidth(),anomalies.getHeight());
        initAnomaliesGraph();
        CorrelatedFeatures correlatedFeatures  = null;
        //Print out the graphs data
        index1 = timeSeries.features.indexOf(newFeature);
        if(algorithm instanceof Hybrid)
            correlatedFeatures = ((Hybrid) algorithm).maxCorellation(timeSeries,timeSeries.values.get(index1),newFeature,index1);


        if(correlatedFeatures.feature2 != null) {
            index2 = timeSeries.features.indexOf(correlatedFeatures.feature2);
            correlatedFeature.setValue(correlatedFeatures.feature2);
        }

        displayGraphs();
        listener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(() -> {
                    double X = timeSeries.values.get(index1).get(TimeStemp.getValue())/maxX*(anomalies.getWidth()/2)+(anomalies.getWidth()/2);
                    double Y = (anomalies.getHeight()/2) - timeSeries.values.get(index2).get(TimeStemp.getValue())/maxY*(anomalies.getHeight()/2);
                    boolean flag = false;
                    series.getData().add(new XYChart.Data<>(newValue.toString(), timeSeries.values.get(index1).get(newValue.intValue())));
                    correlatedSeries.getData().add(new XYChart.Data<>(newValue.toString(), timeSeries.values.get(index2).get(newValue.intValue())));
                    for(AnomalyReport anomalyReport: anomaliesReports) {
                        if (anomalyReport.timeStep == newValue.longValue() && (anomalyReport.description.equals(selectedFeature.getValue() + "-" + correlatedFeature.getValue())  || anomalyReport.description.equals(correlatedFeature.getValue() + "-" + selectedFeature.getValue()))) {
                            gc.setFill(Color.RED);
                            gc.fillOval(X, Y, 4, 4);
                            flag = true;
                        }
                    }
                    if(flag == false) {
                        gc.setFill(Color.GRAY);
                        gc.fillOval(X, Y, 3, 3);
                    }
                    gc.stroke();
                });
            }
        };

        TimeStemp.addListener(listener);
    }

    public CorrelatedFeatures findMax(List<CorrelatedFeatures> correlatedFeaturesList){
        CorrelatedFeatures max = correlatedFeaturesList.get(0);
        for(CorrelatedFeatures cf : correlatedFeaturesList)
            if(cf.feature1.equals(selectedFeature))
                max = cf.corrlation > max.corrlation ? cf : max;
        return max;
    }
    public void initAnomaliesGraph(){
        gc = anomalies.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.beginPath();
        gc.moveTo(anomalies.getWidth()/2,0);
        gc.lineTo(anomalies.getWidth()/2,anomalies.getHeight());
        gc.moveTo(0,(anomalies.getHeight())/2);
        gc.lineTo(anomalies.getWidth(),(anomalies.getHeight())/2);
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
                    changingFeatureHandler(newValue);
                });
            }
        });
        chartGraphsSettings();
        initAnomaliesGraph();
    }
}
