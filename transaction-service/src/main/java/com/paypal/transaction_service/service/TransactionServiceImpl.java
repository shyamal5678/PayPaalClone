package com.paypal.transaction_service.service;

import java.time.LocalDateTime;
import java.util.List;
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
	
	public TransactionServiceImpl(TransactionRepository transactionRepository,ObjectMapper objectMapper) {
		this.objectMapper=objectMapper;
		this.transactionRepository=transactionRepository;
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
		return saved;
	}

	@Override
	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}

}
