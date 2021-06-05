package View;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import View.Feature;

public class Properties{

    public int hertzRate;
    public int port;
    public String ip;
    public String learnNormalFile;
    public HashMap<String,Feature> map;

    public Properties(){ map = new HashMap<>();}
    public void set(File file){
        try {
            XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file.getPath())));
            Properties properties =(Properties) decoder.readObject();
            this.map = properties.map;
            this.port = properties.port;
            this.ip = properties.ip;
            this.hertzRate = properties.hertzRate;
            this.learnNormalFile = properties.learnNormalFile;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createXMLFile(){
        XMLEncoder encoder = null;
        try{
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("./src/test.xml")));
            encoder.writeObject(this);
        }catch (Exception e){e.printStackTrace();}
        encoder.close();
    }


    public HashMap getMap(){ return this.map;}
    public void setPort(int port){ this.port = port;}
    public void setIp(String ip){ this.ip = ip;}
    public void setHertzRate(int hertzRate){ this.hertzRate = hertzRate;}
    public void setLearnNormalFile(String path){this.learnNormalFile = path;}

    public boolean validition(){
        for(Map.Entry<String,Feature> entry: map.entrySet()){
            System.out.println(entry.getValue().featureName);
        }
        return true;
    }
}
