package models;

/**
 * Holds family server person data when it's in RAM (when it's not in database).
 */
public class PersonModel {
    /**
     * Unique identifier for person.
     */
    private String personID;
    /**
     * Username of person if they are also a user.
     */
    private String associatedUsername;
    /**
     * First name of person.
     */
    private String firstName;
    /**
     * Last name of person.
     */
    private String lastName;
    /**
     * Gender of person. (either 'f' or 'm')
     */
    private char gender;    // FIXME? Change to String if needed
    /**
     * ID of person's father if one exists.
     */
    private String fatherID;
    /**
     * ID of person's spouse if one exists.
     */
    private String spouseID;

    public PersonModel(String personID, String associatedUsername, String firstName,
                       String lastName, char gender, String fatherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
