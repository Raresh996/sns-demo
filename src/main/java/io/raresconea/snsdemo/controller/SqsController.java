package io.raresconea.snsdemo.controller;

import com.amazonaws.services.sqs.model.Message;
import io.raresconea.snsdemo.dto.ConsumeQueueMessageDto;
import io.raresconea.snsdemo.dto.QueueDto;
import io.raresconea.snsdemo.dto.SqsMessageDto;
import io.raresconea.snsdemo.service.SqsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sqs")
@AllArgsConstructor
public class SqsController {
    private final SqsService sqsService;

    @PostMapping
    public String create(@RequestBody QueueDto queue) {
        return sqsService.create(queue);
    }

    @PostMapping("/messages")
    public String sendMessage(@RequestBody SqsMessageDto sqsMessage) {
        return sqsService.sendMessage(sqsMessage);
    }

    @PostMapping("/messages/consume")
    public List<Message> getMessage(@RequestBody ConsumeQueueMessageDto consumeQueueMessage) {
        return sqsService.getMessage(consumeQueueMessage.getQueueUrl());
    }

}
