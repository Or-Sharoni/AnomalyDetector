package Algorithms;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class SmallestCircle {
    Circle circle;

    public List<Point> createPoints(TimeSeries ts){
        List<Point> points = new ArrayList<>();
        int size = ts.values.get(0).size();
        for(int i = 0 ; i < size; i++)
            points.add(new Point(ts.values.get(0).get(i),ts.values.get(1).get(i)));
    return points;
    }

    public void learnNormal(TimeSeries ts){
        List<Point> points = createPoints(ts);
        circle = SCE.makeCircle(points);
    }




    public List<AnomalyReport> detect(TimeSeries ts) {
        List<AnomalyReport> anomalyReports = new ArrayList<>();
        List<Point> points = createPoints(ts);
        int index = 0;
        for(Point point: points) {
            if(circle.c.distance(point) > circle.r){
                anomalyReports.add(new AnomalyReport(ts.features.get(0) +"-"+ ts.features.get(1), index));
            }
            index++;

        }
        return anomalyReports;
    }
}
