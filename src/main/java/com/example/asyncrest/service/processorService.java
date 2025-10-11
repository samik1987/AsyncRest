package com.example.asyncrest.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

@Service
public class processorService {

    @Async
    public CompletableFuture<String> processExecution() throws InterruptedException {
        System.out.println("Start execution of async service -" + Thread.currentThread().getName()+" "+ this.getCurrentDateTime());
        Thread.sleep(5000);
        System.out.println("End execution of async service -" + Thread.currentThread().getName()+" "+ this.getCurrentDateTime());
        return CompletableFuture.completedFuture("Response after 5 seconds (Async)");
    }

    public void syncProcessExecution() throws InterruptedException {

        System.out.println("Start execution of sync service -" + Thread.currentThread().getName());
        Thread.sleep(5000);

    }

    public String getCurrentDateTime() {
        // Get current date-time
        LocalDateTime now = LocalDateTime.now();

        // Format date-time for readability
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Print to console
        //System.out.println("Current Date-Time: " + now.format(formatter));

        // Return to API response
        return " Date-Time: " + now.format(formatter);
    }
}
