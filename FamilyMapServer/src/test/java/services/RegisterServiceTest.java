package services;

import daos.DataAccessException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import results.RegisterResult;


import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {
    private RegisterRequest registerRequest = new RegisterRequest("brighamband", "password",
            "brighamband@gmail.com", "Brigham", "Andersen", "m");
    private RegisterService registerService;

    @BeforeEach
    void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();

        registerService = new RegisterService();
    }

    @AfterAll
    static void cleanUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    void testRegisterPass() throws DataAccessException {
        RegisterResult registerResult = registerService.register(registerRequest);

        assertTrue(registerResult.isSuccess());
        assertNull(registerResult.getMessage());
        assertEquals("brighamband", registerResult.getUsername());
    }

    /**
     * Tries making duplicate
     */
    @Test
    void testRegisterFail() throws DataAccessException {
        RegisterResult registerResult = registerService.register(registerRequest);
        registerResult = registerService.register(registerRequest);

        assertFalse(registerResult.isSuccess());
        assertEquals("Error: Username already taken by another user", registerResult.getMessage());
    }
}