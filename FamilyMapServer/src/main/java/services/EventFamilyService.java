package services;

import daos.*;
import models.AuthToken;
import models.Event;
import results.EventFamilyResult;

import java.sql.Connection;
import java.util.List;

import static utils.HeaderUtils.isExistingToken;

/**
 * Implements the event family functionality of the server's web API.
 */
public class EventFamilyService {
    /**
     * Returns ALL events for ALL family members of the current user.
     * @param reqToken Auth token
     * @return Event family response data
     */
    public EventFamilyResult runEventFamily(String reqToken) throws DataAccessException {
        Database db = new Database();

        if (!isExistingToken(reqToken)) {
            return new EventFamilyResult("Invalid auth token");
        }

        try {
            db.openConnection();
            Connection conn = db.getConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            EventDao eventDao = new EventDao(conn);

            // Determine current user based on auth token
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
