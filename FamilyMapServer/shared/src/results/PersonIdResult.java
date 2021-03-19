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
     * ID of person's father
     */
    private String fatherID;
    /**
     * ID of person's mother
     */
    private String motherID;
    /**
     * ID of person's spouse
     */
    private String spouseID;

    /**
     * Generates a success response body for person ID result.
     * @param associatedUsername Username of User associated with person
     * @param personID Identifier of person
     * @param firstName First name of person
     * @param lastName Last name of person
     * @param gender Gender of person
     * @param fatherID ID of person's father
     * @param motherID ID of person's mother
     * @param spouseID ID of person's spouse
     */
    public PersonIdResult(String associatedUsername, String personID,
                          String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        super(null, true);
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
