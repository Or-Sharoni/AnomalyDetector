package ptm1.test;

import java.util.ArrayList;
import java.util.List;

public class SimpleAnomalyDetector implements TimeSeriesAnomalyDetector {
	public SimpleAnomalyDetector() {
		this.newCorrelated = new ArrayList<CorrelatedFeatures>();
	}

	List<CorrelatedFeatures> newCorrelated;
	@Override
	public void learnNormal(TimeSeries ts) {
		//this.newCorrelated = new ArrayList<CorrelatedFeatures>();
		int size = ts.features.size();
		float correlation;
		for (int i = 0 ; i < size - 1; i++)
		{
			for (int j = i+1 ; j < size ; j++)
			{
				float[] x = new float[ts.data.get(i).size()];
				float[] y = new float[ts.data.get(j).size()];
				int index1=0;
				int index2=0;
				for (Float f : ts.data.get(i)) {
					x[index1++] = (f != null ? f : Float.NaN);
				}
				for (Float f : ts.data.get(j)) {
					y[index2++] = (f != null ? f : Float.NaN);
				}
				correlation = StatLib.pearson(x, y);
				if (correlation > ts.myCorr) {
					Point[] points = new Point[ts.data.get(i).size()];
					for (int k=0;k<ts.data.get(i).size();k++)
						points[k] = new Point(x[k],y[k]);

					Line newLine = StatLib.linear_reg(points);
					float treshold = 0;

					for (int k=0;k<ts.data.get(i).size();k++)
					{
						float temp = StatLib.dev(points[k], newLine);
						if (temp > treshold)
							treshold = temp;
					}
					this.newCorrelated.add(new CorrelatedFeatures(ts.features.get(i),ts.features.get(j),correlation,newLine,(float)(treshold + 0.029)));
				}

			}
		}
	}


	@Override
	public List<AnomalyReport> detect(TimeSeries ts) {

		List<AnomalyReport> newAnomalyReport = new ArrayList<AnomalyReport>();
		int ListCorrelatedSize = this.newCorrelated.size();
		int time = 1;
		for (int i=0;i<ListCorrelatedSize;i++) {
			String F1 = this.newCorrelated.get(i).feature1;
			String F2 = this.newCorrelated.get(i).feature2;

			int index1 = ts.features.indexOf(F1);
			int index2 = ts.features.indexOf(F2);

			float[] x = new float[ts.data.get(index1).size()];
			float[] y = new float[ts.data.get(index2).size()];
			int p1=0;
			int p2=0;
			for (Float f : ts.data.get(index1)) {
				x[p1++] = (f != null ? f : Float.NaN);
			}
			for (Float f : ts.data.get(index2)) {
				y[p2++] = (f != null ? f : Float.NaN);
			}

			Point[] points = new Point[ts.data.get(index1).size()];
			for (int k=0;k<ts.data.get(index1).size();k++)
				points[k] = new Point(x[k],y[k]);

			Line thisLine = this.newCorrelated.get(i).lin_reg;

			for (int k=0;k<ts.data.get(index1).size();k++) {
				float det = StatLib.dev(points[k], thisLine);
				if (det > this.newCorrelated.get(i).threshold) {
					String chars = F1+"-"+F2;
					AnomalyReport AR = new AnomalyReport(chars,time);
					newAnomalyReport.add(AR);
				}
				time++;
			}
			time=1;
		}
		return newAnomalyReport;
	}

	public List<CorrelatedFeatures> getNormalModel(){
		return newCorrelated;
	}
}
