import com.example.demo.api.VideoGameController;
import com.example.demo.data.DataAccessLayer;
import com.example.demo.model.entity.Customer;
import com.example.demo.model.entity.Rental;
import com.example.demo.model.entity.VideoGame;
import com.example.demo.model.request.RentGameRequest;
import com.example.demo.model.request.ReturnGameRequest;
import com.example.demo.service.VideoGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class VideoGameServiceTest {

    @BeforeEach // this method will run before each test
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRentGame() throws IOException {
        DataAccessLayer dataAccessLayer = mock(DataAccessLayer.class); // this is the mock object of the data access layer

        // this is the mock object of the customer and video game
        Customer customer = mock(Customer.class);
        VideoGame videoGame = mock(VideoGame.class);

        // mocked methods
        when(dataAccessLayer.getAllCustomers()).thenReturn(List.of(customer));
        when(dataAccessLayer.getAllVideoGames()).thenReturn(List.of(videoGame));

        when(customer.getCustomerid()).thenReturn(1);
        when(videoGame.getTitle()).thenReturn("Game1");
        when(videoGame.getCurrent_stock()).thenReturn(10);

        VideoGameService videoGameService = new VideoGameService(dataAccessLayer);
        VideoGameController videoGameController = new VideoGameController(videoGameService);
        RentGameRequest rentGameRequest = new RentGameRequest(1, "Game1");
        ResponseEntity<String> response = videoGameController.rentGame(rentGameRequest);

        verify(dataAccessLayer, times(1)).saveRental(any());
        verify(dataAccessLayer, times(1)).changeStockAfterRenting(any());

        assertEquals("completed request", response.getBody());

    }

    @Test
    void testRentGameNoStock() throws IOException {
        DataAccessLayer dataAccessLayer = mock(DataAccessLayer.class); // this is the mock object of the data access layer

        // this is the mock object of the customer and video game
        Customer customer = mock(Customer.class);
        VideoGame videoGame = mock(VideoGame.class);

        // mocked methods
        when(dataAccessLayer.getAllCustomers()).thenReturn(List.of(customer));
        when(dataAccessLayer.getAllVideoGames()).thenReturn(List.of(videoGame));

        when(customer.getCustomerid()).thenReturn(1);
        when(videoGame.getTitle()).thenReturn("Game1");
        when(videoGame.getCurrent_stock()).thenReturn(0);

        VideoGameService videoGameService = new VideoGameService(dataAccessLayer);
        VideoGameController videoGameController = new VideoGameController(videoGameService);
        RentGameRequest rentGameRequest = new RentGameRequest(1, "Game1");
        ResponseEntity<String> response = videoGameController.rentGame(rentGameRequest);

        verify(dataAccessLayer, times(0)).saveRental(any());
        verify(dataAccessLayer, times(0)).changeStockAfterRenting(any());

        assertEquals("customer or game not found or game out of stock", response.getBody());

    }

    // VideoGameServiceTest.java
    @Test
    void testReturnGame() throws IOException {
        DataAccessLayer dataAccessLayer = mock(DataAccessLayer.class);

        Customer customer = new Customer("John", 1);
        VideoGame videoGame = new VideoGame("Game1", 10, "sports", 1, 4, "https://www.google.com");
        Date rentalDate = new Date(1733236812650L);
        Rental rental = new Rental(videoGame, customer, 1, rentalDate, "2o3fb03bf012-1234", false);
        String rentalID = "2o3fb03bf012-1234";

        when(dataAccessLayer.getAllCustomers()).thenReturn(List.of(customer));
        when(dataAccessLayer.getAllVideoGames()).thenReturn(List.of(videoGame));
        when(dataAccessLayer.getAllRentals(any(), anyList())).thenReturn(List.of(rental));
        when(dataAccessLayer.checkRentalID(rental, rentalID)).thenReturn(true);

        VideoGameService videoGameService = new VideoGameService(dataAccessLayer);
        VideoGameController videoGameController = new VideoGameController(videoGameService);
        ReturnGameRequest returnGameRequest = new ReturnGameRequest("John", 1, "Game1", rentalID);
        ResponseEntity<String> response = videoGameController.returnGame(returnGameRequest);

        verify(dataAccessLayer, times(1)).checkRentalID(rental, rentalID);
        assertEquals(ResponseEntity.ok("Game returned successfully!"), response);
    }

    @Test
    void testRegisterCustomer() throws IOException {
        DataAccessLayer dataAccessLayer = mock(DataAccessLayer.class);
        VideoGameService videoGameService = new VideoGameService(dataAccessLayer);

        String customerName = "test";
        int expectedCustomerID = 1;

        when(dataAccessLayer.create_next_id("customers.txt")).thenReturn(expectedCustomerID);

        int actualCustomerID = videoGameService.registerCustomer(customerName);

        verify(dataAccessLayer, times(1)).registerCustomer(any(Customer.class));
        assertEquals(expectedCustomerID, actualCustomerID);
    }

    @Test
    void testGetAllCustomers() {
        DataAccessLayer dataAccessLayer = mock(DataAccessLayer.class);
        VideoGameService videoGameService = new VideoGameService(dataAccessLayer);
        VideoGameController videoGameController = new VideoGameController(videoGameService);
        List<Customer> expectedCustomers = List.of(new Customer("test", 1));

        when(dataAccessLayer.getAllCustomers()).thenReturn(expectedCustomers);

        ResponseEntity<List<Customer>> response = videoGameController.getAllCustomers();

        assertEquals(ResponseEntity.ok(expectedCustomers), response);
    }

    @Test
    void testGetAllVideoGames() {
        DataAccessLayer dataAccessLayer = mock(DataAccessLayer.class);
        VideoGameService videoGameService = new VideoGameService(dataAccessLayer);
        VideoGameController videoGameController = new VideoGameController(videoGameService);
        List<VideoGame> expectedVideoGames = List.of(new VideoGame("test", 1, "test", 1, 1, "test"));

        when(dataAccessLayer.getAllVideoGames()).thenReturn(expectedVideoGames);

        ResponseEntity<List<VideoGame>> response = videoGameController.getAllVideoGames();

        assertEquals(ResponseEntity.ok(expectedVideoGames), response);
    }

    @Test
    void testGetAllRentals() throws IOException {
        DataAccessLayer dataAccessLayer = mock(DataAccessLayer.class);
        VideoGameService videoGameService = new VideoGameService(dataAccessLayer);
        VideoGameController videoGameController = new VideoGameController(videoGameService);
        List<String> expectedRentals = List.of("test");

        when(dataAccessLayer.readAndPrintRentalsFile("1")).thenReturn(expectedRentals);

        ResponseEntity<List<String>> response = videoGameController.getAllRentals("1");

        assertEquals(ResponseEntity.ok(expectedRentals), response);
    }
}
