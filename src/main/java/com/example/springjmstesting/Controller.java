package com.example.springjmstesting;

import org.jgroups.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class Controller {

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping("/test")
    public void sendMessage(){
        streamBridge.send("cbsEvent-out-0", MessageBuilder.withPayload("test " + UUID.randomUUID().toString())
                .setHeader("headerTest", UUID.randomUUID().toString())
                .setHeader("contentType", MediaType.APPLICATION_JSON_VALUE)
                .build());
    }
}
