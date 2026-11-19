/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.josephmoiselle.advancedprogrammingcaclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

/**
 *
 * @author Joseph Moiselle 24308453
 */
public class Main {

    public static void main(String[] args) {
       
        int port = 5000;
        
        String addr = JOptionPane.showInputDialog("Enter the IP Address you wish to connect to:");
        System.out.println("Attempting to connect to '" + addr + "' on port '" + port + "'");
        
        try (Socket s = new Socket(addr, port);
                BufferedReader serverInput = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter serverOutput = new PrintWriter(s.getOutputStream(), true);
                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))) {
                
                    System.out.println("Client connected to server");
                    
                    String message;
                    
                    while ((message = keyboard.readLine()) != null) {
                        
                        serverOutput.println(message);
                        String reply = serverInput.readLine();
                        
                        if (reply == null) {
                            
                            System.out.println("Server closed the connection");
                            break;
                            
                        }
                        
                        System.out.println("Server: " + reply);
                        if (reply.equals("TERMINATE")) {
                            
                            break;
                            
                        }
                    }
                    
                } catch (UnknownHostException e) {
                        
                        System.out.println("Unknown host: " + e.getMessage());
                        
                } catch (IOException e) {
                        
                        System.out.println("Connection error: " + e.getMessage());
                        
                }
    }
}
