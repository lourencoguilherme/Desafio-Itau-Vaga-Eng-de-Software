package desafio.itau.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface BucketService {

    public void saveInFile(String dirOrBucketName, String key, Object object) throws JsonProcessingException;
}
