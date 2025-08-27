package com.project.kafka_event_flow;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final KafkaProducerService producerService;

    public KafkaController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/publish")
    public String sendMessage(@RequestParam String message) {
        producerService.sendMessage("my-topic", message);
        return "Message sent: " + message;
    }
}

