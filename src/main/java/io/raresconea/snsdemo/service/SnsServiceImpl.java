package io.raresconea.snsdemo.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import io.raresconea.snsdemo.dto.TopicDto;
import io.raresconea.snsdemo.dto.TopicSubscriptionDto;
import io.raresconea.snsdemo.dto.TopicMessageDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SnsServiceImpl implements SnsService {
    private final AmazonSNSClient amazonSNSClient;

    @Override
    public String createTopic(TopicDto topic) {
        CreateTopicRequest request = new CreateTopicRequest(topic.getName());

        try {
            CreateTopicResult result = amazonSNSClient.createTopic(request);
            return result.getTopicArn();
        } catch (Exception e) {
            log.error(e.getMessage());
            return "Error ocured! Please try again later!";
        }
    }

    @Override
    public String createSubscription(TopicSubscriptionDto subscription) {
        SubscribeRequest subscribeRequest = new SubscribeRequest(
                subscription.getArn(),
                subscription.getSubscriptionProtocol().name,
                subscription.getEndpoint());

        try {
            amazonSNSClient.subscribe(subscribeRequest);
            return "Please confirm email subscription";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "Error ocured! Please try again later!";
        }
    }

    @Override
    public String publishMessage(TopicMessageDto topicMessage) {
        PublishRequest publishRequest = new PublishRequest(
                topicMessage.getArn(),
                topicMessage.getContent(),
                topicMessage.getSubject());

        try {
            amazonSNSClient.publish(publishRequest);
            return "Message published successfully!";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "Error ocured! Please try again later";
        }
    }
}
