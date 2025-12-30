package com.paypal.transaction_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.transaction_service.entity.Transaction;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaEventProducer {
    private final Logger LOGGER= LoggerFactory.getLogger(KafkaEventProducer.class);
@Value("${kafka.transaction.topic}")
    private String TOPIC;

private final KafkaTemplate<String, Transaction> kafkaTemplate;
private final ObjectMapper objectMapper;
@Autowired
public KafkaEventProducer(KafkaTemplate<String,Transaction> kafkaTemplate, ObjectMapper objectMapper){
    this.kafkaTemplate=kafkaTemplate;
    this.objectMapper=objectMapper;
}

public void sendTransactionEvent(String key, Transaction transaction){
    LOGGER.info("📤 Sending to Kafka → Topic: " + TOPIC + ", Key: " + key + ", Message: " + transaction);
    CompletableFuture<SendResult<String,Transaction>> future= kafkaTemplate.send(TOPIC,key,transaction);
    future.thenAccept(result->{
        RecordMetadata metadata=result.getRecordMetadata();
        LOGGER.info("✅ Kafka message sent successfully! Topic: " + metadata.topic() + ", Partition: " + metadata.partition() + ", Offset: " + metadata.offset());
    }).exceptionally(ex->{
        LOGGER.info("❌ Failed to send Kafka message: " + ex.getMessage());
        ex.printStackTrace();
        return null;
    });
}

}
