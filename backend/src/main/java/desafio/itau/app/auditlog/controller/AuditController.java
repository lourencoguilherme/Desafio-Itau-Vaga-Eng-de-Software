package desafio.itau.app.auditlog.controller;

import desafio.itau.app.auditlog.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audits")
public class AuditController {

    @Autowired
    public AuditLogService auditLogService;

    @GetMapping("/create")
    public ResponseEntity<?> auditAllLogs() {
        auditLogService.auditLogsBkp();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
