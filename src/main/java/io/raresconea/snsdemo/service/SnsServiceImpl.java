package io.raresconea.snsdemo.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.*;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.util.StringUtils;
import io.raresconea.snsdemo.dto.*;
import io.raresconea.snsdemo.helper.SNSMessageFilterPolicy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SnsServiceImpl implements SnsService {
    private final AmazonSNSClient amazonSNSClient;
    private final AmazonSQSClient amazonSQSClient;

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
            if (SubscriptionProtocol.SQS.equals(subscription.getSubscriptionProtocol())) {
                return Topics.subscribeQueue(amazonSNSClient, amazonSQSClient, subscription.getArn(), subscription.getEndpoint());
            }

            return amazonSNSClient.subscribe(subscribeRequest).getSubscriptionArn();
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

        if (!StringUtils.isNullOrEmpty(topicMessage.getAttributeName()) && !StringUtils.isNullOrEmpty(topicMessage.getAttributeValue())) {
            MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
            messageAttributeValue.setDataType("String");
            messageAttributeValue.setStringValue(topicMessage.getAttributeValue());

            publishRequest.addMessageAttributesEntry(topicMessage.getAttributeName(), messageAttributeValue);
        }

        try {
            amazonSNSClient.publish(publishRequest);
            return "Message published successfully!";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "Error ocured! Please try again later";
        }
    }

    @Override
    public String applyFilterPolicies(SubscriptionFilterDto subscriptionFilter) {
        SNSMessageFilterPolicy fp = new SNSMessageFilterPolicy();
        fp.addAttribute(subscriptionFilter.getAttributeName(), subscriptionFilter.getAttributeValue());

        try {
            fp.apply(amazonSNSClient, subscriptionFilter.getSubscriptionArn());

            return "Subscription modified successfully";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "Error ocured! Please try again later";
        }
    }
}
