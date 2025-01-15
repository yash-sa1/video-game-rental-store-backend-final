//package com.example.demo.controller;
//
//import com.example.demo.model.Customerfake;
//import com.example.demo.service.VideoGameService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//// This comment was added by Joel
//
//@RestController
//@RequestMapping("/api/v1/customers")
//public class Controller {
//
//   @Autowired
//   private VideoGameService customerService;
//
//    // Create a new customer
//    @PostMapping("/create")
//    public Customerfake createCustomer(@RequestBody Customerfake customer) {
//        return customerService.saveCustomer(customer);
//    }
//
//    // Retrieve all customers
//    @GetMapping("/all")
//    public List<Customerfake> getAllCustomers() {
//        return customerService.getAllCustomers();
//    }
//
//    // Retrieve a specific customer by ID
//    @GetMapping("/{customerId}")
//    public Customerfake getCustomerById(@PathVariable int customerId) {
//        return customerService.findById(customerId);
//    }
//
//    // Update an existing customer
////    @PutMapping("/update/{customerId}")
////    public Customer updateCustomer(@PathVariable int customerId, @RequestBody Customer updatedCustomer) {
////        for (int i = 0; i < customerList.size(); i++) {
////            Customer existingCustomer = customerList.get(i);
////            if (existingCustomer.getId() == customerId) {
////                existingCustomer.setName(updatedCustomer.getName());
////                existingCustomer.setAddress(updatedCustomer.getAddress());
////                existingCustomer.setAge(updatedCustomer.getAge());
////                return existingCustomer;
////            }
////        }
////        throw new RuntimeException("Customer not found with id: " + customerId);
////    }
//
//    // Delete a customer by ID
////    @DeleteMapping("/delete/{customerId}")
////    public String deleteCustomer(@PathVariable int customerId) {
////        customerList.removeIf(customer -> customer.getId() == customerId);
////        return "Customer with id " + customerId + " has been deleted.";
////    }
//}
