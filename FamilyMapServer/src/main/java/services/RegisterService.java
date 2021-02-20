package services;

import requests.RegisterRequest;
import results.RegisterResult;

/**
 * Implements the register functionality of the server's web API.
 */
public class RegisterService {
    /**
     * Creates a new user account, generates 4 generations of ancestor data for the new user, logs the user in, and returns an auth token.
     * @param r Register request data
     * @return Register response data
     */
    public RegisterResult register(RegisterRequest r) {
        return null;
    }
}
