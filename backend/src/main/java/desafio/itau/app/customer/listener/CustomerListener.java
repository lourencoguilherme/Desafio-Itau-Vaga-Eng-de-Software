package desafio.itau.app.customer.listener;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import desafio.itau.app.customer.model.CustomerQueue;
import desafio.itau.config.AwsConfig;
import desafio.itau.infrastructure.BucketService;
import desafio.itau.infrastructure.QueueService;
import lombok.AllArgsConstructor;
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


    @Autowired
    private AwsConfig awsConfig;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QueueService sqsService;

    @Autowired
    private BucketService bucketRepository;

    @Scheduled(fixedDelay = 10000) // Verifica a fila a cada 10 segundos
    public void receiveMessages() {
        List<Message> messages = amazonSQS.receiveMessage(awsConfig.getCustomerQueue()).getMessages();

        for (Message message : messages) {
            processMessage(message.getBody());
            // Depois de processar a mensagem, exclua-a da fila
            amazonSQS.deleteMessage(awsConfig.getCustomerQueue(), message.getReceiptHandle());
        }
    }

    private void processMessage(String messageBody) {
        try {
            CustomerQueue customer = objectMapper.readValue(messageBody, CustomerQueue.class);
            bucketRepository.saveInFile(awsConfig.getCustomerBucket(), customer.getBody().getCustomerId().toString(),  messageBody);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        log.info("Mensagem recebida: " + messageBody);
    }

}
