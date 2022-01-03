package io.raresconea.snsdemo.controller;

import io.raresconea.snsdemo.dto.SubscriptionFilterDto;
import io.raresconea.snsdemo.dto.TopicDto;
import io.raresconea.snsdemo.dto.TopicSubscriptionDto;
import io.raresconea.snsdemo.dto.TopicMessageDto;
import io.raresconea.snsdemo.service.SnsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sns/topics")
@AllArgsConstructor
public class SnsController {
    private final SnsService snsService;

    @PostMapping
    public String createTopic(@RequestBody TopicDto topic) {
        return snsService.createTopic(topic);
    }


    @PostMapping("/subscriptions")
    public String subscribe(@RequestBody TopicSubscriptionDto subscription) {
        return snsService.createSubscription(subscription);
    }

    @PostMapping("/subscriptions/filter-policies")
    public String applyFilterPolicies(@RequestBody SubscriptionFilterDto subscriptionFilter) {
        return snsService.applyFilterPolicies(subscriptionFilter);
    }

    @PostMapping("/messages")
    public String publishMessage(@RequestBody TopicMessageDto message) {
        return snsService.publishMessage(message);
    }

}
