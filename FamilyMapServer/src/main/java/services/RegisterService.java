package services;

import daos.*;
import data.DataGenerator;
import data.PersonEventData;
import models.AuthToken;
import models.Event;
import models.Person;
import models.User;
import requests.RegisterRequest;
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
        final int NUM_GENERATIONS = 4;

        // FIXME - Add functionality for error "Request property missing or has invalid value"

        try {
            db.openConnection();
            Connection conn = db.getConnection();
            PersonDao personDao = new PersonDao(conn);
            UserDao userDao = new UserDao(conn);
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            EventDao eventDao = new EventDao(conn);

            // Look up username, see if unique
            User existingUser = userDao.find(r.getUsername());

            if (existingUser == null) {
                String newPersonId = UUID.randomUUID().toString();

                User user = new User(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(),
                        r.getLastName(), r.getGender(), newPersonId);
                userDao.insert(user);

                // Add current person data
                String fatherID = UUID.randomUUID().toString();
                String motherID = UUID.randomUUID().toString();
                Person person = new Person(newPersonId, r.getUsername(), r.getFirstName(), r.getLastName(),
                        r.getGender(), fatherID, motherID, null);
                personDao.insert(person);

                String newToken = UUID.randomUUID().toString();
                AuthToken authtoken = new AuthToken(newToken, user.getUsername());
                authTokenDao.insert(authtoken);

                // Add personal event (birth) data
                DataGenerator dataGen = new DataGenerator();
                Event ownBirth = dataGen.generateOwnBirth(r.getUsername(), newPersonId);
                eventDao.insert(ownBirth);

                // Add 4 generations of ancestor data for new user
                PersonEventData personEventData = dataGen.generateParentData(NUM_GENERATIONS, fatherID,
                        motherID, user.getUsername(), ownBirth.getYear());
                for (Person ancestorPerson : personEventData.getPersons()) {
                    personDao.insert(ancestorPerson);
                }
                for (Event ancestorEvent : personEventData.getEvents()) {
                    eventDao.insert(ancestorEvent);
                }

                db.closeConnection(true);

                return new RegisterResult(authtoken.getAuthtoken(), user.getUsername(), person.getPersonID());
            } else {
                db.closeConnection(false);

                return new RegisterResult("Username already taken by another user");
            }
        } catch (DataAccessException e) {
            db.closeConnection(false);

            return new RegisterResult("Internal server error");
        }
    }
}
