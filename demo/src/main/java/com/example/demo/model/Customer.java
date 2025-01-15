package com.example.demo.model;

public class Customer {
    private String name;
    private int customerid;

    public Customer(String name, int customerid) {
        this.name = name;
        this.customerid = customerid;
    }

    public String getName() {
        return name;
    }

    public int getCustomerid() {
        return customerid;
    }

    public static Customer fromString( String customerString) {
        String [] parts  = customerString.split(",");
        if (parts.length < 2) {
            throw new IllegalArgumentException("invalid customer string" + customerString);
        }
        String customerName = parts[0];
        int customerID;
        try {
            customerID = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("invalid customer id" + parts[1]);
        }

        return new Customer(customerName, customerID);

    }


}