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
    
        return null;
        
    }
    
    public synchronized String close(String id) throws InvalidCommandException{
    
        return null;
        
    }
    
    public synchronized String list(String name){
    
        return listFor(name);
        
    }
    
    private String listFor(String name){
    
        return null;
        
    }
    
}
