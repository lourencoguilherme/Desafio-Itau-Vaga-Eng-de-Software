package desafio.itau.app.customer.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import desafio.itau.app.customer.model.Customer;
import desafio.itau.config.AwsConfig;
import desafio.itau.infrastructure.QueueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class CustomerInterceptorTest {

    @Mock
    private QueueService sqsService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private AwsConfig awsConfig;

    @InjectMocks
    private CustomerInterceptor customerInterceptor;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        when(objectMapper.writeValueAsString(any())).thenReturn("{\"operation\":\"CREATE\",\"body\":{}}");
        when(awsConfig.getCustomerQueue()).thenReturn("");
    }

    @Test
    void postPersist_ShouldPublishToSQS() throws Exception {
        Customer entity = new Customer();

        customerInterceptor.postPersist(entity);

        verify(sqsService).publishMessage(anyString(), anyString());
    }

    @Test
    void postUpdate_ShouldPublishToSQS() throws Exception {
        Customer entity = new Customer();

        customerInterceptor.postUpdate(entity);

        verify(sqsService).publishMessage(anyString(), anyString());
    }

    @Test
    void preRemove_ShouldPublishToSQS() throws Exception {
        Customer entity = new Customer();

        customerInterceptor.preRemove(entity);

        verify(sqsService).publishMessage(anyString(), anyString());
    }
}
