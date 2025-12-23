package com.sergio.bookstore.service.mails.listener;

import com.sergio.bookstore.service.mails.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaListenerService {

    private final EmailService emailService;

    @Bean
    public Consumer<KStream<Object, String>> listenMessages() {
        return input -> {
            input.foreach((key, value) -> {
                try {
                    var msgParts = value.split(":");
                    if (msgParts.length >= 2) {
                        String action = msgParts[0].trim();
                        String username = msgParts[1].trim();
                        log.info("Processing {} for user {}", action, username);
                        
                        if ("user.creation".equals(action)) {
                            emailService.sendWelcomeEmail(username);
                        } else if ("password.reset".equals(action)) {
                            emailService.sendPasswordResetEmail(username);
                        }
                    }
                    // BUG: Silently ignores messages that don't match expected format
                } catch (Exception e) {
                    // BUG: Swallowing exception silently
                }
            });
        };
    }
}
