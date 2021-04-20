package ptm1.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

	public interface ClientHandler{
		public void communicate(InputStream is, OutputStream os);
	}

	volatile boolean stop;
	public Server() {

		stop=false;
	}
	
	
	private void startServer(int port, ClientHandler ch) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			server.setSoTimeout(1000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		while (!stop) {
			Socket client = null;
			try {
				client = server.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				ch.communicate(client.getInputStream(), client.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// runs the server in its own thread
	public void start(int port, ClientHandler ch) {

		new Thread(()->startServer(port,ch)).start();
	}
	
	public void stop() {

		stop=true;
	}
}
