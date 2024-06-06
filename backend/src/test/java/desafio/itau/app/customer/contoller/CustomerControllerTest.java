package desafio.itau.app.customer.contoller;

import desafio.itau.app.customer.dto.CustomerCreateDTO;
import desafio.itau.app.customer.dto.CustomerDTO;
import desafio.itau.app.customer.service.CustomerService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllCustomers() throws Exception {
        CustomerDTO customer1 = Instancio.create(CustomerDTO.class);
        CustomerDTO customer2 = Instancio.create(CustomerDTO.class);
        List<CustomerDTO> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].customerId").value(customer1.getCustomerId().toString()))
                .andExpect(jsonPath("$[0].name").value(customer1.getName()))
                .andExpect(jsonPath("$[1].customerId").value(customer2.getCustomerId().toString()))
                .andExpect(jsonPath("$[1].name").value(customer2.getName()));
    }

    @Test
    public void testCreateCustomer() throws Exception {
        CustomerCreateDTO customerCreateDTO = Instancio.create(CustomerCreateDTO.class);

        CustomerDTO customerDTO = Instancio.create(CustomerDTO.class);

        Mockito.when(customerService.createCustomer(Mockito.any(CustomerCreateDTO.class)))
                .thenReturn(customerDTO);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value(customerDTO.getCustomerId().toString()))
                .andExpect(jsonPath("$.name").value(customerDTO.getName()));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        CustomerDTO customerDTO = Instancio.create(CustomerDTO.class);
        UUID customerId = customerDTO.getCustomerId();

        Mockito.when(customerService.getCustomerById(customerId)).thenReturn(java.util.Optional.of(customerDTO));

        mockMvc.perform(get("/customers/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value(customerDTO.getCustomerId().toString()))
                .andExpect(jsonPath("$.name").value(customerDTO.getName()));
    }

    @Test
    public void testGetCustomerById_NotFound() throws Exception {
        UUID customerId = UUID.randomUUID();

        Mockito.when(customerService.getCustomerById(customerId)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/customers/{customerId}", customerId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        UUID customerId = UUID.randomUUID();

        Mockito.doNothing().when(customerService).deleteCustomer(customerId);

        mockMvc.perform(delete("/customers/{customerId}", customerId))
                .andExpect(status().isNoContent());
    }


}
