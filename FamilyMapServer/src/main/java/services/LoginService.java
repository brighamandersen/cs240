package services;

import daos.*;
import models.AuthToken;
import models.Person;
import models.User;
import requests.LoginRequest;
import results.LoginResult;
import results.Result;

import java.sql.Connection;
import java.util.UUID;

/**
 * Implements the login functionality of the server's web API.
 */
public class LoginService {
    /**
     * Logs in the user and returns an auth token.
     * @param r Login request data
     * @return Login response data
     */
    public LoginResult login(LoginRequest r) throws DataAccessException {
        Database db = new Database();

        // FIXME - Add functionality for error "Request property missing or has invalid value"

        try {
            db.openConnection();
            Connection conn = db.getConnection();
            UserDao userDao = new UserDao(conn);
            PersonDao personDao = new PersonDao(conn);
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);

            // Look up username to see if exists in database (return User or null)
            User user = userDao.find(r.getUsername());

            // If there is a User, then compare that user's password with the password passed in
            if (user != null) {
                if (r.getPassword().equals(user.getPassword())) {
                    // Get person object for user to extract person id
                    Person person = personDao.findByUsername(user.getUsername());

                    // Add new auth token for user
                    String newToken = UUID.randomUUID().toString();
                    AuthToken authToken = new AuthToken(newToken, user.getUsername());
                    authTokenDao.insert(authToken);

                    db.closeConnection(true);

                    return new LoginResult(authToken.getToken(), user.getUsername(), person.getPersonId());
                }
                db.closeConnection(false);

                return new LoginResult("Incorrect password for " + user.getUsername());
            } else {
                db.closeConnection(false);

                return new LoginResult("No user found with specified auth credentials.");
            }
        } catch (DataAccessException ex) {
            db.closeConnection(false);

            return new LoginResult("Internal server error");
        }
    }
}
