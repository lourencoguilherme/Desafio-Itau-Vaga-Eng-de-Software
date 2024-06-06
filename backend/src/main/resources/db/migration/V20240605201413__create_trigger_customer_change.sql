-- Migration script: create_trigger_customer_change
CREATE TRIGGER customers
AFTER INSERT OR UPDATE OR DELETE ON customers
FOR EACH ROW EXECUTE FUNCTION log_changes();


