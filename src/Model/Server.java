//package Model;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.Socket;
//
//public class Server {
//    private double speed = 1;
//
//    public void displaySimulator() throws IOException, InterruptedException {
//        Socket fg=new Socket("localhost", 5400);
//        BufferedReader in=new BufferedReader(new FileReader("src/reg_flight.csv"));
//        PrintWriter out=new PrintWriter(fg.getOutputStream());
//        String line;
//        while((line=in.readLine())!=null) {
//            out.println(line);
//            out.flush();
//            Thread.sleep((long)(100* speed));
//        }
//        out.close();
//        in.close();
//        fg.close();
//    }
//
//    public void setSpeed(double speed){this.speed = speed;}
//}
