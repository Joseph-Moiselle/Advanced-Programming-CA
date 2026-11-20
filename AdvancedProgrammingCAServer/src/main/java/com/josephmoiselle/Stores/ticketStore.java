/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.josephmoiselle.Stores;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Joseph Moiselle 24308453
 */
public class ticketStore {
    
    private final List<Ticket> tickets = new ArrayList<>();
    
    public synchronized String open(String id, String name, String category, String desc) throws InvalidCommandException{
    
        for(Ticket t : tickets){
            
            if (t.id().equalsIgnoreCase(id)) {
                
                throw new InvalidCommandException("ticket ID '" + id + "' already exists");
                
            }
            
        }
        
        tickets.add(new Ticket(id, name, category, desc));
        
        return listFor(name);
        
    }
    
    public synchronized String close(String id) throws InvalidCommandException{
        
        Ticket found = null;
        
        for (Ticket t : tickets) {
        
            if (t.id().equalsIgnoreCase(id)){
                
                found = t;
                break;
                
            }
            
        }
        
        if (found == null) {
        
            throw new InvalidCommandException("ticket ID '" + id + "' does not exist");
            
        }
        
        tickets.remove(found);
        return "Closed ticket '" + found.id() +"'";
        
    }
    
    public synchronized String list(String name){
    
        return listFor(name);
        
    }
    
    private String listFor(String name){
    
        List<String> mine = new ArrayList<>();
        
        for (Ticket t : tickets) {
            
            if (t.name().equalsIgnoreCase(name)){
                
                mine.add(t.id() + " - " + t.name() + " - " + t.type() + " - " + t.desc());
                
            }
            
        }
        
        if (mine.isEmpty()) {
        
            return name + " has no open tickets";
            
            
        }
        
        String singleOrPlural = (mine.size() == 1) ? "ticket" : "tickets";
        
        return name + " has " + mine.size() + " open " + singleOrPlural + ": " + String.join("; ", mine);
        
    }
    
}
