package results;

/**
 * Parent class of the specific result classes which contains basic result structure for most calls.
 */
public class Result {
    /**
     * Signifies whether a request was successful or not.
     */
    boolean success;
    /**
     * Response message to a request (if null, it won't be serialized).
     */
    String message;

    public Result() {
        success = false;
        message = null;
    }
}
