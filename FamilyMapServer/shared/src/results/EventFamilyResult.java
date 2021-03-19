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
    private List<Event> data;

    /**
     * Generates a success response body for event family result.
     * @param data ALL events for ALL family members of the current user.
     */
    public EventFamilyResult(List<Event> data) {
        super(null, true);
        this.data = data;
    }

    /**
     * Generates an error response body for event family result.
     * @param message Error message
     */
    public EventFamilyResult(String message) {
        super(message);
    }

    public List<Event> getEvents() {
        return data;
    }

    public void setEvents(List<Event> data) {
        this.data = data;
    }
}
