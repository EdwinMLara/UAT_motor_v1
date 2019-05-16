
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author emlar
 */
public class conecxion {
    String url;
    BufferedReader reader;
    StringBuilder stringBuilder;
    
    public conecxion(String url){
        this.url = url;
    }
    public String conecxion_reader() throws Exception{
        try{
        URL con_url = new URL(url);
        HttpURLConnection con = (HttpURLConnection) con_url.openConnection();
        con.setRequestMethod("GET");
        
        con.setReadTimeout(5*1000);
        con.connect();
        
        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line = null;
        
        while((line = reader.readLine()) != null){
            stringBuilder.append(line).append("\n");
        }
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }finally{
            if (reader != null){
                try{
                    reader.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        
        return stringBuilder.toString();
    }
        
    
     
}
