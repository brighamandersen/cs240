package results;

/**
 * Parent class of the specific result classes which contains basic result structure for most calls.
 */
public class Result {
    /**
     * Response message to a request (if null, it won't be serialized).
     */
    String message;
    /**
     * Signifies whether a request was successful or not.
     */
    boolean success;

    /**
     * Generates a generic response body (works for success or error).
     * @param message Message returned from request
     */
    public Result(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
