package requests;

/**
 * Houses request data passed to register API endpoint call.
 */
public class RegisterRequest {
    /**
     * Username for registration
     */
    private String username;
    /**
     * Password for registration
     */
    private String password;
    /**
     * Email address for registration
     */
    private String email;
    /**
     * First name for registration
     */
    private String firstName;
    /**
     * Last name for registration
     */
    private String lastName;
    /**
     * Gender for registration
     */
    private String gender;

    /**
     * Constructor for making register request objects.
     * @param username Username for registration
     * @param password Password for registration
     * @param email Email address for registration
     * @param firstName First name for registration
     * @param lastName Last name for registration
     * @param gender Gender for registration
     */
    public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
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
}
