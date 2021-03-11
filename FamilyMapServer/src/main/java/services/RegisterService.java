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
                String fatherId = UUID.randomUUID().toString();
                String motherId = UUID.randomUUID().toString();
                Person person = new Person(newPersonId, r.getUsername(), r.getFirstName(), r.getLastName(),
                        r.getGender(), fatherId, motherId, null);
                personDao.insert(person);

                String newToken = UUID.randomUUID().toString();
                AuthToken authToken = new AuthToken(newToken, user.getUsername());
                authTokenDao.insert(authToken);

                // Add personal event (birth) data
                DataGenerator dataGenerator = new DataGenerator();
                Event personalBirthEvent = dataGenerator.generatePersonalEventData(r.getUsername(), newPersonId);
                eventDao.insert(personalBirthEvent);

                // FIXME - Add 4 generations of ancestor data for new user
                PersonEventData personEventData = dataGenerator
                        .generateParentData(NUM_GENERATIONS, fatherId, motherId, user.getUsername(), personalBirthEvent.getYear());
                for (Person ancestorPerson : personEventData.getPersons()) {
                    personDao.insert(ancestorPerson);
                }
                for (Event ancestorEvent : personEventData.getEvents()) {
                    eventDao.insert(ancestorEvent);
                }

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
