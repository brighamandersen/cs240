package results;

import models.Person;

import java.util.List;

/**
 * Houses response data returned from person family API endpoint call.
 */
public class PersonFamilyResult extends Result {

    /**
     * ALL family members of the current person (logged-in user).
     */
    private List<Person> persons;

    /**
     * Generates a success response body for event family result.
     * @param persons ALL family members of the current person (logged-in user).
     */
    public PersonFamilyResult(List<Person> persons) {
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

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
