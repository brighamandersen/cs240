package services;

import daos.DataAccessException;
import models.Event;
import models.Person;
import models.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LoadRequest;
import results.LoadResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest {
    private LoadService loadService;

    @BeforeEach
    void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();

        loadService = new LoadService();
    }

    @AfterAll
    static void cleanUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    void testLoadPass() throws DataAccessException {
        List<User> users = new ArrayList<>();
        User user1 = new User("arich", "password", "arich@gmail.com", "Ashton",
                "Rich", "m", "richlet1");
        User user2 = new User("brich", "password", "brich@gmail.com", "Baylor",
                "Rich", "m", "richlet2");
        User user3 = new User("crich", "password", "crich@gmail.com", "Cole",
                "Rich", "m", "richlet3");
        users.add(user1);
        users.add(user2);
        users.add(user3);

        List<Person> persons = new ArrayList<>();
        Person person1 = new Person("richlet1", "arich", "Ashton", "Rich",
                "m", null, null, null);
        Person person2 = new Person("richlet2", "brich", "Baylor", "Rich",
                "m", null, null, null);
        persons.add(person1);
        persons.add(person2);

        List<Event> events = new ArrayList<>();
        Event event1 = new Event("event1", "arich", "richlet1", 79.9833f,
                -84.0667f, "Canada", "Eureka", "birth", 1998);
        events.add(event1);

        LoadRequest loadRequest = new LoadRequest(users, persons, events);
        LoadResult loadResult = loadService.load(loadRequest);

        assertTrue(loadResult.isSuccess());
        assertEquals("Successfully added 3 users, 2 persons, and 1 events to the database.",
                loadResult.getMessage());
    }

    /**
     * Pass in null values
     */
    @Test
    void testLoadFail() throws DataAccessException {
        LoadRequest badLoadRequest = new LoadRequest(null, null, null);
        LoadResult loadResult = loadService.load(badLoadRequest);

        assertFalse(loadResult.isSuccess());
        assertEquals("Error: Invalid request data (missing values, invalid values, etc.)",
                loadResult.getMessage());
    }
}