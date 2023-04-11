package com.example.springjmstesting;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

@SpringBootApplication
public class SpringJmsTestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJmsTestingApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(StreamBridge streamBridge){
        return args -> {

            streamBridge.send("cbsEvent-out-0", MessageBuilder.withPayload("test")
                    .setHeader("test", UUID.randomUUID().toString())
                    .setHeader("contentType", MediaType.APPLICATION_JSON_VALUE)
                    .build());
        };
    }


}
