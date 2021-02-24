package results;

import models.Event;

import java.util.ArrayList;

/**
 * Houses response data returned from event family API endpoint call.
 */
public class EventFamilyResult extends Result {
    /**
     * ALL events for ALL family members of the current user.
     */
    private ArrayList<Event> events;

    /**
     * Generates a success response body for event family result.
     * @param events ALL events for ALL family members of the current user.
     */
    public EventFamilyResult(ArrayList<Event> events) {
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

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
