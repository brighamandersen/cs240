package results;

import models.Person;

import java.util.ArrayList;

/**
 * Houses response data returned from person family API endpoint call.
 */
public class PersonFamilyResult extends Result {

    /**
     * ALL family members of the current person (logged-in user).
     */
    private ArrayList<Person> persons;

    /**
     * Generates a success response body for event family result.
     * @param persons ALL family members of the current person (logged-in user).
     */
    public PersonFamilyResult(ArrayList<Person> persons) {
        super(null, true);
        this.persons = persons;
    }

    /**
     * Generates an error response body for person family result.
     * @param message Error message
     */
    public PersonFamilyResult(String message) {
        super(message);
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }
}
