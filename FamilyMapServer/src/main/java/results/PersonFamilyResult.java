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
    private List<Person> data;

    /**
     * Generates a success response body for event family result.
     * @param data ALL family members of the current person (logged-in user).
     */
    public PersonFamilyResult(List<Person> data) {
        super(null, true);
        this.data = data;
    }

    /**
     * Generates an error response body for person family result.
     * @param message Error message
     */
    public PersonFamilyResult(String message) {
        super(message);
    }

    public List<Person> getPersons() {
        return data;
    }

    public void setPersons(List<Person> data) {
        this.data = data;
    }
}
