package results;

/**
 * Parent class of the specific result classes which contains basic result structure for most calls.
 */
public class Result {
    /**
     * Response message to a request (if null, it won't be serialized).
     */
    private String message = null;
    /**
     * Signifies whether a request was successful or not.
     */
    private boolean success;

    /**
     * Generates a generic response body (use primarily for success responses).
     * @param message Message returned from request
     */
    public Result(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    /**
     * Generates a universal error response body.
     * @param message Error message
     */
    public Result(String message) {
        this.message = "Error: " + message;
        this.success = false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
