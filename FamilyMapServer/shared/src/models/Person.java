package models;

/**
 * Holds family server person data when it's in RAM (when it's not in database).
 */
public class Person {
    /**
     * Unique identifier for person
     */
    private String personID;
    /**
     * Username of person (if they are also a user)
     */
    private String associatedUsername;
    /**
     * First name of person
     */
    private String firstName;
    /**
     * Last name of person
     */
    private String lastName;
    /**
     * Gender of person (either 'f' or 'm')
     */
    private String gender;
    /**
     * ID of person's father (if one exists)
     */
    private String fatherID;
    /**
     * ID of person's mother (if one exists)
     */
    private String motherID;
    /**
     * ID of person's spouse (if one exists.)
     */
    private String spouseID;

    /**
     * Constructor for making person model objects.
     * @param personID Unique identifier for person
     * @param associatedUsername Username of person (if they are also a user)
     * @param firstName First name of person
     * @param lastName Last name of person
     * @param gender Gender of person (either 'f' or 'm')
     * @param fatherID ID of person's father (if one exists)
     * @param motherID ID of person's mother (if one exists)
     * @param spouseID ID of person's spouse (if one exists)
     */
    public Person(String personID, String associatedUsername, String firstName,
                  String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
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

    /**
     * Compares if two persons are equal.
     * @param o Person object
     * @return Boolean signifying whether they are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Person) {
            Person oPerson = (Person) o;
            return oPerson.getPersonID().equals(getPersonID()) &&
                    oPerson.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oPerson.getFirstName().equals(getFirstName()) &&
                    oPerson.getLastName().equals(getLastName()) &&
                    oPerson.getGender().equals(getGender()) &&
                    oPerson.getFatherID().equals(getFatherID()) &&
                    oPerson.getMotherID().equals(getMotherID()) &&
                    oPerson.getSpouseID().equals(getSpouseID());
        } else {
            return false;
        }
    }
}
