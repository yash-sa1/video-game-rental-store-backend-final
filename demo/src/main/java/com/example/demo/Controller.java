package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    // Retrieve all customers
    private List<Customer> customerList = new ArrayList<>();
    private int customerIdCounter = 1;

    // Create a new customer
    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Customer customer) {
        customer.setId(customerIdCounter++);
        customerList.add(customer);
        return customer;
    }

    // Retrieve all customers
    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerList;
    }

    // Retrieve a specific customer by ID
    @GetMapping("/{customerId}")
    public Optional<Customer> getCustomerById(@PathVariable int customerId) {
        return customerList.stream()
                .filter(customer -> customer.getId() == customerId)
                .findFirst();
    }

    // Update an existing customer
    @PutMapping("/update/{customerId}")
    public Customer updateCustomer(@PathVariable int customerId, @RequestBody Customer updatedCustomer) {
        for (int i = 0; i < customerList.size(); i++) {
            Customer existingCustomer = customerList.get(i);
            if (existingCustomer.getId() == customerId) {
                existingCustomer.setName(updatedCustomer.getName());
                existingCustomer.setAddress(updatedCustomer.getAddress());
                existingCustomer.setAge(updatedCustomer.getAge());
                return existingCustomer;
            }
        }
        throw new RuntimeException("Customer not found with id: " + customerId);
    }

    // Delete a customer by ID
    @DeleteMapping("/delete/{customerId}")
    public String deleteCustomer(@PathVariable int customerId) {
        customerList.removeIf(customer -> customer.getId() == customerId);
        return "Customer with id " + customerId + " has been deleted.";
    }

}
