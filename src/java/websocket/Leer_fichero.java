/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 *
 * @author emlar
 */
public class Leer_fichero {
    String path;
    
    public Leer_fichero(String Path){
        this.path = Path;
    }
    
    String leer(){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String datos = "";
        
        try{
            archivo = new File(path);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            
            String linea;
            
            while((linea = br.readLine())!= null){
                //System.out.println(linea);
                datos = datos + " " + linea; 
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                fr.close();
            }
            catch(Exception e2){
                e2.printStackTrace();
            }
        }
        return datos;
    }
    
    
}
