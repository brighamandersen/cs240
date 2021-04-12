package edu.byu.cs240.familymapclient.helpers;

import models.Event;
import models.Person;

public final class Stringify {

    public static String stringifyEventDetails(Event event, Person person) {
        String details = stringifyFullName(person) + "\n";
        details += event.getEventType().toUpperCase() + ": " + stringifyFullLocation(event) + " (" + event.getYear() + ")";
        return details;
    }

    public static String stringifyFullLocation(Event event) {
        return event.getCity() + ", " + event.getCountry();
    }

    public static String stringifyFullName(Person person) {
        return person.getFirstName() + " " + person.getLastName();
    }

    public static String wordifyGender(String gender) {
        if (gender.equals("m")) {
            return "Male";
        }
        return "Female";
    }
}
