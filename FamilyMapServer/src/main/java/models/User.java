package models;

/**
 * Holds family server user data when it's in RAM (when it's not in database).
 */
public class User {
    /**
     * Unique username of user.
     */
    private String username;
    /**
     * Password of user used for security when logging in.
     */
    private String password;
    /**
     * Email address of user.
     */
    private String email;
    /**
     * First name of user.
     */
    private String firstName;
    /**
     * Last name of user.
     */
    private String lastName;
    /**
     * Gender of user. (either 'f' or 'm')
     */
    private char gender;    // FIXME? Change to String if needed
    /**
     * Person identifier of user.
     */
    private String personID;

    public User(String username, String password, String email,
                String firstName, String lastName, char gender, String personID) {
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

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
