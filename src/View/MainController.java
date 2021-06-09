package View;


import Algorithms.Algorithms;
import Algorithms.SimpleAnomalyDetector;
import Algorithms.ZScore;
import Algorithms.Hybrid;
import Algorithms.TimeSeries;
import View.Bar.Bar;
import View.Bar.BarController;
import View.Graphs.GraphsController;
import View.JoyStick.JoyStickController;
import ViewModel.ViewModel;
import View.ControlPanel.ControlPanelController;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;

import javafx.scene.paint.Color;
import javafx.stage.FileChooser;



import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class MainController {

    private ViewModel viewModel;
    @FXML BarController BarController;
    @FXML GraphsController GraphsController;
    @FXML JoyStickController JoyStickController;
    @FXML ControlPanelController ControlPanelController;
    Properties oldXML;
    public MainController(){
        oldXML = new Properties();
        oldXML.set("src/properties.xml");
    }

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
   public void barBinding() {
       BarController.timeText.bindBidirectional(viewModel.timeText);
       BarController.TimeStemp.bindBidirectional(viewModel.TimeStemp);
       BarController.speed.bindBidirectional(viewModel.speed);
       BarController.pause.setOnAction(e -> viewModel.model.Suspend());
       BarController.play.setOnAction(e -> viewModel.model.Play());
       BarController.stop.setOnAction(e -> viewModel.model.Stop());
       BarController.open.setOnAction(e -> openHandler());
       BarController.loadxml.setOnAction(e -> loadXmlHandler());
       BarController.select.setOnAction(e -> {
           try {
               loadAlgorithmHandler();
           } catch (MalformedURLException malformedURLException) {
               malformedURLException.printStackTrace();
           } catch (ClassNotFoundException classNotFoundException) {
               classNotFoundException.printStackTrace();
           } catch (InstantiationException instantiationException) {
               instantiationException.printStackTrace();
           } catch (IllegalAccessException illegalAccessException) {
               illegalAccessException.printStackTrace();
           }
       });

   }





    public void openHandler() {

        GraphsController.features.getItems().clear();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files only", "*.csv"));
        File file = fileChooser.showOpenDialog(null);
        if (file == null) {
            BarController.result.setText("Failed to load");
            BarController.result.setTextFill(Color.web("red"));
            return;
        }
        viewModel.timeSeries = new TimeSeries(file.getPath());
        Set<String> featuresSet = new HashSet<String>();
        for (String feature : viewModel.timeSeries.features) {
            featuresSet.add(feature);
        }
        for (Map.Entry<String, Feature> entry : oldXML.map.entrySet()) {
            if (!featuresSet.contains(entry.getValue().featureName)) {
                BarController.result.setText("Failed to load\n Please try again");
                BarController.result.setTextFill(Color.web("red"));
                return;
            }
        }
        for (String feature : viewModel.timeSeries.features) {
            GraphsController.features.getItems().add(feature);
        }
        viewModel.model.timeSeries = viewModel.timeSeries;
        viewModel.model.displaySimulator();
        viewModel.model.Stop();
        GraphsController.setTimeSeiries(viewModel.timeSeries);
        viewModel.model.port = oldXML.port;
        viewModel.model.ip = oldXML.ip;
    }


    public void loadAlgorithmHandler() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Object algor = null;
        BarController.result.setText("");
        String className;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Class Files only", "*.class"));
        File file = fileChooser.showOpenDialog(null);
        if(file == null) {
            BarController.result.setText("Failed to load");
            BarController.result.setTextFill(Color.web("red"));
            return;
        }
        // load class directory
        className = file.getParentFile().getName() + "." + file.getName().substring(0,file.getName().indexOf("."));
        URL[] url = new URL[1];
        // change this path to your local one
        url[0] = new URL("file://" + file.getParentFile().getParent() + "/");
        URLClassLoader urlClassLoader = new URLClassLoader(url);
        Class<?> c = null;
            c = urlClassLoader.loadClass(className);
        // create an Algorithms instance
            algor = c.newInstance();
        BarController.result.setText("Load Successfully!");
        BarController.result.setTextFill(Color.web("green"));

        System.out.println(algor);

    }

    public void loadXmlHandler(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files only", "*.xml"));
        File file = fileChooser.showOpenDialog(null);
        if(file == null)
            return;
        Properties newXML = new Properties();
        newXML.set(file.getPath());
// test XML
        for(Map.Entry<String,Feature> entry : oldXML.map.entrySet()){
           if(!newXML.map.containsKey(entry.getKey())){
               BarController.result.setText("Failed to load\n Please try again");
               BarController.result.setTextFill(Color.web("red"));
               return;
           }
        }
        BarController.result.setText("Load Successfully!");
        BarController.result.setTextFill(Color.web("green"));
        oldXML = newXML;
        viewModel.speed.setValue(1/oldXML.defaultSpeed);
        BarController.speedPlay.setText(String.valueOf(oldXML.defaultSpeed));
        TimeSeries ts = new TimeSeries(oldXML.learnNormalFile);
        SimpleAnomalyDetector ad = new SimpleAnomalyDetector();
        ZScore zscore = new ZScore();
        Hybrid hybrid = new Hybrid();
        ad.learnNormal(ts);
//        zscore.learnNormal(ts);
//        hybrid.learnNormal(ts);
//        System.out.println(ad.correlatedList);

    }

    public void initialize() {

        BarController.TimeStemp.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(()->{viewModel.model.TimeStemp.setValue(newValue.intValue());});
            }
        });

        BarController.speed.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(()->{viewModel.model.speed.setValue(newValue.doubleValue());});
            }
        });
//        BarController.TimeStemp.addListener((observable, oldValue, newValue) -> {
//            viewModel.model.TimeStemp.setValue(newValue.intValue());
//        });
//
//        BarController.speed.addListener((observable, oldValue, newValue) -> {
//            viewModel.model.speed.setValue(newValue.doubleValue());
//        });
    }
}
