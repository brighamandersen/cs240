package services;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import daos.*;
import models.AuthToken;
import models.Event;
import results.EventIdResult;

import java.nio.file.Path;
import java.sql.Connection;

import static utils.HeaderUtils.isExistingToken;
import static utils.StringUtils.countSlashes;
import static utils.StringUtils.urlToParamStr;

/**
 * Implements the event ID functionality of the server's web API.
 */
public class EventIdService {
    /**
     * Returns the single Event object with the specified ID.
     * @param reqToken Auth token
     * @param urlPath URL path
     * @return Event ID response data
     */
    public EventIdResult runEventId(String reqToken, Path urlPath) throws DataAccessException {
        Database db = new Database();

        if (!isExistingToken(reqToken)) {
            return new EventIdResult("Invalid auth token");
        }

        if (urlPath.getNameCount() > 2) {
            return new EventIdResult("Invalid eventID parameter");
        }

        try {
            db.openConnection();
            Connection conn = db.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            EventDao eventDao = new EventDao(conn);

            String eventIdParam = String.valueOf(urlPath.getName(1));
            Event event = eventDao.findByEventId(eventIdParam);

            if (event == null) {
                db.closeConnection(false);

                return new EventIdResult("Invalid eventID parameter");
            }

            // Check if requested event belongs to user who sent request
            AuthToken authToken = authTokenDao.find(reqToken);

            if (!event.getAssociatedUsername().equals(authToken.getAssociatedUsername())) {
                db.closeConnection(false);

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
