package io.raresconea.snsdemo.dto;

import lombok.Data;

@Data
public class SubscriptionFilterDto {
	String subscriptionArn;
	String attributeName;
	String attributeValue;
}
