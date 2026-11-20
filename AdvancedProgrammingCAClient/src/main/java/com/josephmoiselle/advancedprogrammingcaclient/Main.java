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
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

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
                        
                        if(message.trim().toLowerCase().startsWith("import;")) {
                            
                            String url = message.trim().substring(7).trim();
                            importTickets(url, serverInput, serverOutput);
                            continue;
                            
                        }
                        
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
    
    private static void importTickets(String urlText, BufferedReader serverInput, PrintWriter serverOutput){
        
        int imported = 0;
        int skipped = 0;
        HttpURLConnection conn = null;
        
        try{
            
            URL url = URI.create(urlText).toURL();
            
            conn = (HttpURLConnection) url.openConnection();
            
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            
            int code = conn.getResponseCode();
            if (code != 200) {
                
                System.out.println("Import failed: HTTP " + code);
                return;
                
            }
            
            try (BufferedReader file = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                
                String first = file.readLine();
                if (first == null || !first.trim().toLowerCase().startsWith("student=") || first.trim().length() <= 8) {
                    
                    System.out.println("Import failed: first line must be student=[name]");
                    return;
                    
                }
                
                String student = first.trim().substring(8).trim();
                
                String line;
                
                int lineNo = 1;
                
                while ((line = file.readLine()) != null) {
                    
                    lineNo++;
                    
                    if (line.trim().isEmpty()) {
                        
                        continue;
                        
                    }
                    
                    String[] p = line.split(";", -1);
                    if (p.length != 3) {
                        
                        System.out.println("Skipped line " + lineNo + ": expected 3 fields, received " + p.length);
                        skipped++;
                        continue;
                        
                    }
                    
                    String id = p[0].trim(), category = p[1].trim(), desc = p[2].trim();
                    
                    if(id.isEmpty() || category.isEmpty() || desc.isEmpty()){
                        
                        System.out.println("Skipped line " + lineNo + ": empty field encountered");
                        skipped++;
                        continue;
                        
                    }
                    
                    if (!List.of("account", "network", "software", "other").contains(category.toLowerCase())){
                        
                        System.out.println("Skipped line " + lineNo + ": invalid category '" + category + "'");
                        skipped++;
                        continue;
                        
                    }
                    
                    serverOutput.println("open; " + id + "; " + student + "; " + category + "; " + desc);
                    
                    String reply = serverInput.readLine();
                    System.out.println("Server: " + reply);
                    
                    if (reply.startsWith("InvalidCommandException")) {
                        skipped++;
                    } else{
                        
                        imported++;
                    
                    }
                    
                
                }
            
            }
            
        } catch(IllegalArgumentException | java.net.MalformedURLException e) {
            
            System.out.println("Import failed: " + e.getMessage());
            
        } catch (IOException e){
            
            System.out.println("Import failed: " + e.getMessage());
        
        }finally {
            
            if (conn != null) {
                
                conn.disconnect();
                
            }
            
            System.out.println("Imported: " + imported + "; Skipped: " + skipped);
            
        }
        
    }
}
