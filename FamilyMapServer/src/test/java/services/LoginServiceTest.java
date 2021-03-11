package services;

import daos.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LoginRequest;
import requests.RegisterRequest;
import results.LoginResult;
import results.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    private LoginService loginService;

    @BeforeEach
    void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();

        RegisterRequest registerRequest = new RegisterRequest("brighamband", "password",
                "brighamband@gmail.com", "Brigham", "Andersen", "m");
        RegisterService registerService = new RegisterService();
        registerService.register(registerRequest);

        loginService = new LoginService();
    }

    @Test
    void testLoginPass() throws DataAccessException {
        LoginRequest goodLoginRequest = new LoginRequest("brighamband", "password");
        LoginResult loginResult = loginService.login(goodLoginRequest);

        assertTrue(loginResult.isSuccess());
        assertNull(loginResult.getMessage());
        assertEquals("brighamband", loginResult.getUsername());
    }

    /**
     * Try logging in with bad password
     */
    @Test
    void testLoginFail() throws DataAccessException {
        LoginRequest badLoginRequest = new LoginRequest("brighamband", "wrongpassword");
        LoginResult loginResult = loginService.login(badLoginRequest);

        assertFalse(loginResult.isSuccess());
        assertEquals("Error: Incorrect password for brighamband", loginResult.getMessage());
    }
}