package desafio.itau.infrastructure.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SqsService {

    @Autowired
    private AmazonSQS amazonSQS;

    @Autowired
    public SqsService(AmazonSQS amazonSQS) {
        this.amazonSQS = amazonSQS;
    }

    public void publishMessage(String filaUrl, String mensagem) {
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(filaUrl)
                .withMessageBody(mensagem)
                .withDelaySeconds(5);
        amazonSQS.sendMessage(send_msg_request);
    }
}