package io.raresconea.snsdemo.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SubscriptionProtocol {
    EMAIL("email"),
    SMS("sms"),
    SQS("sqs"),
    LAMBDA("lambda");

    public final String name;
}
