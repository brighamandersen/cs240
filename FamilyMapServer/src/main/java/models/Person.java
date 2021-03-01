package models;

/**
 * Holds family server person data when it's in RAM (when it's not in database).
 */
public class Person {
    /**
     * Unique identifier for person
     */
    private String personId;
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
    private String fatherId;
    /**
     * ID of person's mother (if one exists)
     */
    private String motherId;
    /**
     * ID of person's spouse (if one exists.)
     */
    private String spouseId;

    /**
     * Constructor for making person model objects.
     * @param personId Unique identifier for person
     * @param associatedUsername Username of person (if they are also a user)
     * @param firstName First name of person
     * @param lastName Last name of person
     * @param gender Gender of person (either 'f' or 'm')
     * @param fatherId ID of person's father (if one exists)
     * @param motherId ID of person's mother (if one exists)
     * @param spouseId ID of person's spouse (if one exists)
     */
    public Person(String personId, String associatedUsername, String firstName,
                  String lastName, String gender, String fatherId, String motherId, String spouseId) {
        this.personId = personId;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherId = fatherId;
        this.motherId = motherId;
        this.spouseId = spouseId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
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
            return oPerson.getPersonId().equals(getPersonId()) &&
                    oPerson.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oPerson.getFirstName().equals(getFirstName()) &&
                    oPerson.getLastName().equals(getLastName()) &&
                    oPerson.getGender().equals(getGender()) &&
                    oPerson.getFatherId().equals(getFatherId()) &&
                    oPerson.getMotherId().equals(getMotherId()) &&
                    oPerson.getSpouseId().equals(getSpouseId());
        } else {
            return false;
        }
    }
}
