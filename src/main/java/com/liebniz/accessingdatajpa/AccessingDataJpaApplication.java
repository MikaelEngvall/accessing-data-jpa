package com.liebniz.accessingdatajpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class AccessingDataJpaApplication {

    private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

    public static void main(String[] args) {
        System.setProperty("log4j.configurationFile", "log4j.properties");
        SpringApplication.run(AccessingDataJpaApplication.class);
    }

    @Bean
    public CommandLineRunner demo(CustomerRepository repository) {
        return (args) -> {
            // Save a few customers
            Customer jack = repository.save(new Customer("Jack", "Bauer"));
            repository.save(new Customer("Chloe", "O'Brian"));
            repository.save(new Customer("Kim", "Bauer"));
            repository.save(new Customer("David", "Palmer"));
            repository.save(new Customer("Michelle", "Dessler"));
            // Fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Customer customer1 : repository.findAll()) {
                log.info(customer1.toString());
            }
            log.info("");

            // Fetch an individual customer by ID
            Optional<Customer> customerOptional = Optional.ofNullable(repository.findById(jack.getId())); //There must be another way?!
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                log.info("Customer found with findById():");
                log.info("--------------------------------");
                log.info(customer.toString());
            } else {
                log.info("Customer not found");
            }
            log.info("");

            // Fetch customers by last name
            log.info("Customers found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByLastName("Bauer")
                    .forEach(bauer -> log.info(bauer.toString()));

            log.info("");
        };
    }
}
