package services;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import daos.*;
import models.AuthToken;
import models.Event;
import models.Person;
import results.EventIdResult;
import results.PersonIdResult;

import java.sql.Connection;

import static utils.HeaderUtils.checkAuth;
import static utils.StringUtils.countSlashes;
import static utils.StringUtils.urlToParamStr;

/**
 * Implements the event ID functionality of the server's web API.
 */
public class EventIdService {
    /**
     * Returns the single Event object with the specified ID.
     * @param exchange Object for request and response data
     * @return Event ID response data
     */
    public EventIdResult runEventId(HttpExchange exchange) throws DataAccessException {
        Database db = new Database();

        if (!checkAuth(exchange)) {
            return new EventIdResult("Invalid auth token");
        }

        String urlPath = exchange.getRequestURI().toString();
        if (countSlashes(urlPath) > 2) {
            return new EventIdResult("Invalid eventID parameter");
        }

        try {
            db.openConnection();
            Connection conn = db.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            EventDao eventDao = new EventDao(conn);

            String eventIdParam = urlToParamStr(urlPath);
            Event event = eventDao.findByEventId(eventIdParam);

            if (event == null) {
                return new EventIdResult("Invalid eventID parameter");
            }

            // Check if requested event belongs to user who sent request
            Headers reqHeaders = exchange.getRequestHeaders();
            String reqToken = reqHeaders.getFirst("Authorization");
            AuthToken authToken = authTokenDao.find(reqToken);

            if (!event.getAssociatedUsername().equals(authToken.getAssociatedUsername())) {
                return new EventIdResult("Requested event does not belong to this user");
            }

            db.closeConnection(true);

            return new EventIdResult(event.getAssociatedUsername(), event.getEventId(), event.getPersonId(),
                    event.getLatitude(), event.getLongitude(), event.getCountry(), event.getCity(),
                    event.getEventType(), event.getYear());
        } catch (DataAccessException ex) {
            db.closeConnection(false);

            return new EventIdResult("Internal server error");
        }

    }
}
