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
     * Identifier of person (ONLY HERE IS IT 'personID' and not 'personId')
     */
    private String personID;
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
     * ID of person's father (ONLY HERE IS IT 'fatherID' and not 'fatherId')
     */
    private String fatherID;
    /**
     * ID of person's mother (ONLY HERE IS IT 'motherID' and not 'motherId')
     */
    private String motherID;
    /**
     * ID of person's spouse (ONLY HERE IS IT 'spouseID' and not 'spouseId')
     */
    private String spouseID;

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
        this.personID = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherId;
        this.motherID = motherId;
        this.spouseID = spouseId;
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
        return personID;
    }

    public void setPersonId(String personId) {
        this.personID = personId;
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
        return fatherID;
    }

    public void setFatherId(String fatherId) {
        this.fatherID = fatherId;
    }

    public String getMotherId() {
        return motherID;
    }

    public void setMotherId(String motherId) {
        this.motherID = motherId;
    }

    public String getSpouseId() {
        return spouseID;
    }

    public void setSpouseId(String spouseId) {
        this.spouseID = spouseId;
    }
}
