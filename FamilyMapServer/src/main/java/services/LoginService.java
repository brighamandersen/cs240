package services;

import daos.DataAccessException;
import daos.Database;
import daos.PersonDao;
import daos.UserDao;
import models.Person;
import models.User;
import requests.LoginRequest;
import results.LoginResult;
import results.Result;

import java.sql.Connection;

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

        try {
            db.openConnection();
            Connection conn = db.getConnection();
            UserDao userDao = new UserDao(conn);
            PersonDao personDao = new PersonDao(conn);

            User user = userDao.find(r.getUsername());
            Person person = personDao.findByUsername(r.getUsername());
            String hardCodedToken = "hard-coded-token";

            db.closeConnection(true);

            return new LoginResult(hardCodedToken, r.getUsername(), person.getPersonId());
        } catch (DataAccessException ex) {
            db.closeConnection(false);

            return new LoginResult("Internal server error");
        }
    }
}
