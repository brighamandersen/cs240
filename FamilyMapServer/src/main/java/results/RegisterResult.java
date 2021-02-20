package results;

/**
 * Houses response data returned from register API endpoint call.
 */
public class RegisterResult extends Result {
    private String authToken;
    private String username;
    private String personID;

    /**
     * Generates a success response body.
     * @param authToken
     * @param username
     * @param personID
     */
    public RegisterResult(String authToken, String username, String personID) {
        this.authToken = authToken;
        this.username = username;
        this.personID = personID;
        success = true;
    }

    /**
     * Generates an error response body.
     * @param errorMsg The resulting error from the request (ex: request property missing or has invalid value, username already taken by another user, internal server error).
     */
    public RegisterResult(String errorMsg) {
        message = "Error: " + errorMsg;
        success = false;
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
