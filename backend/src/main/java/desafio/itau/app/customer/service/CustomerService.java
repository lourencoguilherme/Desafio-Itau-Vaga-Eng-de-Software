package desafio.itau.app.customer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import desafio.itau.app.customer.dto.CustomerCreateDTO;
import desafio.itau.app.customer.dto.CustomerDTO;
import desafio.itau.app.customer.model.Customer;
import desafio.itau.app.customer.repository.CustomerRepository;
import desafio.itau.infrastructure.bucket.repository.BucketRepository;
import desafio.itau.infrastructure.bucket.repository.s3.S3Repository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BucketRepository<Customer> bucketRepository;

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CustomerDTO> getCustomerById(UUID customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        return optionalCustomer.flatMap(customer -> Optional.of(convertToDTO(customer)));
    }

    public CustomerDTO createCustomer(CustomerCreateDTO customerDTO) {
        Customer customer = convertToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }

    public CustomerDTO updateCustomer(UUID customerId, CustomerDTO customerDTO) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            convertToDTO(existingCustomer);
            existingCustomer.setCustomerId(customerId);
            Customer updatedCustomer = customerRepository.save(existingCustomer);
            return convertToDTO(updatedCustomer);
        }
        return null;
    }

    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    public List<CustomerDTO> auditAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        for (Customer customer : customers) {
            try {
                bucketRepository.saveInFile("customers-changes", customer.getCustomerId().toString(),  customer);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return customers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setName(customer.getName());
        return customerDTO;
    }

    private Customer convertToEntity(CustomerCreateDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        return customer;
    }
}