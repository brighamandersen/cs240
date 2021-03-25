package edu.byu.cs240.familymapclient.model;

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

    // DataCache Instance Functions
        // clear
        // startSync
        // endSync

    // Getters/setters/helpers for edu.byu.cs240.familymapclient.model objects
    public static Map<String, Person> getPersons() {
        return instance._getPersons();
    }

    public static void setPersons(Map<String, Person> persons) {
        instance._setPersons(persons);
    }

    public static void addPerson(String personID, Person person) {
        instance._addPerson(personID, person);
    }

    public static Map<String, Event> getEvents() {
        return instance._getEvents();
    }

    public static void setEvents(Map<String, Event> events) {
        instance._setEvents(events);
    }

    public static void addEvent(String eventID, Event event) {
        instance._addEvent(eventID, event);
    }

    public static Map<String, List<Event>> getPersonEvents() {
        return instance._getPersonEvents();
    }

    public static void setPersonEvents(Map<String, List<Event>> personEvents) {
        instance._setPersonEvents(personEvents);
    }

    public static Person getUser() {
        return instance._getUser();
    }

    public static void setUser(Person user) {
        instance._setUser(user);
    }

    private Map<String, Person> persons;    // String is id
    private Map<String, Event> events;
    private Map<String, List<Event>> personEvents;
    private Person user;

    private DataCache() {
        persons = new HashMap<>();
        events = new HashMap<>();
        personEvents = new HashMap<>();
        user = null;
    }

    private Map<String, Person> _getPersons() {
        return persons;
    }

    private void _setPersons(Map<String, Person> persons) {
        this.persons = persons;
    }

    private void _addPerson(String personID, Person person) {
        persons.put(personID, person);
    }

    private Map<String, Event> _getEvents() {
        return events;
    }

    private void _setEvents(Map<String, Event> events) {
        this.events = events;
    }

    private void _addEvent(String eventID, Event event) {
        events.put(eventID, event);
    }

    private Map<String, List<Event>> _getPersonEvents() {
        return personEvents;
    }

    private void _setPersonEvents(Map<String, List<Event>> personEvents) {
        this.personEvents = personEvents;
    }

    private Person _getUser() {
        return user;
    }

    private void _setUser(Person user) {
        this.user = user;
    }
}

// How to use
// edu.byu.cs240.familymapclient.model.DataCache.getInstance().sampleMethod();
