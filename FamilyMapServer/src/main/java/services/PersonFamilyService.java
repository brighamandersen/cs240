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
import results.PersonIdResult;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static utils.HeaderUtils.checkAuth;
import static utils.StringUtils.urlToParamStr;

/**
 * Implements the person family functionality of the server's web API.
 */
public class PersonFamilyService {
    /**
     * Returns ALL family members of the current user.
     * @param exchange Object for request and response data
     * @return Person family data
     */
    public PersonFamilyResult runPersonFamily(HttpExchange exchange) throws DataAccessException {
        Database db = new Database();

        if (!checkAuth(exchange)) {
            return new PersonFamilyResult("Invalid auth token");
        }

        try {
            db.openConnection();
            Connection conn = db.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            PersonDao personDao = new PersonDao(conn);

            // Determine current user based on auth token
            Headers reqHeaders = exchange.getRequestHeaders();
            String reqToken = reqHeaders.getFirst("Authorization");
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
