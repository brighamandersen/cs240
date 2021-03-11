package services;

import daos.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import results.PersonFamilyResult;
import results.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class PersonFamilyServiceTest {
    private PersonFamilyService personFamilyService;
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

        personFamilyService = new PersonFamilyService();
    }

    @Test
    void testRunPersonFamilyPass() throws DataAccessException {
        PersonFamilyResult personFamilyResult = personFamilyService.runPersonFamily(authToken);

        assertTrue(personFamilyResult.isSuccess());
        assertNull(personFamilyResult.getMessage());
        assertEquals(1, personFamilyResult.getPersons().size());    // FIXME, should it include current user??
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