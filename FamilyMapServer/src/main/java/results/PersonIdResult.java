package results;

/**
 * Houses response data returned from person ID API endpoint call.
 */
public class PersonIdResult extends Result {
    /**
     * Username of User associated with person
     */
    private String associatedUsername;
    /**
     * Identifier of person
     */
    private String personId;
    /**
     * First name of person
     */
    private String firstName;
    /**
     * Last name of person
     */
    private String lastName;
    /**
     * Gender of person
     */
    private String gender;
    /**
     * ID of person's father
     */
    private String fatherId;
    /**
     * ID of person's mother
     */
    private String motherId;
    /**
     * ID of person's spouse
     */
    private String spouseId;

    /**
     * Generates a success response body for person ID result.
     * @param associatedUsername Username of User associated with person
     * @param personId Identifier of person
     * @param firstName First name of person
     * @param lastName Last name of person
     * @param gender Gender of person
     * @param fatherId ID of person's father
     * @param motherId ID of person's mother
     * @param spouseId ID of person's spouse
     */
    public PersonIdResult(String associatedUsername, String personId,
                          String firstName, String lastName, String gender, String fatherId, String motherId, String spouseId) {
        super(null, true);
        this.associatedUsername = associatedUsername;
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherId = fatherId;
        this.motherId = motherId;
        this.spouseId = spouseId;
    }


    /**
     * Generates an error response body for person ID result.
     * @param message Error message
     */
    public PersonIdResult(String message) {
        super(message);
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public String getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(String spouseId) {
        this.spouseId = spouseId;
    }
}
