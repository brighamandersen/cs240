package requests;

/**
 * Houses request data passed to login API endpoint call.
 */
public class LoginRequest {
    /**
     * Attempted login username
     */
    private String username;
    /**
     * Attempted login password
     */
    private String password;

    /**
     * Constructor for making login request objects.
     * @param username Attempted login username
     * @param password Attempted login password
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
