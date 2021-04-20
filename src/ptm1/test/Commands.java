package ptm1.test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Commands {
	public TimeSeries trainFile;
	public TimeSeries testFile;
	public SimpleAnomalyDetector detector;
	public List<AnomalyReport> report;
	public int numOfLines=0;

	// Default IO interface
	public interface DefaultIO{
		public String readText();
		public void write(String text);
		public float readVal();
		public void write(float val);

		// you may add default methods here
	}

	// implementing standard io
	public class StandardIO implements DefaultIO {
		public String readText() {
			try (Scanner scan = new Scanner(System.in)) {
				String text = scan.nextLine();
				return text;
			}
		}

		public void write(String text) {
			System.out.println(text);
		}

		public float readVal() {
			try (Scanner scan = new Scanner(System.in)) {
				float value = scan.nextFloat();
				return value;
			}
		}

		public void write(float val) {
			System.out.println(val);
		}

		public void close() {
			return;
		}
	}
	// the default IO to be used in all commands
	DefaultIO dio;
	public Commands(DefaultIO dio) {
		this.dio=dio;
	}
	
	// you may add other helper classes here
	
	
	
	// the shared state of all commands
	private class SharedState{
		// implement here whatever you need
		
	}
	
	private  SharedState sharedState=new SharedState();

	
	// Command abstract class
	public abstract class Command{
		protected String description;
		
		public Command(String description) {
			this.description=description;
		}
		
		public abstract void execute();
	}
	
	// implement here all other commands
	public class uploadCSV extends Command{

		public uploadCSV() {

			super("1. upload a time series csv file\n");
		}

		@Override
		public void execute() {
			dio.write("Please upload your local train CSV file.\n");
			String buffer = dio.readText();
			PrintWriter out1 =null;
			try {
				out1 = new PrintWriter(new FileWriter("anomalyTrain.csv"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			PrintWriter out2 = null;
			try {
				out2= new PrintWriter(new FileWriter("anomalyTest.csv"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			while (!(buffer.equals("done"))){
				out1.print(buffer);
				out1.print("\n");
				buffer=dio.readText();
			}
			out1.close();
			trainFile = new TimeSeries("anomalyTrain.csv");
			dio.write("Upload complete.\n");
			dio.write("Please upload your local test CSV file.\n");
			buffer=dio.readText();
			while (!(buffer.equals("done"))){
				out2.print(buffer);
				out2.print("\n");
				buffer=dio.readText();
				numOfLines++;
			}
			out2.close();
			testFile = new TimeSeries("anomalyTest.csv");
			dio.write("Upload complete.\n");
		}
	}

	public class algorithmSettings extends Command{

		public algorithmSettings() {

			super("2. algorithm settings\n");
		}

		@Override
		public void execute() {
			StandardIO sio = new StandardIO();
			dio.write("The current correlation threshold is " + trainFile.myCorr + "\n");
			dio.write("Type a new threshold\n");
			double x = (double) dio.readVal();
			while (x>1 && x<0){
				dio.write("please choose a value between 0 and 1.\n");
				x = (double) dio.readVal();
			}
			trainFile.myCorr = x;
			testFile.myCorr = x;
		}
	}
	public class detectAnomalies extends Command{

		public detectAnomalies() {

			super("3. detect anomalies\n");
		}

		@Override
		public void execute() {
			detector = new SimpleAnomalyDetector();
			detector.learnNormal(trainFile);
			report = detector.detect(testFile);
			dio.write("anomaly detection complete.\n");
		}
	}
	public class displayResults extends Command{

		public displayResults() {

			super("4. display results\n");
		}

		@Override
		public void execute() {
			for (AnomalyReport ar:report){
				dio.write(ar.timeStep);
				dio.write("\t");
				dio.write(ar.description+"\n");
			}
			dio.write("Done.\n");

		}
	}
	public class uploadAnomalies extends Command{

		public uploadAnomalies() {

			super("5. upload anomalies and analyze results\n");
		}

		@Override
		public void execute() {
			dio.write("Please upload your local anomalies file.\n");
			String buff = dio.readText();
			buff =  dio.readText();
			ArrayList<Point> myArr = new ArrayList<Point>();
			ArrayList<Point> newArr = new ArrayList<Point>();
			int i=0;
			float start;
			float tmp;
			float cnt=0;
			while(i<report.size()-2){
				start = report.get(i).timeStep;
				tmp = start;
				while(((start+1)==report.get(i+1).timeStep) && (report.get(i).description.equals(report.get(i+1).description))){
					i++;
					cnt++;
				}
				if(cnt>0){
					Point p = new Point(tmp,tmp+cnt);
					myArr.add(p);
				}
				i += cnt;
			}
			float p = 0;
			float tp = 0;
			float fp = 0;
			int flag = 0;
			int N = numOfLines -1;
			while(!(buff.equals("done"))) {
				p++;
				String[] uplArr = buff.split(",");
				int f1 = Integer.parseInt(uplArr[0]);
				int f2 = Integer.parseInt(uplArr[1]);
				N -= (f2 - f1 + 1);
				newArr.add(new Point((float) f1, (float) f2));
				buff = dio.readText();
			}

			for(Point po:myArr){
				for(Point po1:newArr){
					if((po.x>=po1.x && po.y<=po1.y) || (po.x<=po1.y && po.y>=po1.y) || (po.x<=po1.x && po.y>=po1.x)) {
						tp++;
						flag = 1;
						break;
					}
				}
			if(flag==0){
				fp++;
			}
				flag = 0;
			}

			dio.write("Upload complete.\n");
			dio.write("True Positive Rate: " + (Math.floor((tp/p) * 1000) / 1000) + "\n");
			dio.write("False Positive Rate: " + (Math.floor((fp/N) * 1000) / 1000) + "\n");
		}
	}
	public class Exit extends Command{

		public Exit() {

			super("6. exit\n");
		}

		@Override
		public void execute() {
			return;
		}
	}
}
