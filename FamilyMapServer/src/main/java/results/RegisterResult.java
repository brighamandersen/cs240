package results;

/**
 * Houses response data returned from register API endpoint call.
 */
public class RegisterResult extends Result {
    /**
     * Auth token of new user
     */
    private String authtoken;
    /**
     * Username of new user
     */
    private String username;
    /**
     * Person ID of new user
     */
    private String personID;

    /**
     * Generates a success response body for register result.
     * @param authtoken Auth token of new user
     * @param username Username of new user
     * @param personID Person ID of new user
     */
    public RegisterResult(String authtoken, String username, String personID) {
        super(null, true);
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    /**
     * Generates an error response body for register result.
     * @param message Error message
     */
    public RegisterResult(String message) {
        super(message);
    }

    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
