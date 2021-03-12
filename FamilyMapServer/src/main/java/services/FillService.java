package services;

import daos.*;
import data.DataGenerator;
import data.PersonEventData;
import models.Event;
import models.Person;
import models.User;
import results.RegisterResult;
import results.Result;

import java.nio.file.Path;
import java.sql.Connection;
import java.util.UUID;

/**
 * Implements the fill functionality of the server's web API.
 */
public class FillService {
    /**
     * Populates the server's database with generated data for the specified username.
     * @param urlPath URL path
     * @return Fill response data
     */
    public Result fill(Path urlPath) throws DataAccessException {
        String usernameParam;
        int numGensParam = 4;

        if (urlPath.getNameCount() > 3 || urlPath.getNameCount() < 2) {
            return new Result("Invalid username or generations parameter");
        }

        usernameParam = String.valueOf(urlPath.getName(1));

        if (urlPath.getNameCount() == 3) {
            numGensParam = Integer.parseInt(String.valueOf(urlPath.getName(2)));

            if (numGensParam < 0) {
                return new Result("Invalid username or generations parameter");
            }
        }

        Database db = new Database();

        try {
            db.openConnection();
            Connection conn = db.getConnection();
            UserDao userDao = new UserDao(conn);
            PersonDao personDao = new PersonDao(conn);
            EventDao eventDao = new EventDao(conn);

            User user = userDao.find(usernameParam);

            // Check that username matches a registered user
            if (user != null) {
                // If persons or events exist associated with that username, delete them
                Person origPerson = personDao.findByUsername(usernameParam);

                personDao.deleteByUsername(usernameParam);
                eventDao.deleteByUsername(usernameParam);

                String fatherID = UUID.randomUUID().toString();
                String motherID = UUID.randomUUID().toString();
                Person recreatedPerson = new Person(origPerson.getPersonID(),origPerson.getAssociatedUsername(),
                        origPerson.getFirstName(), origPerson.getLastName(), origPerson.getGender(),
                        fatherID, motherID, null);

                DataGenerator dataGen = new DataGenerator();
                Event ownBirth = dataGen.generateOwnBirth(user.getUsername(), recreatedPerson.getPersonID());
                eventDao.insert(ownBirth);

                // Add generations of ancestor data for specified user
                PersonEventData personEventData = dataGen.generateParentData(numGensParam, fatherID,
                        motherID, user.getUsername(), ownBirth.getYear());

                db.closeConnection(true);

                // + 1 because we're including the individual's person and event data
                int personsAdded = personEventData.getPersonsSize() + 1;
                int eventsAdded = personEventData.getEventsSize() + 1;

                return new Result("Successfully added " + personsAdded + " persons and "
                        + eventsAdded + " events to the database.", true);
            } else {
                db.closeConnection(false);

                return new Result("Invalid username or generations parameter");
            }
        } catch (DataAccessException e) {
            db.closeConnection(false);

            return new RegisterResult("Internal server error");
        }
    }
}
