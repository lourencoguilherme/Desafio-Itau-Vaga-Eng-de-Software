package desafio.itau.infrastructure;

public interface QueueService {
    void publishMessage(String filaUrl, String mensagem);
}
