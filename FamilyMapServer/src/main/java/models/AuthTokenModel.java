package models;

/**
 * Holds family server auth token data when it's in RAM (when it's not in database).
 */
public class AuthTokenModel {
    /**
     * Unique token which allows user access to program.
     */
    private String token;
    /**
     * Timestamp for when a token was created.
     */
    private String timestamp;   // FIXME?  Should this be String?
    /**
     * Username to which the token belongs.
     */
    private String associatedUsername;

    public AuthTokenModel(String token, String timestamp, String associatedUsername) {
        this.token = token;
        this.timestamp = timestamp;
        this.associatedUsername = associatedUsername;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }
}
