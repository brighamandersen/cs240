package services;

import daos.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import results.EventFamilyResult;
import results.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class EventFamilyServiceTest {
    private EventFamilyService eventFamilyService;
    private String authtoken;

    @BeforeEach
    void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();

        RegisterRequest registerRequest = new RegisterRequest("brighamband", "password",
                "brighamband@gmail.com", "Brigham", "Andersen", "m");
        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = registerService.register(registerRequest);
        authtoken = registerResult.getAuthToken();

        eventFamilyService = new EventFamilyService();
    }

    @AfterAll
    static void cleanUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    void testRunEventFamilyPass() throws DataAccessException {
        EventFamilyResult eventFamilyResult = eventFamilyService.runEventFamily(authtoken);

        assertTrue(eventFamilyResult.isSuccess());
        assertNull(eventFamilyResult.getMessage());
        assertEquals(91, eventFamilyResult.getEvents().size());
    }

    /**
     * Makes request with invalid auth token
     */
    @Test
    void testRunEventFamilyFail() throws DataAccessException {
        String BAD_AUTH_TOKEN = "badauthtoken";

        EventFamilyResult eventFamilyResult = eventFamilyService.runEventFamily(BAD_AUTH_TOKEN);

        assertFalse(eventFamilyResult.isSuccess());
        assertEquals("Error: Invalid auth token", eventFamilyResult.getMessage());
    }
}