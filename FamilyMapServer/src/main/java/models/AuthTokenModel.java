package models;

public class AuthTokenModel {
    private String token;
    private String timestamp;   // FIXME?  Should this be String?
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
