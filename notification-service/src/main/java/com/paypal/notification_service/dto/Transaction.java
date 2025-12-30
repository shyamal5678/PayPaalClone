package com.paypal.notification_service.dto;

import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record Transaction(Long id, Long senderId, Long receiverId,
                          @Positive(message = "Amount must be positive") Double amount, LocalDateTime timestamp, String status) {
}
