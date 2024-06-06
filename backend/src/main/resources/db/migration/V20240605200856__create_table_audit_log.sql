-- Migration script: create_table_audit_log
CREATE TABLE audit_log (
    audit_log_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    table_name TEXT,
    operation TEXT,
    old_data JSONB,
    new_data JSONB,
    change_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


