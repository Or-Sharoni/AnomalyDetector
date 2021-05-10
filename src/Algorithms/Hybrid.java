package Algorithms;

import test.CorrelatedFeatures;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Hybrid {
    ArrayList<Object> algorithms;
    ArrayList<CorrelatedFeatures> correlatedFeatures;

    public TimeSeries createTime(ArrayList<Float> feature1,ArrayList<Float> feature2,String featureName1,String featureName2){
        TimeSeries timeSeries = new TimeSeries();

        timeSeries.values.add(feature1);
        timeSeries.values.add(feature2);
        timeSeries.features.add(featureName1);
        timeSeries.features.add(featureName2);
        timeSeries.numberOfRows += 2;
        return timeSeries;
    }

    public CorrelatedFeatures maxCorellation(TimeSeries ts, ArrayList<Float> value,String featureName,int index1){

        float max = 0;
        int index = 0;
        CorrelatedFeatures returnedValue = new CorrelatedFeatures(featureName,null,0,index1,0);

        for(ArrayList<Float> feature: ts.values){
            if(feature == value) continue;
            float correlation = Math.abs(StatLib.pearson(feature,value));

            if(max < correlation) {
                max = correlation;
                returnedValue.feature2 = ts.features.get(index);
                returnedValue.corrlation = correlation;
                returnedValue.index2 = index;
            }

            index++;
        }
        return returnedValue;

    }


    public ArrayList<CorrelatedFeatures> ListMaker(TimeSeries ts){
        ArrayList<CorrelatedFeatures> correlatedList = new ArrayList<>();
        int index = 0;
        for(ArrayList<Float> feature: ts.values) {
            correlatedList.add(maxCorellation(ts, feature, ts.features.get(index),index));
            index++;
        }
        return correlatedList;
    }

    public void learnNormal(TimeSeries ts){
        correlatedFeatures = ListMaker(ts);
        for(CorrelatedFeatures cr: correlatedFeatures)
            System.out.println(cr.feature1 + " " + cr.feature2 +" "+ cr.corrlation + "  " +cr.index1 + "  "+  cr.index2);

        TimeSeries timeSeries;
        algorithms = new ArrayList<>();


        for(CorrelatedFeatures cf: correlatedFeatures){
            if(cf.corrlation < 0.5){
                timeSeries = new TimeSeries();
                ArrayList<Float> feature = ts.values.get(cf.index1);
                String featureName = ts.features.get(cf.index1);
                timeSeries.values.add(feature);
                timeSeries.features.add(featureName);
                timeSeries.numberOfRows++;
                ZScore zScore = new ZScore();
                zScore.learnNormal(timeSeries);
                algorithms.add(zScore);

            }
            if(cf.corrlation >= 0.5 && cf.corrlation < 0.95 ){
                timeSeries = createTime(ts.values.get(cf.index1),ts.values.get(cf.index2),ts.features.get(cf.index1),ts.features.get(cf.index2));

                SmallestCircle smallestCircle = new SmallestCircle();
                smallestCircle.learnNormal(timeSeries);
                algorithms.add(smallestCircle);
            }


            if(cf.corrlation >= 0.95){
                timeSeries = createTime(ts.values.get(cf.index1),ts.values.get(cf.index2),ts.features.get(cf.index1),ts.features.get(cf.index2));

                SimpleAnomalyDetector simpleAnomalyDetector = new SimpleAnomalyDetector();
                simpleAnomalyDetector.learnNormal(timeSeries);
                algorithms.add(simpleAnomalyDetector);
            }

        }


    }
    public List<AnomalyReport> detect(TimeSeries ts){
        List<AnomalyReport> anomalyReports = new ArrayList<>();
        TimeSeries timeSeries;
        int index = 0;
        for(CorrelatedFeatures cf: correlatedFeatures) {
            if (cf.corrlation < 0.5) {
                timeSeries = new TimeSeries();
                ArrayList<Float> feature = ts.values.get(cf.index1);
                String featureName = ts.features.get(cf.index1);
                timeSeries.values.add(feature);
                timeSeries.features.add(featureName);
                timeSeries.numberOfRows++;

                if(algorithms.get(index) instanceof ZScore)
                    for(AnomalyReport anomalyReport :((ZScore) algorithms.get(index)).detect(timeSeries)){
                        anomalyReports.add(anomalyReport);
                    }
            }
            if (cf.corrlation >= 0.5 && cf.corrlation < 0.95) {
                timeSeries = createTime(ts.values.get(cf.index1),ts.values.get(cf.index2),ts.features.get(cf.index1),ts.features.get(cf.index2));
                if(algorithms.get(index) instanceof SmallestCircle)
                    for(AnomalyReport anomalyReport : ((SmallestCircle) algorithms.get(index)).detect(timeSeries)){
                        anomalyReports.add(anomalyReport);
                    }
            }

            if (cf.corrlation >= 0.95) {
                timeSeries = createTime(ts.values.get(cf.index1),ts.values.get(cf.index2),ts.features.get(cf.index1),ts.features.get(cf.index2));

                if(algorithms.get(index) instanceof SimpleAnomalyDetector)
                      for(AnomalyReport anomalyReport : ((SimpleAnomalyDetector) algorithms.get(index)).detect(timeSeries)){
                          anomalyReports.add(anomalyReport);
                      }

            }
            index++;
        }

    return anomalyReports;
    }
}

