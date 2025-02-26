package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public KafkaController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody MessageRequest messageRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        kafkaProducerService.sendMessage("journal-topic", username + ": " + messageRequest.getMessage());
        return "Message sent!";
    }

    // DTO class for request body
    public static class MessageRequest {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
