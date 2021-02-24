package models;

import java.util.Objects;

/**
 * Holds family server auth token data when it's in RAM (when it's not in database).
 */
public class AuthToken {
    /**
     * Unique access token
     */
    private String token;
    /**
     * When token was created
     */
    private String timestamp;
    /**
     * Username of User who owns token
     */
    private String associatedUsername;

    /**
     * Constructor for making auth token model objects.
     * @param token Unique access token
     * @param timestamp When token was created
     * @param associatedUsername Username of User who owns token
     */
    public AuthToken(String token, String timestamp, String associatedUsername) {
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

    /**
     * Compares if two auth tokens are equal.
     * @param o Auth token object
     * @return Boolean signifying whether they are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof AuthToken) {
            AuthToken oAuthToken = (AuthToken) o;
            return oAuthToken.getToken().equals(getToken()) &&
                    oAuthToken.getTimestamp().equals(getTimestamp()) &&
                    oAuthToken.getAssociatedUsername().equals(getAssociatedUsername());
        } else {
            return false;
        }
    }
}
