package services;

import daos.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import results.RegisterResult;
import results.Result;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest {
    private FillService fillService;
    private String username;

    @BeforeEach
    void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();

        RegisterRequest registerRequest = new RegisterRequest("brighamband", "password",
                "brighamband@gmail.com", "Brigham", "Andersen", "m");
        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = registerService.register(registerRequest);
        username = registerResult.getUsername();

        fillService = new FillService();
    }

    @Test
    void testFillPass() {
        int GOOD_NUM_GENERATIONS = 2;
        Path goodUrlPath = Path.of("/fill/" + username + "/" + GOOD_NUM_GENERATIONS);

        Result result = fillService.fill(goodUrlPath);

        assertTrue(result.isSuccess());
        assertEquals("Successfully added 7 persons and 19 events to the database.", result.getMessage());
    }

    /**
     * Pass in negative generations
     */
    @Test
    void testFillFail() {
        int BAD_NUM_GENERATIONS = -2;
        Path badUrlPath = Path.of("/fill/" + username + "/" + BAD_NUM_GENERATIONS);

        Result result = fillService.fill(badUrlPath);

        assertFalse(result.isSuccess());
        assertEquals("Error: Invalid username or generations parameter", result.getMessage());
    }
}