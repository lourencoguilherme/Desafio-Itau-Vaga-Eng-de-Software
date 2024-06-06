package desafio.itau.infrastructure.bucket.repository;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface BucketRepository {

    public void saveInFile(String dirOrBucketName, String key, Object object) throws JsonProcessingException;
}
