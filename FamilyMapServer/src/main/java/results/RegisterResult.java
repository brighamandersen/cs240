package results;

/**
 * Houses response data returned from register API endpoint call.
 */
public class RegisterResult extends Result {
    /**
     * Auth token of new user  (ONLY HERE IS IT 'authtoken' and not 'authToken')
     */
    private String authtoken;
    /**
     * Username of new user
     */
    private String username;
    /**
     * Person ID of new user (ONLY HERE IS IT 'personID' and not 'personId')
     */
    private String personID;

    /**
     * Generates a success response body for register result.
     * @param authToken Auth token of new user
     * @param username Username of new user
     * @param personId Person ID of new user
     */
    public RegisterResult(String authToken, String username, String personId) {
        super(null, true);
        this.authtoken = authToken;
        this.username = username;
        this.personID = personId;
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

    public void setAuthToken(String authToken) {
        this.authtoken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonId() {
        return personID;
    }

    public void setPersonId(String personId) {
        this.personID = personId;
    }
}
