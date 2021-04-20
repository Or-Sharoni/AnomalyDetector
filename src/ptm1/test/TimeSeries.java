package ptm1.test;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class TimeSeries {

	public ArrayList<ArrayList<Float>> data;
	public ArrayList<String> features;
	public int numberOfRows;
	public double myCorr = 0.9;

	public TimeSeries(String csvFileName) {
		Scanner scan = null;
		try {
			scan = new Scanner(new BufferedReader(new FileReader(csvFileName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String strFeatures = scan.next();
		features = new ArrayList<String>(Arrays.asList(strFeatures.split(",")));
		int numOfFeatures = features.size();


		numberOfRows = numOfFeatures;
		data = new ArrayList<ArrayList<Float>>();
		for(int i = 0; i < numOfFeatures; i++) {
			ArrayList<Float> row = new ArrayList<Float>();
			data.add(row);
		}
		float floatTemp;
		Scanner strReader;
		while(scan.hasNextLine()) {
			String lineFeatures = scan.nextLine();
			strReader = new Scanner(lineFeatures);
			strReader.useDelimiter(",");
			for(int index = 0; index <numOfFeatures; index++) {
				if(strReader.hasNext()) {
					floatTemp = strReader.nextFloat();
					data.get(index).add(floatTemp);

				}
			}
			strReader.close();
		}

		scan.close();
	}
}
