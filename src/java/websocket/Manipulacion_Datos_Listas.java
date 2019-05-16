/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author emlar
 */
public class Manipulacion_Datos_Listas {
    List<String> list_Temperatura;
    List<String> list_Humedad;
    int numeros_agregar;
    //List<String> list_Presion;
    
    public Manipulacion_Datos_Listas(List <String> list_Temperatura, List <String> list_Humedad){
        //this.list_Presion = list_Presion;
        this.list_Temperatura = list_Temperatura;
        this.list_Humedad = list_Humedad;
        if(list_Humedad.size() <= list_Temperatura.size()){
            this.numeros_agregar = list_Humedad.size();
        }else{
            this.numeros_agregar = list_Temperatura.size();
        }
    }
    
    String Crear_cadena_escritura(){
        String str1,str2,str_return;
        str1 = "?Temperatura = ";
        str2 = "?Humedad = ";
        
        for (String list_Temperatura1 : list_Temperatura) {
            str1 = str1 + list_Temperatura1 + " ";
            str2 = str2 + list_Temperatura1 + " ";
        }
        str_return = str1 + "\n" + str2 + "\n";
        return str_return;
    }
   
    static public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}
