package io.raresconea.snsdemo.service;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import io.raresconea.snsdemo.dto.QueueDto;
import io.raresconea.snsdemo.dto.SqsMessageDto;
import jdk.internal.org.jline.utils.Log;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SqsServiceIml implements SqsService {
    private final AmazonSQSClient amazonSQSClient;

    @Override
    public String create(QueueDto queue) {
        try {
            CreateQueueResult result = amazonSQSClient.createQueue(queue.getName());
            return result.getQueueUrl();
        } catch (Exception e) {
            log.error(e.getMessage());
            return "Error ocured! Please try again later!";
        }
    }

    @Override
    public String sendMessage(SqsMessageDto snsMessage) {
        SendMessageRequest request = new SendMessageRequest()
                .withQueueUrl(snsMessage.getQueueUrl())
                .withMessageBody(snsMessage.getBody())
                .withDelaySeconds(5);

        try {
            amazonSQSClient.sendMessage(request);
            return "Message sent";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "Error ocured! Please try again later!";
        }
    }

    @Override
    public List<Message> getMessage(String queueUrl) {
        return amazonSQSClient.receiveMessage(queueUrl)
                .getMessages();
    }

    @SqsListener("queue-2")
    public void getMessageAsync(String message) {
        Log.info("Message from SQS Queue: " + message);
    }
}
