package desafio.itau.app.customer.listener;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import desafio.itau.app.customer.model.Customer;
import desafio.itau.infrastructure.s3.S3Service;
import desafio.itau.infrastructure.sqs.SqsService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
@EnableScheduling
public class CustomerListener {

    @Autowired
    private AmazonSQS amazonSQS;

    @Value("${aws.sqs.customerQueue}")
    private String customerQueue;

    @Value("${aws.sqs.customerBucket}")
    private String customerBucket;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SqsService sqsService;

    @Autowired
    private S3Service bucketRepository;

    @Scheduled(fixedDelay = 10000) // Verifica a fila a cada 10 segundos
    public void receiveMessages() {
        List<Message> messages = amazonSQS.receiveMessage(customerQueue).getMessages();

        for (Message message : messages) {
            processMessage(message.getBody());
            // Depois de processar a mensagem, exclua-a da fila
            amazonSQS.deleteMessage(customerQueue, message.getReceiptHandle());
        }
    }

    private void processMessage(String messageBody) {
        try {
            Customer customer = objectMapper.readValue(messageBody, Customer.class);
            bucketRepository.saveInFile(customerBucket, customer.getCustomerId().toString(),  messageBody);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        log.info("Mensagem recebida: " + messageBody);
    }


}
