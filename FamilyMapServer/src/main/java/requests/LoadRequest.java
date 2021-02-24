package requests;

import java.util.ArrayList;

/**
 * Houses request data passed to load API endpoint call.
 */
public class LoadRequest {
    /**
     * Users to be created
     */
    private ArrayList<String> users;
    /**
     * Persons to be created
     */
    private ArrayList<String> persons;
    /**
     * Events to be created
     */
    private ArrayList<String> events;

    /**
     * Constructor for making load request objects.
     * @param users Users to be created
     * @param persons Persons to be created
     * @param events Events to be created
     */
    public LoadRequest(ArrayList<String> users, ArrayList<String> persons, ArrayList<String> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public ArrayList<String> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<String> persons) {
        this.persons = persons;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<String> events) {
        this.events = events;
    }
}
