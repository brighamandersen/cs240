package services;

import daos.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import results.PersonIdResult;
import results.RegisterResult;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PersonIdServiceTest {
    private PersonIdService personIdService;
    private String authtoken;
    private String personID;

    @BeforeEach
    void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();

        RegisterRequest registerRequest = new RegisterRequest("brighamband", "password",
                "brighamband@gmail.com", "Brigham", "Andersen", "m");
        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = registerService.register(registerRequest);
        authtoken = registerResult.getAuthToken();
        personID = registerResult.getPersonID();

        personIdService = new PersonIdService();
    }

    @AfterAll
    static void cleanUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    void testRunPersonIdPass() throws DataAccessException {
        Path goodUrlPath = Path.of("/person/" + personID);
        PersonIdResult personIdResult = personIdService.runPersonId(authtoken, goodUrlPath);

        assertTrue(personIdResult.isSuccess());
        assertNull(personIdResult.getMessage());
        assertEquals(personID, personIdResult.getPersonID());
    }

    /**
     * Makes request with invalid auth token
     */
    @Test
    void testRunPersonIdFail() throws DataAccessException {
        String BAD_AUTH_TOKEN = "badauthtoken";
        String RANDOM_PERSON_ID = "randompersonid";
        Path badUrlPath = Path.of("/person/" + RANDOM_PERSON_ID);

        PersonIdResult personIdResult = personIdService.runPersonId(BAD_AUTH_TOKEN, badUrlPath);

        assertFalse(personIdResult.isSuccess());
        assertEquals("Error: Invalid auth token", personIdResult.getMessage());
    }
}