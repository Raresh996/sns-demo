package io.raresconea.snsdemo.dto;

import lombok.Data;

@Data
public class TopicSubscriptionDto {
    private String arn;
    private SubscriptionProtocol subscriptionProtocol;
    private String endpoint;
}
