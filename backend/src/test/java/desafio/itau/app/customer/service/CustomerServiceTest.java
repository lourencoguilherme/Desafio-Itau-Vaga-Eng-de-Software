package desafio.itau.app.customer.service;

import desafio.itau.app.customer.dto.CustomerCreateDTO;
import desafio.itau.app.customer.dto.CustomerDTO;
import desafio.itau.app.customer.dto.CustomerUpdateDTO;
import desafio.itau.app.customer.model.Customer;
import desafio.itau.app.customer.repository.CustomerRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCustomers() {
        Customer customer1 = Instancio.create(Customer.class);
        Customer customer2 = Instancio.create(Customer.class);
        when(customerRepository.findAll()).thenReturn(List.of(customer1, customer2));

        List<CustomerDTO> result = customerService.getAllCustomers();

        assertEquals(2, result.size());
        assertEquals(customer1.getCustomerId(), result.get(0).getCustomerId());
        assertEquals(customer2.getCustomerId(), result.get(1).getCustomerId());
    }

    @Test
    void testGetCustomerById() {
        Customer customer = Instancio.create(Customer.class);
        UUID customerId = customer.getCustomerId();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Optional<CustomerDTO> result = customerService.getCustomerById(customerId);

        assertTrue(result.isPresent());
        assertEquals(customer.getCustomerId(), result.get().getCustomerId());
    }

    @Test
    void testGetCustomerById_NotFound() {
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        Optional<CustomerDTO> result = customerService.getCustomerById(customerId);

        assertFalse(result.isPresent());
    }

    @Test
    void testCreateCustomer() {
        CustomerCreateDTO createDTO = Instancio.create(CustomerCreateDTO.class);
        Customer customer = Instancio.create(Customer.class);
        when(customerRepository.save(any())).thenReturn(customer);

        CustomerDTO result = customerService.createCustomer(createDTO);

        assertNotNull(result);
        assertEquals(customer.getCustomerId(), result.getCustomerId());
    }
    @Test
    void testUpdateCustomer() {
        CustomerUpdateDTO updateDTO = Instancio.create(CustomerUpdateDTO.class);
        Customer customer = Instancio.create(Customer.class);
        UUID customerId = customer.getCustomerId();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(ArgumentMatchers.any(Customer.class))).thenReturn(customer);

        CustomerDTO result = customerService.updateCustomer(customerId, updateDTO);

        assertNotNull(result);
        assertEquals(updateDTO.getName(), result.getName());
    }

    @Test
    void testUpdateCustomer_NotFound() {
        CustomerUpdateDTO updateDTO = Instancio.create(CustomerUpdateDTO.class);
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        CustomerDTO result = customerService.updateCustomer(customerId, updateDTO);

        assertNull(result);
    }

    @Test
    void testDeleteCustomer() {
        UUID customerId = UUID.randomUUID();
        doNothing().when(customerRepository).deleteById(customerId);

        assertDoesNotThrow(() -> customerService.deleteCustomer(customerId));

        verify(customerRepository, times(1)).deleteById(customerId);
    }
}