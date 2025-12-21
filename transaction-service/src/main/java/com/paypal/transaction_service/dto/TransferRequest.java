package com.paypal.transaction_service.dto;

public record TransferRequest(String senderName, String receiverName,Double amount) {

}
