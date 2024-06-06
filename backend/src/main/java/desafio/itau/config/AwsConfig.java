package desafio.itau.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AwsConfig {
    @Value("${aws.sqs.customerQueue}")
    private String customerQueue;

    @Value("${aws.sqs.customerBucket}")
    private String customerBucket;


}
