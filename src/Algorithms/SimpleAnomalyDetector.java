package Algorithms;

import java.util.List;
import java.util.ArrayList;

public class SimpleAnomalyDetector implements Algorithms {
	public List<CorrelatedFeatures> correlatedList;
	float correlationLimit = (float) 0.94;
	
	
	public Point[] getPointsArray(ArrayList<Float> Array1,ArrayList<Float> Array2) {
		int size = Array1.size();
		Point[] points = new Point[size];
		for(int i = 0; i < size; i++) {
			points[i] = new Point(Array1.get(i),Array2.get(i));
		}
		return points;
		
	}
	@Override
	public void learnNormal(TimeSeries ts) {
		int index = 0;
		correlatedList = new ArrayList<CorrelatedFeatures>();
		int size = ts.values.get(0).size();
		for(int i = 0; i < ts.numberOfRows-1; i++)
			for(int j = i+1; j < ts.numberOfRows; j++) {
				float correlation = Math.abs(StatLib.pearson(ts.values.get(i), ts.values.get(j)));


				//If The correlation is bigger than 0.9 add the parameters into the list
				if(correlation > correlationLimit) {
					//Build the points array
					Point[] points = getPointsArray(ts.values.get(i),ts.values.get(j));
					//Create new line based the two correlated rows
					Line line = StatLib.linear_reg(points);
					//Get the Maximum value of the dev
					float threshold = 0;
					for(int k = 0; k < size; k++) {
						float temp = StatLib.dev(points[k], line);
						if(threshold < temp) {
							threshold = temp;
						}
					}
				correlatedList.add(new CorrelatedFeatures(ts.features.get(i),ts.features.get(j),correlation,line,(float)(threshold + 0.025)));
				}
			}
		} 
		


	@Override
	public List<AnomalyReport> detect(TimeSeries ts) {
		List<CorrelatedFeatures> modle = getNormalModel();
		List<AnomalyReport> anomalyList = new ArrayList<AnomalyReport>();
		for(CorrelatedFeatures line: modle) {
			String description = line.feature1 + "-" + line.feature2;
			int index1 = ts.features.indexOf(line.feature1);
			int index2 = ts.features.indexOf(line.feature2);
			int size = ts.values.get(index1).size();
			Point[] points = getPointsArray(ts.values.get(index1),ts.values.get(index2));
			for(int time = 0; time < size; time++) {
				float temp = StatLib.dev(points[time], line.lin_reg);
				if(line.threshold <= temp) {
					anomalyList.add(new AnomalyReport(description,(long)time+1));
				}
			}
		}

		return anomalyList;
	}
	
	public List<CorrelatedFeatures> getNormalModel(){
		return correlatedList;
	}
}
