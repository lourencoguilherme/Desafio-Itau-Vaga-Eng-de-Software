package desafio.itau.app.customer.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import desafio.itau.app.customer.model.Customer;
import desafio.itau.config.AwsConfig;
import desafio.itau.infrastructure.QueueService;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import desafio.itau.app.customer.model.CustomerQueue;

@Slf4j
@Component
@EntityListeners(CustomerInterceptor.class)
public class CustomerInterceptor {

    @Autowired
    private QueueService sqsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AwsConfig awsConfig;

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
            String json  = objectMapper.writeValueAsString(new CustomerQueue(operation, (Customer) entity));
            sqsService.publishMessage(awsConfig.getCustomerQueue(), json);
        } catch (Exception e) {
            log.error("Error uploading object: {}", e.getMessage());
        }
    }

}
