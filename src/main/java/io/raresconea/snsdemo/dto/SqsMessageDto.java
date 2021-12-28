package io.raresconea.snsdemo.dto;

import lombok.Data;

@Data
public class SqsMessageDto {
    private String queueUrl;
    private String body;
}
