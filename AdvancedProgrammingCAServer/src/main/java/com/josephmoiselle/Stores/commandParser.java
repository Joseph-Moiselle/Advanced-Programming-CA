/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.josephmoiselle.Stores;


/**
 *
 * @author Joseph Moiselle 24308453
 */
public class commandParser {
    
    public record Command(String action, String id, String student, String category, String desc) {}
    
    public static Command parse(String message) throws InvalidCommandException{
        
        String[] parts = message.split(";", -1);
        
        if (parts.length != 5) {
            
            throw new InvalidCommandException("Invalid Command, one or more fields is missing. \n Expected: action;id;student;category;desc");
            
        }
        
        for (int i = 0;  i < parts.length; i ++){
        
            parts[i] = parts[i].trim();
            
        }
        
        String action = parts[0].toLowerCase();
        
        if (!action.equals("open") && !action.equals("close") && !action.equals("list")) {
        
            throw new InvalidCommandException("Unrecognised command. \n Command must be: open, close, list");
        
        }
        
        return new Command (action, parts[1], parts[2], parts[3].toLowerCase(), parts[4]);
        
    }

}
