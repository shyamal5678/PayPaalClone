package com.paypal.transaction_service.service;

import java.time.LocalDateTime;
import java.util.List;

import com.paypal.transaction_service.kafka.KafkaEventProducer;
import org.slf4j.*;
import org.springframework.stereotype.Service;

import com.paypal.transaction_service.entity.Transaction;
import com.paypal.transaction_service.repository.TransactionRepository;

import tools.jackson.databind.ObjectMapper;
@Service
public class TransactionServiceImpl implements TransactionService{
	private final Logger LOGGER= LoggerFactory.getLogger(TransactionServiceImpl.class);
	private final TransactionRepository transactionRepository;
	private final ObjectMapper objectMapper;
    private final KafkaEventProducer kafkaEventProducer;
	
	public TransactionServiceImpl(TransactionRepository transactionRepository,ObjectMapper objectMapper,KafkaEventProducer kafkaEventProducer) {
		this.objectMapper=objectMapper;
		this.transactionRepository=transactionRepository;
        this.kafkaEventProducer=kafkaEventProducer;
	}

	@Override
	public Transaction createTransaction(Transaction transaction) {
		LOGGER.info("🚀 Entered createTransaction()");

        Long senderId = transaction.getSenderId();
        Long receiverId = transaction.getReceiverId();
        Double amount = transaction.getAmount();

        Transaction transactionObj = new Transaction();
        transactionObj.setSenderId(senderId);
        transactionObj.setReceiverId(receiverId);
        transactionObj.setAmount(amount);
        transactionObj.setTimestamp(LocalDateTime.now());
        transactionObj.setStatus("SUCCESS");

        LOGGER.info("📥 Incoming Transaction object: " + transactionObj);

        Transaction saved = transactionRepository.save(transactionObj);
        LOGGER.info("💾 Saved Transaction from DB: " + saved);
        try{
        String eventPayload=objectMapper.writeValueAsString(saved);
        String key= String.valueOf(saved.getId());
        kafkaEventProducer.sendTransactionEvent(key,saved);
        LOGGER.info("Kafka message sent",saved.getId());
        } catch (Exception e) {
            LOGGER.error("Kafka message failed",e);
        }
		return saved;
	}

	@Override
	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}

}
