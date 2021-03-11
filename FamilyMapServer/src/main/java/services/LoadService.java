package services;

import daos.*;
import models.Event;
import models.Person;
import models.User;
import requests.LoadRequest;
import results.LoadResult;

import java.sql.Connection;

/**
 * Implements the load functionality of the server's web API.
 */
public class LoadService {
    /**
     * Clears all data from the database (just like the /clear API), and then loads the posted user, person, and event data into the database.
     * @param r Load request data
     * @return Load response data
     */
    public LoadResult load(LoadRequest r) throws DataAccessException {
        Database db = new Database();

        // FIXME - Add functionality for "Invalid request data (missing values, invalid values, etc.)"
        if (r.getUsers() == null || r.getPersons() == null || r.getEvents() == null) {
            return new LoadResult("Invalid request data (missing values, invalid values, etc.)");
        }

        try {
            db.openConnection();
            Connection conn = db.getConnection();
            UserDao userDao = new UserDao(conn);
            PersonDao personDao = new PersonDao(conn);
            EventDao eventDao = new EventDao(conn);

            db.clearTables();

            for (User user : r.getUsers()) {
                userDao.insert(user);
            }

            for (Person person : r.getPersons()) {
                personDao.insert(person);
            }

            for (Event event : r.getEvents()) {
                eventDao.insert(event);
            }

            db.closeConnection(true);

            return new LoadResult(r.getUsers().size(), r.getPersons().size(), r.getEvents().size());
        } catch (DataAccessException ex) {
            db.closeConnection(false);

            return new LoadResult("Internal server error");
        }
    }
}