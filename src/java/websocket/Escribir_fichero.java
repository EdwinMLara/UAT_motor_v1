/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 *
 * @author emlar
 */
public class Escribir_fichero {
     String Path;
    public Escribir_fichero(String Path){
        this.Path = Path;
    }
    
    void Escrbir(String msj){
        FileWriter fichero = null;
        PrintWriter pw = null;
        
        try{
            fichero = new FileWriter(Path);
            pw =  new PrintWriter(fichero);
            pw.print(msj);
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(null != fichero)
                    fichero.close();
            }
            catch(Exception e2){
                e2.printStackTrace();
            }
        }
        
    }
}
