package desafio.itau.app.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO {
    private UUID customerId;
    private String name;
}
