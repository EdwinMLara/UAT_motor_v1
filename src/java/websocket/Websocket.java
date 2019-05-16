/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONArray;
import org.json.JSONException;

@ServerEndpoint("/Websocket")
public class Websocket {
    static List<String> list_Temperatura = new ArrayList<>();
    static List<String> list_Humedad = new ArrayList<>();
    static List<String> list_Temperatura_aux = new ArrayList<>();
    static List<String> list_Humedad_aux = new ArrayList<>();
    List<String> list_Presion = new ArrayList<>();
    final static String path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\senal_websocket\\web\\archivo.txt";
    final static String current_path = "C:\\Users\\emlar\\OneDrive\\Documentos\\NetBeansProjects\\senal_websocket\\web\\current_archivo.txt";
    static Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());
    static int count=0;
    
    
    @OnOpen
    public void onOpen(Session user){
        users.add(user);
        System.out.println("Conected: " + user.getId());
    }
    
    @OnMessage
    public void onMessage(String onmessage) throws IOException, JSONException{
                //System.out.println(onmessage);
                if(onmessage.indexOf(":")>0){
                    send_Message(onmessage);   
                }
                   
                if(Manipulacion_Datos_Listas.isJSONValid(onmessage)){
                    JSONArray array = new JSONArray(onmessage);
                    String str = array.get(0).toString();
                    
                    switch (str){
                        case "Temperatura":
                            for (int i=1;i<array.length();i++){
                                list_Temperatura_aux.add(array.get(i).toString());
                            }
                            llenar_listas_aux(list_Temperatura,list_Temperatura_aux);
                            break;
                        case "Humedad":
                            for (int i=1;i<array.length();i++){
                                list_Humedad_aux.add(array.get(i).toString());
                            }
                            llenar_listas_aux(list_Humedad,list_Humedad_aux);
                            break;
                        case "Presion":
                            break;
                        default:
                            System.out.println("No hay coinsidencias");
                    }
                    
                    if(!list_Temperatura_aux.isEmpty() && !list_Humedad_aux.isEmpty()){
                        guardar_datos(list_Temperatura_aux,list_Humedad_aux,current_path);
                        list_Temperatura_aux.clear();
                        list_Humedad_aux.clear();
                    }
        
                }
                
                if(onmessage.equals("Temperatura Fin") || onmessage.equals("Humedad Fin") ){
                    count = count + 1;
                    if(count == 2){
                        System.out.println("Termino");
                        count = 0;
                        guardar_datos(list_Temperatura,list_Humedad,path);
                        list_Temperatura.clear();
                        list_Humedad.clear();
                    }
                }else{
                    send_Message(onmessage);
                }
    }
    
    public void guardar_datos(List<String> list_Temperatura,List<String> list_Humedad,String path){
        Manipulacion_Datos_Listas mdl = new Manipulacion_Datos_Listas(list_Temperatura,list_Humedad);
        String str = mdl.Crear_cadena_escritura();
        Escribir_fichero fichero = new Escribir_fichero(path);
        fichero.Escrbir(str);
        
    }
    
    
    public String buildJson (String message){
        JsonObject json = Json.createObjectBuilder().add("message",message).build();
        StringWriter stringwriter = new StringWriter();
        try(JsonWriter jsonwriter = Json.createWriter(stringwriter)){
            jsonwriter.write(json);
        }
        System.out.println(stringwriter.toString());
        return stringwriter.toString();
    }
    
    public static void send_Message(String onmessage) throws IOException{
        Iterator<Session> interator = users.iterator();
        while(interator.hasNext()){
            interator.next().getBasicRemote().sendText(onmessage);
        }
    }
    
    public void llenar_listas_aux(List<String> list,List<String> list_aux){
        if(list.isEmpty()){
            list_aux.stream().forEach((aux) -> {
                list.add(aux);
            });
        }else{
            list.addAll(list_aux);
        }
    }
    
    @OnClose
    public void handleClose(Session userSession){
        users.remove(userSession);
    }
    
    @OnError
    public void handleError(Throwable t){
        StackTraceElement[] stackTrace = null;
        t.setStackTrace(stackTrace);
    }
    
}
