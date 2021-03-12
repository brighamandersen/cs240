package results;

/**
 * Houses response data returned from login API endpoint call.
 */
public class LoginResult extends Result {
    /**
     * New auth token of logged-in user
     */
    private String authtoken;
    /**
     * Username of logged-in user
     */
    private String username;
    /**
     * ID of person associated with logged-in user
     */
    private String personID;

    /**
     * Generates a success response body for login result.
     * @param authtoken New auth token of logged-in user
     * @param username Username of logged-in user
     * @param personID ID of person associated with logged-in user
     */
    public LoginResult(String authtoken, String username, String personID) {
        super(null, true);
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
    }

    /**
     * Generates an error response body for login result.
     * @param message Error message
     */
    public LoginResult(String message) {
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
        this.personID = LoginResult.this.personID;
    }
}
