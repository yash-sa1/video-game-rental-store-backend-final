package com.example.demo.model;

import java.util.Date;
import java.util.List;

public class Rental {

    private VideoGame game;
    private Customer customer;
    private Date rentalDate;
    private String rentalID;
    private final boolean returned;

    public Rental(VideoGame game, Customer customer,int customerID, Date rentalDate, String rentalID, boolean returned) {
        this.game = game;
        this.customer = customer;
        this.rentalDate = rentalDate;
        this.rentalID = rentalID;
        this.returned = returned;
    }

    public VideoGame getGame() {
        return game;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getRentalID() {
        return rentalID;
    }

    public static Rental fromString(final String rentalString, final List<VideoGame> videoGames, final List<Customer> customers) {
        if (rentalString == null || rentalString.trim().isEmpty()) {
            throw new IllegalArgumentException("invalid rental string" + rentalString);
        }

        String[] parts = rentalString.split(",");
        if (parts.length < 6) {
            throw new IllegalArgumentException("invalid rental string" + rentalString);
        }

        String videoGameName = parts[0];
        VideoGame videoGame = videoGames.stream().filter(game -> game.getTitle().equals(videoGameName)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid videogame name" + videoGameName));

        String customerName = parts[1];
        Customer customer = customers.stream().filter(c -> c.getName().equals(customerName)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid customer name" + customerName));

        int customerID;
        try {
            customerID = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("invalid customer id" + parts[2]);
        }

        Date rentalDate;
        try {
            rentalDate = new Date(Long.parseLong(parts[3]));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("invalid rental date" + parts[3]);
        }

        String specificRentalID = parts[4];

        boolean returned;
        try {
            returned = Boolean.parseBoolean(parts[5]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("invalid returned value" + parts[5]);
        }
        return new Rental(videoGame, customer, customerID, rentalDate, specificRentalID, returned);
    }
}