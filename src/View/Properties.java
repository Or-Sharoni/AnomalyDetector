package Model;

import java.beans.XMLDecoder;
import java.io.*;

public class Properties{

    public void set(){
        try {
            XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("src/properties.xml")));
            System.out.println((Properties)decoder.readObject());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
