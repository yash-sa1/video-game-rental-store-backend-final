package com.example.demo.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReturnGameRequest {
    private String name;
    private Integer customerID;
    private String title;
    private String givenrentalID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGivenrentalID() {
        return givenrentalID;
    }

    public void setGivenrentalID(String givenrentalID) {
        this.givenrentalID = givenrentalID;
    }

    public ReturnGameRequest(String name, Integer customerID, String title, String givenrentalID) {
        this.name = name;
        this.customerID = customerID;
        this.title = title;
        this.givenrentalID = givenrentalID;
    }
}