package Algorithms;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class TimeSeries {
	
	public ArrayList<ArrayList<Float>> values;
	public ArrayList<String> features;
	public int numberOfRows;
	public ArrayList<ArrayList<Float>> valuesLines;

	public TimeSeries(){
		values = new ArrayList<>();
		features = new ArrayList<>();
		numberOfRows = 0;

	}
	public TimeSeries(String csvFileName) {
		Scanner scan = null;
		try {
			scan = new Scanner(new BufferedReader(new FileReader(csvFileName)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String strFeatures = scan.next();
		//REMOVE!!!!
		features = new ArrayList<String>(Arrays.asList(strFeatures.split(",")));
		int numOfFeatures = features.size();

		
		numberOfRows = numOfFeatures;
		//Building the matrix of the values from the file
		values = new ArrayList<ArrayList<Float>>();
		valuesLines=new ArrayList<ArrayList<Float>>();
		for(int i = 0; i < numOfFeatures; i++) {
			ArrayList<Float> row = new ArrayList<Float>();
			values.add(row);
		}
		float floatTemp;
		int i=0;
		Scanner strReader;
		while(scan.hasNextLine()) {
			String lineFeatures = scan.nextLine();
			strReader = new Scanner(lineFeatures);
			strReader.useDelimiter(",");
			ArrayList<Float> Line = new ArrayList<Float>();
			valuesLines.add(Line);

			for(int index = 0; index <numOfFeatures; index++) {
				if(strReader.hasNext()) {
					floatTemp = strReader.nextFloat();
					values.get(index).add(floatTemp);
					valuesLines.get(i).add(floatTemp);
				}
			}
			i++;
			strReader.close();
		}		
		valuesLines.remove(0);
		scan.close();
	}
}
