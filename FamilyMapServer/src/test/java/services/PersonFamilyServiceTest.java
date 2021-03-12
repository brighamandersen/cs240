package services;

import daos.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import results.PersonFamilyResult;
import results.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class PersonFamilyServiceTest {
    private PersonFamilyService personFamilyService;
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

        personFamilyService = new PersonFamilyService();
    }

    @AfterAll
    static void cleanUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    void testRunPersonFamilyPass() throws DataAccessException {
        PersonFamilyResult personFamilyResult = personFamilyService.runPersonFamily(authtoken);

        assertTrue(personFamilyResult.isSuccess());
        assertNull(personFamilyResult.getMessage());
        assertEquals(31, personFamilyResult.getPersons().size());
    }

    /**
     * Makes request with invalid auth token
     */
    @Test
    void testRunPersonFamilyFail() throws DataAccessException {
        String BAD_AUTH_TOKEN = "badauthtoken";

        PersonFamilyResult personFamilyResult = personFamilyService.runPersonFamily(BAD_AUTH_TOKEN);

        assertFalse(personFamilyResult.isSuccess());
        assertEquals("Error: Invalid auth token", personFamilyResult.getMessage());
    }
}