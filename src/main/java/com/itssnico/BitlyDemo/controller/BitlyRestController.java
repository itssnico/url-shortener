package com.itssnico.BitlyDemo.controller;

import com.itssnico.BitlyDemo.model.BitlyRequest;
import com.itssnico.BitlyDemo.service.BitlyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController// shows Spring that this class is the controller of the application
@Slf4j // Simple Logging Facade for Java provides a Java logging API by means of a simple facade pattern.
@RequestMapping("/api/v1") // shoq the path that we're gonna use
public class BitlyRestController {

    @Autowired
    BitlyService bitlyService; // we connected the service with the controller

    @PostMapping("/processBitly")
    public String processBitly(@RequestBody BitlyRequest bitlyRequest){
        String shortURL = bitlyService.getShortURL(bitlyRequest.getLongURL()); // here were getting the longURL to make it short
        return shortURL;
    }

}
