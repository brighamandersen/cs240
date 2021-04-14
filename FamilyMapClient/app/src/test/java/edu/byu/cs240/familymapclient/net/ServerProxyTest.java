package edu.byu.cs240.familymapclient.net;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import requests.LoginRequest;
import requests.RegisterRequest;
import results.EventFamilyResult;
import results.LoginResult;
import results.PersonFamilyResult;
import results.RegisterResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerProxyTest {

    private ServerProxy serverProxy;
    private RegisterRequest registerRequest = new RegisterRequest("tester", "password",
            "tester@gmail.com", "Tester", "Johnson", "m");

    @BeforeEach
    public void setUp() {
        serverProxy = new ServerProxy("localhost", 8080);
        serverProxy.clear();
    }

    @AfterAll
    static void cleanUp() {
        ServerProxy serverProxy = new ServerProxy("localhost", 8080);
        serverProxy.clear();
    }

    @Test
    void testLoginPass() {
        serverProxy.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest(registerRequest.getUsername(), registerRequest.getPassword());
        LoginResult loginResult = serverProxy.login(loginRequest);

        assertTrue(loginResult.isSuccess());
        assertNull(loginResult.getMessage());
        assertEquals("tester", loginResult.getUsername());
    }

    /**
     * Try logging in with bad password
     */
    @Test
    void testLoginFail() {
        LoginRequest badLoginRequest = new LoginRequest("brighamband", "wrongpassword");
        LoginResult loginResult = serverProxy.login(badLoginRequest);

        assertFalse(loginResult.isSuccess());
        assertEquals("Error: Bad response code", loginResult.getMessage());
    }

    @Test
    void testRegisterPass() {
        RegisterResult registerResult = serverProxy.register(registerRequest);

        assertTrue(registerResult.isSuccess());
        assertNull(registerResult.getMessage());
        assertEquals("tester", registerResult.getUsername());
    }

    /**
     * Try registering the same user twice
     */
    @Test
    void testRegisterFail() {
        serverProxy.register(registerRequest);      // First registration
        RegisterResult registerResult = serverProxy.register(registerRequest);      // Second (duplicate) registration

        assertFalse(registerResult.isSuccess());
        assertEquals("Error: Bad response code", registerResult.getMessage());
    }

    @Test
    void testFetchAllPersonsPass() {
        RegisterResult registerResult = serverProxy.register(registerRequest);

        PersonFamilyResult personFamilyResult = serverProxy.fetchAllPersons(registerResult.getAuthtoken());

        assertTrue(personFamilyResult.isSuccess());
        assertNull(personFamilyResult.getMessage());
        assertEquals(31, personFamilyResult.getPersons().size());
    }

    /**
     * Try fetching data without being registered (bad authtoken)
     */
    @Test
    void testFetchAllPersonsFail() {
        PersonFamilyResult personFamilyResult = serverProxy.fetchAllPersons(null);

        assertFalse(personFamilyResult.isSuccess());
        assertEquals("Error: Bad response code", personFamilyResult.getMessage());
        assertNull(personFamilyResult.getPersons());
    }

    @Test
    void testFetchAllEventsPass() {
        RegisterResult registerResult = serverProxy.register(registerRequest);

        EventFamilyResult eventFamilyResult = serverProxy.fetchAllEvents(registerResult.getAuthtoken());

        assertTrue(eventFamilyResult.isSuccess());
        assertNull(eventFamilyResult.getMessage());
        assertEquals(91, eventFamilyResult.getEvents().size());
    }

    @Test
    void testFetchAllEventsFail() {
        EventFamilyResult eventFamilyResult = serverProxy.fetchAllEvents(null);

        assertFalse(eventFamilyResult.isSuccess());
        assertEquals("Error: Bad response code", eventFamilyResult.getMessage());
        assertNull(eventFamilyResult.getEvents());
    }
}
