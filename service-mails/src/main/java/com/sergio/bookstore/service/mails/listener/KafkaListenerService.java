package com.sergio.bookstore.service.mails.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
public class KafkaListenerService {

    @Bean
    public Consumer<KStream<Object, String>> listenMessages() {
        return input -> {
            input.foreach((key, value) -> {
                try {
                    var msgParts = value.split(":");
                    if (msgParts.length >= 2) {
                        log.info("Processing {} with content {}", msgParts[0], msgParts[1]);
                        // Send email logic here...
                    }
                    // BUG: Silently ignores messages that don't match expected format
                } catch (Exception e) {
                    // BUG: Swallowing exception silently
                }
            });
        };
    }
}
