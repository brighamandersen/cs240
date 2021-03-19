package models;

/**
 * Holds family server auth token data when it's in RAM (when it's not in database).
 */
public class AuthToken {
    /**
     * Unique access token
     */
    private String authtoken;
    /**
     * Username of User who owns token
     */
    private String associatedUsername;

    /**
     * Constructor for making auth token model objects.
     * @param authtoken Unique access token
     * @param associatedUsername Username of User who owns token
     */
    public AuthToken(String authtoken, String associatedUsername) {
        this.authtoken = authtoken;
        this.associatedUsername = associatedUsername;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
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
            return oAuthToken.getAuthtoken().equals(getAuthtoken()) &&
                    oAuthToken.getAssociatedUsername().equals(getAssociatedUsername());
        } else {
            return false;
        }
    }
}
