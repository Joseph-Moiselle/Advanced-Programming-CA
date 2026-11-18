/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.josephmoiselle.advancedprogrammingcaclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

/**
 *
 * @author Joseph Moiselle 24308453
 */
public class Main {

    public static void main(String[] args) {
        Socket s = null;
        BufferedReader in = null;
        DataOutputStream out = null;
        String addr;
        int port = 5000;
        
        addr = JOptionPane.showInputDialog("Enter the IP you wish to connect to:");
        System.out.println("Attempting to connect to '" + addr + "' on port '" + port + "'");
        
         try {
            //Establish a connection
            s = new Socket(addr, port);
            System.out.println("Connected");
            
            //read output from terminal
            in = new BufferedReader( new InputStreamReader(System.in));
            
            //Send data to server
            out = new DataOutputStream(s.getOutputStream());
            
            String message = "";
            
            //Send messages till "Over" is received
            while (!message.equals("Over")){
                message = in.readLine();
                out.writeUTF(message);
            }
        }
        catch (UnknownHostException e) {
            System.out.println(e);
        } 
        catch (IOException e){
            System.out.println(e);
        } catch (invalidCommandException){
            
        }
    }
}
