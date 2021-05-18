package View.Graphs;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;

public class GraphsController {

    @FXML LineChart firstCorrelated;
    @FXML LineChart secondCorrelated;
    @FXML ScatterChart anomalies;

    public GraphsController() {}
}
