package desafio.itau.infrastructure.bucket.repository.s3;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private ObjectMapper objectMapper;


    @SneakyThrows
    public void saveInFile(String dirOrBucketName, String key, Object object){
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(dirOrBucketName)
                .key(key)
                .build();

        try {
            String json = objectMapper.writeValueAsString(object);

            // Salvar o JSON no Amazon S3
            Path file = Files.write(
                    Paths.get("./temp", key),
                    json.getBytes());
            s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));
            log.info("Object created: {}", putObjectRequest.key());
        } catch (S3Exception e) {
            log.error("Error uploading object: {}", e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}