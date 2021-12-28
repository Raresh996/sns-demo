package io.raresconea.snsdemo.service;

import com.amazonaws.services.sqs.model.Message;
import io.raresconea.snsdemo.dto.QueueDto;
import io.raresconea.snsdemo.dto.SqsMessageDto;

import java.util.List;

public interface SqsService {
    String create(QueueDto queue);
    String sendMessage(SqsMessageDto snsMessage);
    List<Message> getMessage(String queueUrl);
}
