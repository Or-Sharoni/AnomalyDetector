package ptm1.test;


import ptm1.test.Commands.DefaultIO;
import ptm1.test.Server.ClientHandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class AnomalyDetectionHandler implements ClientHandler{

	public void AnomalyDetectionHandler(){

	}
	public void communicate(InputStream is, OutputStream os){
		SocketIO socket = new SocketIO(is,os);
		CLI cli = new CLI(socket);
		cli.start();
		socket.close();
	}

	public class SocketIO implements DefaultIO{
		Scanner in;
		PrintWriter out;

		public SocketIO(InputStream is, OutputStream os){
			this.in = new Scanner(is);
			this.out = new PrintWriter(os);
		}
		public String readText() {

			return in.nextLine();
		}

		public void write(String text) {

			out.print(text);
			out.flush();
		}

		public float readVal() {

			return in.nextFloat();
		}

		public void write(float val) {

			out.print(val);
			out.flush();
		}

		public void close() {
			in.close();
			out.close();
		}

	}


}
