package desafio.itau.app.customer.listener;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import desafio.itau.app.customer.model.CustomerQueue;
import desafio.itau.config.AwsConfig;
import desafio.itau.config.JacksonConfig;
import desafio.itau.infrastructure.BucketService;
import desafio.itau.infrastructure.QueueService;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JacksonConfig.class)
public class CustomerListenerTest {

    @Mock
    private AmazonSQS amazonSQS;

    @Mock
    private ObjectMapper objectMapper;

    @Autowired
    private ObjectMapper autoObjectMapper;

    @Mock
    private QueueService sqsService;

    @Mock
    private BucketService bucketRepository;

    @Mock
    private AwsConfig awsConfig;

    @InjectMocks
    private CustomerListener customerListener;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(awsConfig.getCustomerQueue()).thenReturn("");
        when(awsConfig.getCustomerBucket()).thenReturn("");
    }

    @Test
    public void testReceiveMessages() throws JsonProcessingException {
        List<Message> messages = new ArrayList<>();
        // Criar algumas mensagens de exemplo
        messages.add(new Message().withBody(autoObjectMapper.writeValueAsString(Instancio.create(CustomerQueue.class))));
        messages.add(new Message().withBody(autoObjectMapper.writeValueAsString(Instancio.create(CustomerQueue.class))));
        when(objectMapper.readValue(anyString(), Mockito.eq(CustomerQueue.class))).thenReturn(Instancio.create(CustomerQueue.class));

        // Configurar comportamento do mock do AmazonSQS para retornar as mensagens criadas
        when(amazonSQS.receiveMessage(anyString())).thenReturn(new com.amazonaws.services.sqs.model.ReceiveMessageResult().withMessages(messages));

        // Executar o método a ser testado
        customerListener.receiveMessages();

        // Verificar se as mensagens foram processadas e excluídas
        verify(bucketRepository, times(2)).saveInFile(any(), any(), any());
        verify(amazonSQS, times(2)).deleteMessage(any(), any());
    }
}
