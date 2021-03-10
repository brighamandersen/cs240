package requests;

import models.Event;
import models.Person;
import models.User;

import java.util.List;

/**
 * Houses request data passed to load API endpoint call.
 */
public class LoadRequest {
    /**
     * Users to be created
     */
    private List<User> users;
    /**
     * Persons to be created
     */
    private List<Person> persons;
    /**
     * Events to be created
     */
    private List<Event> events;

    /**
     * Constructor for making load request objects.
     * @param users Users to be created
     * @param persons Persons to be created
     * @param events Events to be created
     */
    public LoadRequest(List<User> users, List<Person> persons, List<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
