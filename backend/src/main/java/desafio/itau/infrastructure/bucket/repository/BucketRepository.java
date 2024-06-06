package desafio.itau.infrastructure.bucket.repository;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface BucketRepository<T> {

    public void saveInFile(String dirOrBucketName, String key, T object) throws JsonProcessingException;
}
