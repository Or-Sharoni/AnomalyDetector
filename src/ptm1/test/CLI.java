package ptm1.test;

import ptm1.test.Commands.Command;
import ptm1.test.Commands.DefaultIO;

import java.util.ArrayList;

public class CLI {

	ArrayList<Command> commands;
	DefaultIO dio;
	Commands c;
	
	public CLI(DefaultIO dio) {
		this.dio=dio;
		c=new Commands(dio); 
		commands=new ArrayList<>();
		commands.add(c.new uploadCSV());
		commands.add(c.new algorithmSettings());
		commands.add(c.new detectAnomalies());
		commands.add(c.new displayResults());
		commands.add(c.new uploadAnomalies());
		commands.add(c.new Exit());
	}
	
	public void start() {
		dio.write("Welcome to the Anomaly Detection Server.\n");
		dio.write("Please choose an option:\n");
		for (Command i : commands) {
			dio.write(i.description);
		}
		int x = (int) dio.readVal();
		while(x!=6){
			commands.get(x-1).execute();
			dio.write("Welcome to the Anomaly Detection Server.\n");
			dio.write("Please choose an option:\n");
			for (Command i : commands) {
				dio.write(i.description);
			}
			x= (int) dio.readVal();

		}


	}
}
