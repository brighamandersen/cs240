package results;

import models.Event;

import java.util.List;

/**
 * Houses response data returned from event family API endpoint call.
 */
public class EventFamilyResult extends Result {
    /**
     * ALL events for ALL family members of the current user.
     */
    private List<Event> events;

    /**
     * Generates a success response body for event family result.
     * @param events ALL events for ALL family members of the current user.
     */
    public EventFamilyResult(List<Event> events) {
        super(null, true);
        this.events = events;
    }

    /**
     * Generates an error response body for event family result.
     * @param message Error message
     */
    public EventFamilyResult(String message) {
        super(message);
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
