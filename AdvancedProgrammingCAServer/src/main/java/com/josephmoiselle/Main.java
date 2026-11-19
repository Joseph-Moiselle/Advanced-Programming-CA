/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.josephmoiselle;

import java.net.*;
import java.io.IOException;
import com.josephmoiselle.Stores.ticketStore;

/**
 *
 * @author Joseph Moiselle 24308453
 */

// THIS IS THE SERVER PROJECT
public class Main {

    public static void main(String[] args) {
        
        ticketStore store = new ticketStore();
        
        try (ServerSocket ss = new ServerSocket (5000)) {
            
            System.out.println("Server started. \n Awaiting connection");
            
            while (true) {
                
                Socket s = ss.accept();
                
                new Thread(new ClientManager(s, store)).start();
                
            }
        } catch (IOException e) {
            
            System.out.println("Server error: " + e.getMessage());
        }
        
    }
}