package services;

import com.sun.net.httpserver.HttpExchange;
import results.EventFamilyResult;

/**
 * Implements the event family functionality of the server's web API.
 */
public class EventFamilyService {
    /**
     * Returns ALL events for ALL family members of the current user.
     * @param exchange Object for request and response data
     * @return Event family response data
     */
    public EventFamilyResult runEventFamily(HttpExchange exchange) {
        return null;
    }
}
