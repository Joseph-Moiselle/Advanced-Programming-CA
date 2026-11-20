/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.josephmoiselle.Stores;

import java.util.List;
/**
 *
 * @author Joseph Moiselle 24308453
 */
public class commandParser {
    
    public record Command(String action, String id, String student, String category, String desc) {}
    
    public static Command parse(String message) throws InvalidCommandException{
        
        String[] parts = message.split(";", -1);
        
        if (parts.length !=5) {
            
            throw new InvalidCommandException("Expected 5 fields: action; ticketID; student; category; desc");
            
        }
        
        for (int i = 0; i < parts.length; i++) {
            
            parts[i] = parts[i].trim();
            
        }
        
        String action = parts[0].toLowerCase();
        String id = parts[1];
        String student = parts[2];
        String category = parts[3];
        String desc = parts[4];
        
        switch (action) {
            
            case "open" -> {
                
                if (missing(id) || missing(student) || missing(category) || missing(desc)) {
                    
                    throw new InvalidCommandException("Command 'open' is missing one or more of the following fields: ticketID, student, category, desc");
                    
                }
                
                if (!List.of("account", "network", "software", "other").contains(category)) {
                    
                    throw new InvalidCommandException("Unrecognised category. Categegory must be: account/network/software/other");
                    
                }
                
            }
            
            case "close" -> {
                
                if(missing(id)) {
                    
                    throw new InvalidCommandException("close requires ticketID");
                    
                }
                
            }
            
            case "list" -> {
                
                if (missing(student)) {
                    
                    throw new InvalidCommandException("List requires student name");
                    
                }
                
            }
            
            default -> throw new InvalidCommandException("Unknown command. Command must be open/close/list");
            
        }
        
        return new Command(action, id, student, category, desc);
        
    }
    
    private static boolean missing(String field) {
        
        return field.isEmpty() || field.equals("-");
        
    }

}
