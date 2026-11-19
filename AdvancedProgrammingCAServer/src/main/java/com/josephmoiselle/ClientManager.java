/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.josephmoiselle;
import java.net.Socket;
import java.io.*;
import com.josephmoiselle.Stores.*;

/**
 *
 * @author Joseph Moiselle 24308453
 */
public class ClientManager implements Runnable {
    
    private final Socket socket;
    private final ticketStore ticket;
    
    public ClientManager(Socket socket, ticketStore ticket) {
        
        this.socket = socket;
        this.ticket = ticket;
    }
    
    @Override
    public void run(){
        
        System.out.println("Client connected at: " + socket.getRemoteSocketAddress());
        
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            
            String message;
            
            while ((message = in.readLine()) != null){
                
                if ((message.equals("STOP"))){
                    
                    out.println("TERMINATE");
                    break;
                    
                }
                
                String reply;
                
                try{
                commandParser.Command c = commandParser.parse(message);
                switch (c.action()){
                    
                    case "open" -> reply = ticket.open(c.id(), c.student(), c.category(), c.desc());
                    case "close" -> reply = ticket.close(c.id());
                    default -> reply = ticket.list(c.student());
                }
                } catch (InvalidCommandException e){
                    
                    reply = "Invalid command: " + e.getMessage();
                
                }
                
                out.println(reply);
            
            }
            
        } catch (IOException e) {
            
            System.out.println("Client error: " + e);
            
        } finally {
            
            try{ socket.close(); } catch (IOException ignored) {}
            
            System.out.println("Client Disconnected");
            
        }
    }
    
}
