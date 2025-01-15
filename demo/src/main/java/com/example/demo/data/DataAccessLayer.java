package com.example.demo.data;

import com.example.demo.model.entity.Customer;
import com.example.demo.model.entity.Rental;
import com.example.demo.model.entity.VideoGame;
import com.example.demo.service.FileUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class DataAccessLayer implements DataAccessLayerInterface {

    private static final String CUSTOMER_FILE = "customers.txt";
    private static final String GAME_FILE = "games.txt";
    private static final String RENTAL_FILE = "rentals.txt";

    public DataAccessLayer() {
    } // this is the constructor method

    @Override
    public List<Customer> getAllCustomers() {
        return getAllFromFile(CUSTOMER_FILE, Customer::fromString);
    }

    @Override
    public List<VideoGame> getAllVideoGames() {

        return getAllFromFile(GAME_FILE, VideoGame::fromString);
    }

    @Override
    public List<Rental> getAllRentals(List<VideoGame> videoGames, List<Customer> customers) {
        return getAllFromFile(RENTAL_FILE, x -> Rental.fromString(x, videoGames, customers));
    }

    @Override
    public void saveRental(Rental rental) { // this method saves the rental details to the rentals.txt file
        String input_into_file = rental.getGame().getTitle() + ","  + rental.getCustomer() + "," + new Date().getTime() + "," + rental.getRentalID() + "," + false;
        FileUtils.savedata(input_into_file, RENTAL_FILE);
    }

    @Override
    public void changeStockAfterRenting(VideoGame game) throws IOException {
        int newStock = game.getCurrent_stock() - 1;
        FileUtils.changeStock(GAME_FILE, game.getTitle(), newStock);
        game.setCurrent_stock(newStock);
        System.out.println("Game rented successfully! yesss");

    }

    @Override
    public void changeStockAfterReturning(VideoGame game) throws IOException {
        int newStock = game.getCurrent_stock() + 1;
        FileUtils.changeStock(GAME_FILE, game.getTitle(), newStock);
        game.setCurrent_stock(newStock);

    }

    @Override
    public boolean checkRentalID(Rental rental, String entryToCheck) throws IOException {
        String enteredSearch = rental.getGame().getTitle() + "," + rental.getCustomer().getName() + "," + rental.getCustomer().getCustomerid();
        return FileUtils.check_rentalID(RENTAL_FILE, enteredSearch, entryToCheck);
    }

    @Override
    public List<String> readAndPrintRentalsFile(String customerID) throws IOException { // this method reads and prints the rentals.txt file
        return FileUtils.readAndPrintRentalsFile(RENTAL_FILE, customerID);
    }

    @Override
    public void registerCustomer(Customer customer) throws IOException {
        String input_into_file = customer.getName() + "," + FileUtils.create_next_id(CUSTOMER_FILE);
        FileUtils.savedata(input_into_file, CUSTOMER_FILE);

    }

    @Override
    public int create_next_id(String filename) throws IOException {
        return FileUtils.create_next_id(filename);
    }

    // Generic method to read data from a file and convert each line to a target entity
    private <T> List<T> getAllFromFile(String filename, Function<String, T> fromStringFunction) {
        // Create a new list to hold the target entities
        final List<T> targetEntities = new ArrayList<>();

        // Load the file lines using the FileUtils.loadData method
        List<String> fileLines = FileUtils.loadData(filename);

        // Iterate over each line in the file
        fileLines.forEach(fileLine -> {
            // Convert the file line to a target entity using the provided function
            T TargetEntity = fromStringFunction.apply(fileLine);
            // Add the target entity to the list
            targetEntities.add(TargetEntity);
        });

        // Return the list of target entities
        return targetEntities;
    }
}

