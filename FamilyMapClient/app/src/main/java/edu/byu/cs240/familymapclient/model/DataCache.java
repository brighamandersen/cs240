package edu.byu.cs240.familymapclient.model;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

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
    private Map<String, Float> eventColors;
    private int extraMarkerIndex;
    private Person user;
    private boolean showSpouseLines;
    private boolean showFamilyTreeLines;
    private boolean showLifeStoryLines;

    // FIELDS TO ADD FOR FILTERING
//    private List<Person> males;
//    private List<Person> females;
//    private List<Person> fathersSide;
//    private List<Person> mothersSide;

    private DataCache() {
        persons = new HashMap<>();
        events = new HashMap<>();
        personEvents = new HashMap<>();
        eventColors = new HashMap<String, Float>() {{
            put("birth", BitmapDescriptorFactory.HUE_GREEN);
            put("marriage", BitmapDescriptorFactory.HUE_YELLOW);
            put("death", BitmapDescriptorFactory.HUE_RED);
        }};
        extraMarkerIndex = 0;
        user = null;
        showSpouseLines = true;
        showFamilyTreeLines = true;
        showLifeStoryLines = true;
    }

    public static Map<String, Person> getPersons() {
        return DataCache.getInstance().persons;
    }

    public static void addPerson(String personID, Person person) {
        DataCache.getInstance().persons.put(personID, person);
    }

    public static Map<String, Event> getEvents() {
        return DataCache.getInstance().events;
    }

    public static void addEvent(String eventID, Event event) {
        DataCache.getInstance().events.put(eventID, event);
    }

    public static Map<String, List<Event>> getPersonEvents() {
        return DataCache.getInstance().personEvents;
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

    float[] extraMarkerColors = new float[] {
            BitmapDescriptorFactory.HUE_BLUE,
            BitmapDescriptorFactory.HUE_MAGENTA,
            BitmapDescriptorFactory.HUE_ORANGE,
            BitmapDescriptorFactory.HUE_ROSE,
            BitmapDescriptorFactory.HUE_VIOLET
    };

    public static Map<String, Float> getEventColors() {
        return DataCache.getInstance().eventColors;
    }

    public static void addEventColor(String eventType) {
        int index = DataCache.getInstance().extraMarkerIndex;
        float newMarkerColor = DataCache.getInstance().extraMarkerColors[index];

        DataCache.getInstance().eventColors.put(eventType, newMarkerColor);

        // Loop back to beginning of extra marker array if fully looped
        DataCache.getInstance().extraMarkerIndex++;
        if (DataCache.getInstance().extraMarkerIndex == 5) {
            DataCache.getInstance().extraMarkerIndex = 0;
        }
    }

    public static Person getUser() {
        return DataCache.getInstance().user;
    }

    public static void setUser(Person user) {
        DataCache.getInstance().user = user;
    }

    public static boolean getShowSpouseLines() {
        return DataCache.getInstance().showSpouseLines;
    }

    public static void setShowSpouseLines(boolean b) {
        DataCache.getInstance().showSpouseLines = b;
    }

    public static boolean getShowFamilyTreeLines() {
        return DataCache.getInstance().showFamilyTreeLines;
    }

    public static void setShowFamilyTreeLines(boolean b) {
        DataCache.getInstance().showFamilyTreeLines = b;
    }

    public static boolean getShowLifeStoryLines() {
        return DataCache.getInstance().showLifeStoryLines;
    }

    public static void setShowLifeStoryLines(boolean b) {
        DataCache.getInstance().showLifeStoryLines = b;
    }
}
