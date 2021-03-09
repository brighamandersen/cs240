package services;

import daos.*;
import models.AuthToken;
import models.Person;
import models.User;
import requests.RegisterRequest;
import results.LoginResult;
import results.RegisterResult;

import java.sql.Connection;
import java.util.UUID;

/**
 * Implements the register functionality of the server's web API.
 */
public class RegisterService {
    /**
     * Creates a new user account, generates 4 generations of ancestor data for the new user, logs the user in, and returns an auth token.
     * @param r Register request data
     * @return Register response data
     */
    public RegisterResult register(RegisterRequest r) throws DataAccessException {
        Database db = new Database();

        try {
            db.openConnection();
            Connection conn = db.getConnection();
            PersonDao personDao = new PersonDao(conn);
            UserDao userDao = new UserDao(conn);
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);

            // Look up username, see if unique
            User existingUser = userDao.find(r.getUsername());

            if (existingUser == null) {
                String newPersonId = UUID.randomUUID().toString();
                Person person = new Person(newPersonId, r.getUsername(), r.getFirstName(), r.getLastName(), r.getGender(), null, null, null);
                User user = new User(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(), r.getLastName(), r.getGender(), newPersonId);
                String newToken = UUID.randomUUID().toString();
                AuthToken authToken = new AuthToken(newToken, user.getUsername());

                personDao.insert(person);
                userDao.insert(user);
                authTokenDao.insert(authToken);

                db.closeConnection(true);
                return new RegisterResult(authToken.getToken(), user.getUsername(), person.getPersonId());
            } else {
                db.closeConnection(false);
                return new RegisterResult("Username already taken by another user");
            }
        } catch (DataAccessException ex) {
            db.closeConnection(false);

            return new RegisterResult("Internal server error");
        }
    }
}
