package services;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import daos.*;
import models.AuthToken;
import models.Event;
import models.Person;
import results.EventFamilyResult;
import results.PersonFamilyResult;

import java.sql.Connection;
import java.util.List;

import static utils.HeaderUtils.checkAuth;

/**
 * Implements the event family functionality of the server's web API.
 */
public class EventFamilyService {
    /**
     * Returns ALL events for ALL family members of the current user.
     * @param exchange Object for request and response data
     * @return Event family response data
     */
    public EventFamilyResult runEventFamily(HttpExchange exchange) throws DataAccessException {
        Database db = new Database();

        if (!checkAuth(exchange)) {
            return new EventFamilyResult("Invalid auth token");
        }

        try {
            db.openConnection();
            Connection conn = db.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            EventDao eventDao = new EventDao(conn);

            // Determine current user based on auth token
            Headers reqHeaders = exchange.getRequestHeaders();
            String reqToken = reqHeaders.getFirst("Authorization");
            AuthToken authToken = authTokenDao.find(reqToken);
            String curUser = authToken.getAssociatedUsername();

            List<Event> events = eventDao.multiFindByUsername(curUser);

            db.closeConnection(true);

            return new EventFamilyResult(events);
        } catch (DataAccessException ex) {
            db.closeConnection(false);

            return new EventFamilyResult("Internal server error");
        }
    }
}
