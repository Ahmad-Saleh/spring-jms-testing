package com.example.springjmstesting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import javax.jms.BytesMessage;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@EnableJms
@Configuration
public class UserNotificationsConfigurations {

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                          MessageConverter messageConverter) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setMessageConverter(messageConverter);
        factory.setPubSubDomain(Boolean.TRUE);
        factory.setSubscriptionDurable(Boolean.FALSE);
        factory.setSessionTransacted(Boolean.TRUE);
        factory.setSubscriptionShared(Boolean.TRUE);
        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new MessageConverter() {

            @Override
            public Message toMessage(Object object, Session session) throws MessageConversionException {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object fromMessage(Message message) throws JMSException, MessageConversionException {
                ByteArrayOutputStream baos = readMessageBytes((BytesMessage) message);

                return MessageBuilder.createMessage(baos.toString(StandardCharsets.UTF_8),
                        new MessageHeaders(Collections.singletonMap("headerTest", message.getStringProperty("headerTest"))));

            }
        };
    }

    private static ByteArrayOutputStream readMessageBytes(BytesMessage message) throws JMSException {
        byte[] buffer = new byte[1000];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int size;
        while ((size = message.readBytes(buffer)) != -1) {
            baos.write(buffer, 0, size);
        }
        return baos;
    }

}
