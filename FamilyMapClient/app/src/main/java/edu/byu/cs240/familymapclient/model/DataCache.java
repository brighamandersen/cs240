package edu.byu.cs240.familymapclient.model;

import java.util.HashMap;
import java.util.Map;

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

    private Map<String, Person> persons;    // String is id
    private Map<String, Event> events;

    // FIXME -Add these later as needed
        //    private Map<String, List<Event>> personEvents; // Chronologically pre-sorted
        //    private Settings settings;
        //    private List<String> eventTypes;
        //    private Map<String, MapColor> eventTypeColors;
        //    private Person user;
        //    private Set<String> paternalAncestors;
        //    private Set<String> maternalAncestors;
        //    private Map<String, List<Person>> personChildren;

    private DataCache() {
        persons = new HashMap<>();
        events = new HashMap<>();

        // FIXME - Add these later
        //        personEvents = new HashMap<>();
        //        settings = new Settings();
        //        eventTypes = new ArrayList<>();
        //        eventTypeColors = new HashMap<>();
        //        user = null;
        //        paternalAncestors = new HashSet<>();
        //        maternalAncestors = new HashSet<>();
        //        personChildren = new HashMap<>();
    }

    // Insert helper functions that help you store data from the server in the formats above
}

// How to use
// edu.byu.cs240.familymapclient.model.DataCache.getInstance().sampleMethod();
