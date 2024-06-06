package desafio.itau.app.customer.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import desafio.itau.app.customer.model.Customer;
import desafio.itau.infrastructure.sqs.SqsService;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EntityListeners(CustomerInterceptor.class)
public class CustomerInterceptor {

    @Autowired
    private SqsService sqsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${aws.sqs.customerQueue}")
    private String customerQueue;

    @PostPersist
    public void postPersist(Object entity) {
        publishToSQS("CREATE", entity);
    }

    @PostUpdate
    public void postUpdate(Object entity) {
        publishToSQS("UPDATE", entity);
    }

    @PreRemove
    public void preRemove(Object entity) {
        publishToSQS("DELETE", entity);
    }

    private void publishToSQS(String operation, Object entity) {
        try {
            String json  = objectMapper.writeValueAsString(entity);
            sqsService.publishMessage(customerQueue, json);
        } catch (Exception e) {
            log.error("Error uploading object: {}", e.getMessage());
        }
    }

}
