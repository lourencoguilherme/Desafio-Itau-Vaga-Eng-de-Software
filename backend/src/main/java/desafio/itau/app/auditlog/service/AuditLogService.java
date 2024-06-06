package desafio.itau.app.auditlog.service;

import desafio.itau.app.auditlog.model.AuditLog;
import desafio.itau.app.auditlog.repository.AuditLogRepository;
import desafio.itau.infrastructure.s3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private S3Service bucketRepository;

    @Value("${aws.sqs.auditBucket}")
    private String auditBucket;

    @Autowired
    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public List<AuditLog> auditLogsBkp() {
        List<AuditLog> auditLogs = auditLogRepository.findAll();

        for (AuditLog auditLog : auditLogs) {
            try {
                bucketRepository.saveInFile(auditBucket, auditLog.getAuditLogId().toString(),  auditLog);
                deleteAuditLogsByAuditLogId(auditLog.getAuditLogId());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return auditLogRepository.findAll();
    }

    public void deleteAuditLogsByAuditLogId(UUID auditLogId) {
        auditLogRepository.deleteById(auditLogId);
    }
}
