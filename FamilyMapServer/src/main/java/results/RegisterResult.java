package results;

/**
 * Houses response data returned from register API endpoint call.
 */
public class RegisterResult extends Result {
    /**
     * Auth token of new user
     */
    private String authToken;
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
     * @param authToken Auth token of new user
     * @param username Username of new user
     * @param personID Person ID of new user
     */
    public RegisterResult(String authToken, String username, String personID) {
        super(null, true);
        this.authToken = authToken;
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
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
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
