package data;

import models.Event;
import models.Person;

import java.util.List;

public class PersonEventData {
    private List<Person> persons;
    private List<Event> events;

    public PersonEventData(List<Person> persons, List<Event> events) {
        this.persons = persons;
        this.events = events;
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

    public void addPerson(Person person) {
        persons.add(person);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public int getPersonsSize() {
        return persons.size();
    }

    public int getEventsSize() {
        return events.size();
    }
}
