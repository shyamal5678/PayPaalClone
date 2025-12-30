package com.paypal.notification_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.paypal.notification_service.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    private final ObjectMapper objectMapper;

    public NotificationConsumer(NotificationRepository notificationRepository,ObjectMapper objectMapper){
        this.notificationRepository=notificationRepository;
        this.objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        //not in milliseconds but in dateTimeFormat
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    @KafkaListener(topics = "${kafka.transaction.topic}", groupId = "${kafka.transaction.group}")
    public void listner(String message){}

}
