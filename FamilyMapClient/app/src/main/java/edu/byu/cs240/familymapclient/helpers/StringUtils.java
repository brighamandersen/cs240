package edu.byu.cs240.familymapclient.helpers;

import models.Event;
import models.Person;

public final class StringUtils {

    public static String stringifyFullEvent(Event event) {
        return event.getEventType().toUpperCase() + ": " + stringifyFullLocation(event) +
                " (" + event.getYear() + ")";
    }

    public static String stringifyFullLocation(Event event) {
        return event.getCity() + ", " + event.getCountry();
    }

    public static String stringifyFullName(Person person) {
        return person.getFirstName() + " " + person.getLastName();
    }

    public static String stringifyBarDetails(Event event, Person person) {
        String details = stringifyFullName(person) + "\n";
        details += stringifyFullEvent(event);
        return details;
    }

    public static String stringifyLifeEventDetails(Event event, Person person) {
        String details = stringifyFullEvent(event) + "\n";
        details += stringifyFullName(person);
        return details;
    }

    public static String wordifyGender(String gender) {
        if (gender.equals("m")) {
            return "Male";
        }
        return "Female";
    }
}
