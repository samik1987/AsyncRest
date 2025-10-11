package com.example.asyncrest.controller;

import com.example.asyncrest.service.processorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class HomeController {

    @Autowired
    private processorService service;

    @GetMapping("/sync")
    public String getSyncResponse() throws InterruptedException {
        System.out.println("Request thread entered to sync controller -" + Thread.currentThread().getName() +" "+ service.getCurrentDateTime());
        service.syncProcessExecution();
        System.out.println("Request thread End at sync controller -" + Thread.currentThread().getName()+" "+ service.getCurrentDateTime());
        return "Response from sync controller after 5 Sec -"+ Thread.currentThread().getName() ;
    }


    @GetMapping("/async")
    public CompletableFuture<String> getAsyncResponse() throws InterruptedException {
        System.out.println("Request thread entered to Async controller -" + Thread.currentThread().getName() +" "+ service.getCurrentDateTime());
        CompletableFuture<String> res= service.processExecution();
        System.out.println("Request thread End at Async controller -" + Thread.currentThread().getName()+" "+ service.getCurrentDateTime());
        return res ;
    }
}
