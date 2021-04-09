package edu.byu.cs240.familymapclient.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Event;
import models.Person;

/**
 * App data cached while app is running (after logging in, it will fetch all data needed from server at once).
 */
public class DataCache {
    private static DataCache instance;

    public static DataCache getInstance() {
        if (instance == null) {
            instance = new DataCache();
        }
        return instance;
    }

    public static void initialize() {
        instance = new DataCache();
    }

    /**
     * Used for logging out a user
     */
    public static void clear() {
        instance = null;
    }

    private Map<String, Person> persons;    // String key is personID
    private Map<String, Event> events;      // String key is eventID
    private Map<String, List<Event>> personEvents;  // String key is personID, stores chronological person events
    private Person user;

    private DataCache() {
        persons = new HashMap<>();
        events = new HashMap<>();
        personEvents = new HashMap<>();
        user = null;
    }

    public static Map<String, Person> getPersons() {
        return DataCache.getInstance().persons;
    }

    public static void setPersons(Map<String, Person> persons) {
        DataCache.getInstance().persons = persons;
    }

    public static void addPerson(String personID, Person person) {
        DataCache.getInstance().persons.put(personID, person);
    }

    public static Map<String, Event> getEvents() {
        return DataCache.getInstance().events;
    }

    public static void setEvents(Map<String, Event> events) {
        DataCache.getInstance().events = events;
    }

    public static void addEvent(String eventID, Event event) {
        DataCache.getInstance().events.put(eventID, event);
    }

    public static Map<String, List<Event>> getPersonEvents() {
        return DataCache.getInstance().personEvents;
    }

    public static void setPersonEvents(Map<String, List<Event>> personEvents) {
        DataCache.getInstance().personEvents = personEvents;
    }

    public static void addPersonEvent(String personID, Event event) {
        if (getPersonEvents().containsKey(personID)) {
            getPersonEvents().get(personID).add(event);
        } else {
            List<Event> events = new ArrayList<>();
            events.add(event);
            DataCache.getInstance().personEvents.put(personID, events);
        }
    }

    public static Person getUser() {
        return DataCache.getInstance().user;
    }

    public static void setUser(Person user) {
        DataCache.getInstance().user = user;
    }
}
