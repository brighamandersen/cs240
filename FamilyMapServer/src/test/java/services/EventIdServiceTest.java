package services;

import daos.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import results.EventIdResult;
import results.PersonIdResult;
import results.RegisterResult;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class EventIdServiceTest {
    private EventIdService eventIdService;
    private String authToken;

    @BeforeEach
    void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();

        RegisterRequest registerRequest = new RegisterRequest("brighamband", "password",
                "brighamband@gmail.com", "Brigham", "Andersen", "m");
        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = registerService.register(registerRequest);
        authToken = registerResult.getAuthToken();

        // FIXME - Have this call the load service to generate dummy event data

        eventIdService = new EventIdService();
    }

    @Test
    void testRunEventIdPass() {
//        EventIdResult eventIdResult = eventIdService.runEventId(authToken, "/event/" + eventId);

    }

    /**
     * Makes request with invalid auth token
     */
    @Test
    void testRunEventIdFail() throws DataAccessException {
        String BAD_AUTH_TOKEN = "badauthtoken";
        String RANDOM_EVENT_ID = "randomeventid";
        Path badUrlPath = Path.of("/event/" + RANDOM_EVENT_ID);

        EventIdResult eventIdResult = eventIdService.runEventId(BAD_AUTH_TOKEN, badUrlPath);

        assertFalse(eventIdResult.isSuccess());
        assertEquals("Error: Invalid auth token", eventIdResult.getMessage());
    }
}