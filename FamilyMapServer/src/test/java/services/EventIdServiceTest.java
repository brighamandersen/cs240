package services;

import daos.DataAccessException;
import models.Event;
import models.Person;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LoadRequest;
import requests.RegisterRequest;
import results.EventIdResult;
import results.RegisterResult;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

        eventIdService = new EventIdService();
    }

    @Test
    void testRunEventIdPass() throws DataAccessException {
        List<User> users = new ArrayList<>();
        List<Person> persons = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        String newEventId = "neweventid";
        Event newEvent = new Event(newEventId, "arich", "richlet1", 79.9833f,
                -84.0667f, "Canada", "Eureka", "birth", 1998);
        events.add(newEvent);

        LoadRequest loadRequest = new LoadRequest(users, persons, events);
        LoadService loadService = new LoadService();
        loadService.load(loadRequest);

        Path goodUrlPath = Path.of("/event/" + newEventId);
        EventIdResult eventIdResult = eventIdService.runEventId(authToken, goodUrlPath);

        assertTrue(eventIdResult.isSuccess());
        assertNull(eventIdResult.getMessage());
        assertEquals(newEventId, eventIdResult.getEventId());
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