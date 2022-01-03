package io.raresconea.snsdemo.service;

import io.raresconea.snsdemo.dto.SubscriptionFilterDto;
import io.raresconea.snsdemo.dto.TopicDto;
import io.raresconea.snsdemo.dto.TopicSubscriptionDto;
import io.raresconea.snsdemo.dto.TopicMessageDto;

public interface SnsService {
    String createTopic(TopicDto topic);
    String createSubscription(TopicSubscriptionDto subscription);
    String publishMessage(TopicMessageDto message);
    String applyFilterPolicies(SubscriptionFilterDto subscriptionFilter);
}
