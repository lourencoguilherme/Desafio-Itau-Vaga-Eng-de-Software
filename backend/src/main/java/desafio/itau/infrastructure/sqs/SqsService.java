package desafio.itau.infrastructure.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import desafio.itau.infrastructure.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SqsService implements QueueService {

    @Autowired
    private AmazonSQS amazonSQS;

    public void publishMessage(String filaUrl, String mensagem) {
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(filaUrl)
                .withMessageBody(mensagem)
                .withDelaySeconds(5);
        amazonSQS.sendMessage(send_msg_request);
    }
}