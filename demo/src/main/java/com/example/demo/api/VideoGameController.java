package com.example.demo.api;

import com.example.demo.model.Customer;
import com.example.demo.model.RentGameRequest;
import com.example.demo.model.ReturnGameRequest;
import com.example.demo.model.VideoGame;
import com.example.demo.service.VideoGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController

@RequestMapping("/rental-store")
public class VideoGameController {

    private final Logger logger = LoggerFactory.getLogger(VideoGameController.class);
    private final VideoGameService videoGameService;

    @Autowired
    public VideoGameController(VideoGameService videoGameService) {
        this.videoGameService = videoGameService;
    }

    @GetMapping("/videogames")
    public ResponseEntity<List<VideoGame>> getAllVideoGames() {
        logger.info("recieved get all video games request");
        return ResponseEntity.ok(videoGameService.getAllVideoGames());
    }

    @GetMapping("/customers") // this is the get mapping for getting all customers
    public ResponseEntity<List<Customer>> getAllCustomers() {
        logger.info("recieved get all customers request");
        return ResponseEntity.ok(videoGameService.getAllCustomers()); // this is the response entity for getting all customers
    }

    @GetMapping("/my-rentals")
    public ResponseEntity <List<String>> getAllRentals(@RequestParam String customerID) throws IOException {
        logger.info("recieved get all rentals request");
        return ResponseEntity.ok(videoGameService.displayRentals(customerID));
    }

    @PostMapping("/register-customer") // this is the post mapping for registering a customer
    public ResponseEntity<String> registerCustomer(@RequestBody String customer) throws IOException {
        logger.info("recieved register customer request");
        videoGameService.registerCustomer(customer);
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/rent-game")
    public ResponseEntity rentGame(@RequestBody RentGameRequest rentGameRequest) throws IOException {
        logger.info("recieved rent game request"); // this is the logger info for the rent game request
        String result = videoGameService.rentGame(rentGameRequest.getCustomerId(), rentGameRequest.getGameTitle());
        logger.info(result);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/return-game") // this is the put mapping for returning a game
    public ResponseEntity returnGame(@RequestBody ReturnGameRequest returnGameRequest) throws IOException {
        logger.info("recieved return game request");
        String result = videoGameService.returnGame(returnGameRequest.getName(), returnGameRequest.getCustomerID(), returnGameRequest.getTitle()
                , returnGameRequest.getGivenrentalID()); // this is the result of the return game request
        logger.info(result);
        return ResponseEntity.ok(result);
    }

}