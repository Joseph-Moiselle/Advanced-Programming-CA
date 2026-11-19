/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.josephmoiselle;

import java.net.*;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
//import com.josephmoiselle.Stores.commandParser;

/**
 *
 * @author Joseph Moiselle 24308453
 */
public class Main {

    public static void main(String[] args) {
        
        Socket s = null;
        ServerSocket ss = null;
        DataInputStream in = null;
        ArrayList OpenTix[];
        int pos = 0;
        
        try{
            ss = new ServerSocket(5000);
            System.out.println("Server started, waiting for client");
            System.out.println(System.getProperty("java.version"));
            
            s = ss.accept();
            System.out.println("Client connected");
            System.out.println(System.getProperty("java.version"));
            
            in = new DataInputStream( new BufferedInputStream(s.getInputStream()));
            
            String message = "";
            
            while (!message.equals("STOP")){
                try{
                    message = in.readUTF();
                    System.out.println(message);
                    
                    //commandParser.parse(message);
                } catch (IOException i){
                    System.out.println(i);
                } /*catch (invalidCommand i){
                    System.out.println("The command is invalid, valid commands are: 'open;' 'list;' 'close'");
                }*/
            }
            
            System.out.println("TERMINATE");
            
            s.close();
            in.close();
        } catch (IOException i){
            System.out.println(i);
        }
        
    }
}
