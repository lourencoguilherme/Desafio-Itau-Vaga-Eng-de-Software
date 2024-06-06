package desafio.itau.app.auditlog.service;

import desafio.itau.app.auditlog.model.AuditLog;
import desafio.itau.app.auditlog.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public List<AuditLog> findAuditLogsByTableName(String tableName) {
        return auditLogRepository.findByTableName(tableName);
    }

    public void deleteAuditLogsByAuditLogId(UUID auditLogId) {
        auditLogRepository.deleteById(auditLogId);
    }
}
