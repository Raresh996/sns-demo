package io.raresconea.snsdemo.dto;

import lombok.Data;

@Data
public class TopicMessageDto {
    private String arn;
    private String subject;
    private String content;
}
