package View.Graphs;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.ListView;

public class GraphsController {

    @FXML LineChart firstCorrelated;
    @FXML LineChart secondCorrelated;
    @FXML ScatterChart anomalies;
    @FXML public ListView features;


    public GraphsController() {}
}
