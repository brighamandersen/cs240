package services;

import com.sun.net.httpserver.Headers;
import daos.AuthTokenDao;
import daos.DataAccessException;
import daos.Database;
import daos.PersonDao;
import models.AuthToken;
import models.Person;
import results.PersonIdResult;
import com.sun.net.httpserver.HttpExchange;

import java.nio.file.Path;
import java.sql.Connection;

import static utils.HeaderUtils.isExistingToken;

/**
 * Implements the person ID functionality of the server's web API.
 */
public class PersonIdService {
    /**
     * Returns the single Person object with the specified ID.
     * @param reqToken Auth token
     * @param urlPath URL path
     * @return Person ID response data
     */
    public PersonIdResult runPersonId(String reqToken, Path urlPath) throws DataAccessException {
        Database db = new Database();

        if (!isExistingToken(reqToken)) {
            return new PersonIdResult("Invalid auth token");
        }

        if (urlPath.getNameCount() > 2) {
            return new PersonIdResult("Invalid personID parameter");
        }

        try {
            db.openConnection();
            Connection conn = db.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            PersonDao personDao = new PersonDao(conn);

            String personIdParam = String.valueOf(urlPath.getName(1));
            Person person = personDao.findByPersonID(personIdParam);

            if (person == null) {
                db.closeConnection(false);

                return new PersonIdResult("Invalid personID parameter");
            }

            // Check if requested person belongs to user who sent request
            AuthToken authToken = authTokenDao.find(reqToken);

            if (!person.getAssociatedUsername().equals(authToken.getAssociatedUsername())) {
                db.closeConnection(false);

                return new PersonIdResult("Requested person does not belong to this user");
            }

            db.closeConnection(true);

            return new PersonIdResult(person.getAssociatedUsername(), person.getPersonId(),
                    person.getFirstName(), person.getLastName(), person.getGender(),
                    person.getFatherId(), person.getMotherId(), person.getSpouseId());
        } catch (DataAccessException ex) {
            db.closeConnection(false);

            return new PersonIdResult("Internal server error");
        }
    }
}
