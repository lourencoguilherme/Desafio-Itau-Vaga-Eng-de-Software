package desafio.itau.app.customer.model;

import desafio.itau.app.customer.interceptor.CustomerInterceptor;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@EntityListeners(CustomerInterceptor.class)
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    private UUID customerId;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
    private boolean deleted;
}