package io.raresconea.snsdemo.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSClient;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AwsSqsConfig {
    private final AwsConfigProperties awsConfigProperties;

    @Bean
    public AmazonSQSClient amazonSQSClient() {
        return (AmazonSQSClient) AmazonSQSClient.builder()
                .withRegion(Regions.EU_CENTRAL_1)
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(
                                awsConfigProperties.getAccessKey(),
                                awsConfigProperties.getSecretKey())
                        )
                )
                .build();
    }

}
