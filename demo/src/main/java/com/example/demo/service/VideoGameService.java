package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.model.Rental;
import com.example.demo.model.VideoGame;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.data.DataAccessLayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service // this annotation is used to mark the class as a service
public class VideoGameService {

    private static final Logger logger = LoggerFactory.getLogger(VideoGameService.class); // this is the logger object
    private static List<VideoGame> games = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static List<Rental> rentals = new ArrayList<>();
    private final DataAccessLayer dataAccessLayer;

    @Autowired // autowired means to automatically resolve and inject the dependencies
    public VideoGameService(DataAccessLayer dataAccessLayer) {
        this.dataAccessLayer = dataAccessLayer;
        try {
            logger.info("Initialising VideoGameService");
            loadInitialData();
        } catch (Exception e) { // this is the exception handling
            logger.error("Error initialising VideoGameService", e);
            throw new RuntimeException("Error initialising VideoGameService", e);
        }
    }

    private void loadInitialData() {
        try{games = dataAccessLayer.getAllVideoGames();
            customers = dataAccessLayer.getAllCustomers(); // this method gets all the customers
            rentals = dataAccessLayer.getAllRentals(games, customers);
        } catch (Exception e) {
            logger.error("Error loading initial data", e);
            throw new RuntimeException("Error loading initial data", e);
        }
    }

    public List<VideoGame> getAllVideoGames() {
        return dataAccessLayer.getAllVideoGames();
    }

    public List<Customer> getAllCustomers() {
        return dataAccessLayer.getAllCustomers(); // this method gets all the customers
    }

    public int registerCustomer(String customerName) throws IOException {
        int customerID = dataAccessLayer.create_next_id("customers.txt"); // creates a new ID
        var customer = new Customer(customerName, customerID); // creates a new customer object with the entered name
        dataAccessLayer.registerCustomer(customer); // registers the customer accessing the data access layer
        return ( customer.getCustomerid());

    }

    public String rentGame(int customerID, String title) throws IOException {

        Customer customer = customers.stream()
                .filter(c -> c.getCustomerid() == customerID)
                .findFirst()
                .orElse(null); // utilising streams to filter the customer objects and finds the first one that matches the given customer ID

        VideoGame game = games.stream()
                .filter(g -> g.getTitle().equals(title))
                .findFirst()
                .orElse(null);

        if (customer !=null && game != null && game.getCurrent_stock() >= 1) { // checks if the customer and game are valid and if the game is in stock
            String specificRentalID = UUID.randomUUID().toString();
            var rental = new Rental(game, customer, customerID, new Date(),  specificRentalID, false);
            rentals.add(rental);
            dataAccessLayer.saveRental(rental); // saves the rental details to the rentals file
            dataAccessLayer.changeStockAfterRenting(game); // changes the stock after renting
            return ("its done");
        } else {
            return ("customer or game not found or game out of stock");
        }

    }

    public String returnGame( String name, Integer customerID, String title, String givenrentalID) throws IOException {
        Customer customer = customers.stream().filter(c -> c.getCustomerid() == customerID).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid customer id" + customerID));
        // utilising streams to filter the customer and game objects and finds the first one that matches the given title
        VideoGame game = games.stream().filter(g -> g.getTitle().equals(title)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid game title" + title));

        Rental rental = rentals.stream().filter(r -> r.getCustomer().getName().equals(name) && r.getGame().getTitle().equals(title) && r.getRentalID().equals(givenrentalID)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid rental details" + name + title + givenrentalID));

        if (customer != null && game != null && dataAccessLayer.checkRentalID(rental, givenrentalID)) { // checks if the rental ID is valid
            return "Game returned successfully!";
        } else {
            return "Game not returned!";
        }
    }

    public List<String> displayRentals(String customerID) throws IOException {
        return dataAccessLayer.readAndPrintRentalsFile(customerID); // reads and prints the rentals file by customer ID
    }
}

