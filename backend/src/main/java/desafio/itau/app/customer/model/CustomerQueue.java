package desafio.itau.app.customer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerQueue implements Serializable {
    private String operation;
    private Customer body;
}