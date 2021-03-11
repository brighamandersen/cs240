package services;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import daos.AuthTokenDao;
import daos.DataAccessException;
import daos.Database;
import daos.PersonDao;
import models.AuthToken;
import models.Person;
import results.PersonFamilyResult;

import java.sql.Connection;
import java.util.List;

import static utils.HeaderUtils.isExistingToken;

/**
 * Implements the person family functionality of the server's web API.
 */
public class PersonFamilyService {
    /**
     * Returns ALL family members of the current user.
     * @param reqToken Auth token
     * @return Person family data
     */
    public PersonFamilyResult runPersonFamily(String reqToken) throws DataAccessException {
        Database db = new Database();

        if (!isExistingToken(reqToken)) {
            return new PersonFamilyResult("Invalid auth token");
        }

        try {
            db.openConnection();
            Connection conn = db.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            PersonDao personDao = new PersonDao(conn);

            // Determine current user based on auth token
            AuthToken authToken = authTokenDao.find(reqToken);
            String curUser = authToken.getAssociatedUsername();

            List<Person> persons = personDao.multiFindByUsername(curUser);

            db.closeConnection(true);

            return new PersonFamilyResult(persons);
        } catch (DataAccessException ex) {
            db.closeConnection(false);

            return new PersonFamilyResult("Internal server error");
        }
    }
}
