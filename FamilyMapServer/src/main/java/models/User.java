package models;

/**
 * Holds family server user data when it's in RAM (when it's not in database).
 */
public class User {
    /**
     * Unique username of user
     */
    private String username;
    /**
     * Password of user
     */
    private String password;
    /**
     * Email address of user
     */
    private String email;
    /**
     * First name of user
     */
    private String firstName;
    /**
     * Last name of user
     */
    private String lastName;
    /**
     * Gender of user (either 'f' or 'm')
     */
    private String gender;
    /**
     * Person identifier of user
     */
    private String personID;

    /**
     * Constructor for making user model objects.
     * @param username Unique username of user
     * @param password Password of user
     * @param email Email address of user
     * @param firstName First name of user
     * @param lastName Last name of user
     * @param gender Gender of user (either 'f' or 'm')
     * @param personID Person identifier of user
     */
    public User(String username, String password, String email,
                String firstName, String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * Compares if two users are equal.
     * @param o User object
     * @return Boolean signifying whether they are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof User) {
            User oUser = (User) o;
            return oUser.getUsername().equals(getUsername()) &&
                    oUser.getPassword().equals(getPassword()) &&
                    oUser.getEmail().equals(getEmail()) &&
                    oUser.getFirstName().equals(getFirstName()) &&
                    oUser.getLastName().equals(getLastName()) &&
                    oUser.getGender().equals(getGender()) &&
                    oUser.getPersonID().equals(getPersonID());
        } else {
            return false;
        }
    }
}
