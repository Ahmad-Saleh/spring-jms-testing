package com.example.springjmstesting;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class Listener {


    @JmsListener(destination = "messagesToCbs", containerFactory = "jmsListenerContainerFactory")
    public void listen(Message<String> message){
        System.out.println("payload: " + message.getPayload());
        System.out.println("header: " + message.getHeaders().get("headerTest"));
    }
}
