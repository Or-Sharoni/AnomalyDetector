package Algorithms;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ZScore implements Algorithms{

    public ArrayList<Double> trainingData;
    public ArrayList<String> features;
    public List<AnomalyReport> anomalyReportList;



    public double txCalculator (ArrayList<Float> array , double avg, double S){
        double tx = 0;
        double Zscore = 0;

        for (Float number : array) {
            Zscore = Math.abs(number - avg) / S;
            if(Zscore > tx)
                tx = Zscore;
        }
        return tx;
    }

    public void learnNormal(TimeSeries ts){
        trainingData = new ArrayList<>();
        features = ts.features;

        double avg = 0;
        double S = 0;
        double tx = 0;
        double Zscore = 0;

        for(ArrayList<Float> array: ts.values) {

            //Init new array for each feature
            ArrayList<Float> tempArr = new ArrayList<>();

            for(Float number: array) {

                //Add new number into tempArr for each iterate
                //Calculate the var and avarage of the values
                tempArr.add(number);
                avg = StatLib.avg(tempArr);
                S = Math.sqrt(StatLib.var(tempArr));
                Zscore = Math.abs(number - avg) / S;

                //Check if the Zscore is greater than the current tx
                if(Zscore > tx)
                    tx = Zscore;
            }

            //Iterate the array values and calculate each Z-Score
            //Define the highest Z-Score value into tx
            //Add the highest tx value into the trainingData
            trainingData.add(tx);
            tx = 0;

        }
    }

    public List<AnomalyReport> detect(TimeSeries ts) {

        List<AnomalyReport> reports = new ArrayList<>();

        int time = 1;
        double avg = 0;
        double S = 0;
        double Zscore = 0;
        int index = 0;


        for (ArrayList<Float> array : ts.values) {

            //Init new array for each feature
            ArrayList<Float> tempArr = new ArrayList<>();

            for(Float number: array){


                //Add new number into tempArr for each iterate
                //Calculate the var and avarage of the values
                tempArr.add(number);
                avg = StatLib.avg(tempArr);
                S = Math.sqrt(StatLib.var(tempArr));
                Zscore = Math.abs(number - avg) / S;

                //Check if the Zscore is greater than the tx from the training data
                if(Zscore > trainingData.get(index))
                    reports.add(new AnomalyReport(features.get(index),time));

                //Keep update the timeline
                time++;
            }

            time = 1;
            index++;
        }
        this.anomalyReportList = reports;
        return reports;
    }
}
