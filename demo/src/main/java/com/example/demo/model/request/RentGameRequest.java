package com.example.demo.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Data
@Getter
@Setter

public class RentGameRequest {
    private Integer customerId;
    private String gameTitle;

    public Integer getCustomerId() {
        return customerId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public RentGameRequest(Integer customerId, String gameTitle) {
        this.customerId = customerId;
        this.gameTitle = gameTitle;
    }
}