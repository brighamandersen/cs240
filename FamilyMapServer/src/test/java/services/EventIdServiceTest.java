package services;

import daos.DataAccessException;
import models.Event;
import models.Person;
import models.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LoadRequest;
import requests.LoginRequest;
import requests.RegisterRequest;
import results.EventIdResult;
import results.LoginResult;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventIdServiceTest {
    private EventIdService eventIdService;

    @BeforeEach
    void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();

        eventIdService = new EventIdService();
    }

    @AfterAll
    static void cleanUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    void testRunEventIdPass() throws DataAccessException {
        // Make user to be loaded
        List<User> users = new ArrayList<>();
        String username = "brighamband";
        String password = "password";
        String personID = "personid1";
        User newUser = new User(username, password, "brighamband@gmail.com", "Brigham",
                "Andersen", "m", personID);
        users.add(newUser);

        // Make person to be loaded
        List<Person> persons = new ArrayList<>();
        Person newPerson = new Person(personID, username, newUser.getFirstName(), newUser.getLastName(),
                newUser.getGender(), null, null, null);
        persons.add(newPerson);

        // Make event to be loaded
        List<Event> events = new ArrayList<>();
        String eventID = "randomeventid";
        Event newEvent = new Event(eventID, username, personID,
                79.9833f, -84.0667f, "Canada", "Eureka", "baptism", 2010);
        events.add(newEvent);

        LoadRequest loadRequest = new LoadRequest(users, persons, events);
        LoadService loadService = new LoadService();
        loadService.load(loadRequest);

        // Log in with loaded user
        LoginRequest loginRequest = new LoginRequest(username, password);
        LoginService loginService = new LoginService();
        LoginResult loginResult = loginService.login(loginRequest);
        String authtoken = loginResult.getAuthtoken();

        // Use authtoken and event id to look up event
        Path goodUrlPath = Path.of("/event/" + eventID);
        EventIdResult eventIdResult = eventIdService.runEventId(authtoken, goodUrlPath);

        assertTrue(eventIdResult.isSuccess());
        assertNull(eventIdResult.getMessage());
        assertEquals(eventID, eventIdResult.getEventID());
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