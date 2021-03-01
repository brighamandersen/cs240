package results;

/**
 * Houses response data returned from login API endpoint call.
 */
public class LoginResult extends Result {
    /**
     * New auth token of logged-in user
     */
    private String authToken;
    /**
     * Username of logged-in user
     */
    private String username;
    /**
     * ID of person associated with logged-in user
     */
    private String personId;

    /**
     * Generates a success response body for login result.
     * @param authToken New auth token of logged-in user
     * @param username Username of logged-in user
     * @param personId ID of person associated with logged-in user
     */
    public LoginResult(String authToken, String username, String personId) {
        super(null, true);
        this.authToken = authToken;
        this.username = username;
        this.personId = personId;
    }

    /**
     * Generates an error response body for login result.
     * @param message Error message
     */
    public LoginResult(String message) {
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

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
