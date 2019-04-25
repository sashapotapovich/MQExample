package com.example.webservice;

import com.example.entity.Message;
import com.example.entity.MessageRepository;
import com.example.jms.MessageSendingEndpoint;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MessageController {

    private final String DEAD_LETTER = "DEV.DEAD.LETTER.QUEUE";
    private MessageRepository repository;
    private MessageSendingEndpoint sendingEndpoint;

    public MessageController(MessageRepository repository, MessageSendingEndpoint sendingEndpoint) {
        this.repository = repository;
        this.sendingEndpoint = sendingEndpoint;
    }

    @PostMapping(value = "/send/{queue}")
    public void greeting(@PathVariable String queue, @RequestBody Message message) {
        if (Stream.of(QueueNames.values()).anyMatch(queueNames -> queueNames.getQueueName().equalsIgnoreCase(queue))) {

            if (message != null) {
                repository.save(message);
                sendingEndpoint.sendMessage(queue.toUpperCase(), message.getText());
            }
        } else {
            log.error("Queue with the name - {} was not found", queue);
            sendingEndpoint.sendMessage(DEAD_LETTER, message.getText());
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    private enum QueueNames {
        DEVQUEUE1("DEV.QUEUE.1"),
        DEVQUEUE2("DEV.QUEUE.2"),
        DEVQUEUE3("DEV.QUEUE.3");

        private String queueName;

    }
}