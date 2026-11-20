/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.josephmoiselle.advancedprogrammingcaclient;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Joseph Moiselle 24308453
 */
public class SynchronisationTest {
    
    public static void main(String[] args) throws InterruptedException{
        
        int clients = 3;
        CountDownLatch ready = new CountDownLatch(clients);
        CountDownLatch go = new CountDownLatch(1);
        AtomicInteger successes = new AtomicInteger();
        AtomicInteger rejections = new AtomicInteger();
        Thread[] threads = new Thread[clients];
        
        for(int i = 0; i < clients; i++) {
            
            final int n = i + 1;
            threads[i] = new Thread(() ->{
                
                try (Socket s = new Socket("localhost", 5000);
                        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        PrintWriter output = new PrintWriter(s.getOutputStream(), true)){
                    
                    ready.countDown();
                    go.await();
                    
                    output.println("open; T104; Alice; network; Wi-Fi repeatedly disconnects - sent by client " + n);
                    String reply = input.readLine();
                    System.out.println("Client " + n + ": " + reply);
                    
                    if(reply.startsWith("InvalidCommandException")){
                        
                        rejections.incrementAndGet();
                        
                    } else{
                        
                        successes.incrementAndGet();
                        
                    }
                    
                    output.println("Stop");
                    input.readLine();
                    
                } catch (Exception e){
                    
                    System.out.println("Client" +n + " error: " +e);
                    
                }
                
            });
            threads[i].start();
            
        }
        
        ready.await();
        go.countDown();
        for (Thread t : threads) t.join();
        
        System.out.println("Successes: " + successes + "; Rejected: " + rejections);
        
    }
    
}
