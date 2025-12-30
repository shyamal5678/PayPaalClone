package com.paypal.notification_service.controller;

import com.paypal.notification_service.entity.Notification;
import com.paypal.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notify")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @PostMapping("/create")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification){
        return ResponseEntity.ok(notificationService.sendNotification(notification));
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Notification>> getAllNotificationsByUserId(@PathVariable String userId){
        return ResponseEntity.ok(notificationService.getAllNotificationsByUserId(userId));
    }
}
