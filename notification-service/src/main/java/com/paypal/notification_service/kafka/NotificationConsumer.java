package com.paypal.notification_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.paypal.notification_service.entity.Transaction;
import com.paypal.notification_service.entity.Notification;
import com.paypal.notification_service.repository.NotificationRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificationConsumer {
    private final Logger LOGGER= LoggerFactory.getLogger(NotificationConsumer.class);
    private final NotificationRepository notificationRepository;
    private final ObjectMapper objectMapper;
    @Value("${kafka.transaction.topic}")
    private String topicName;

    @Value("${kafka.transaction.group}")
    private String group;

    @PostConstruct
    public void logKafkaConfig() {
        LOGGER.info("Kafka topic = " + topicName);
        LOGGER.info("Kafka group = " + group);
    }

    public NotificationConsumer(NotificationRepository notificationRepository,ObjectMapper objectMapper){
        this.notificationRepository=notificationRepository;
        this.objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        //not in milliseconds but in dateTimeFormat
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    @KafkaListener(topics = "transaction-dev",
            groupId = "${kafka.transaction.group}")
    public void listener(Transaction transaction) throws JsonProcessingException {
        System.out.println("📥 Received transaction: " + transaction);

        Notification notification = new Notification();
        notification.setUserId(String.valueOf(transaction.getSenderId()));
        notification.setMessage("💰 ₹" + transaction.getAmount() + " received from user " + transaction.getSenderId());
        notification.setSentAt(LocalDateTime.now());

        notificationRepository.save(notification);
        System.out.println("✅ Notification saved: " + notification);
    }

}
