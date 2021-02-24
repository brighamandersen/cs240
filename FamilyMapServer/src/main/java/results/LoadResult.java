package results;

/**
 * Houses response data returned from load API endpoint call.
 */
public class LoadResult extends Result {

    /**
     * Generates a success response body for load result.
     *
     * @param numUsers   Number of users added to database
     * @param numPersons Number of persons added to database
     * @param numEvents  Number of events added to database
     */
    public LoadResult(int numUsers, int numPersons, int numEvents) {
        super("Successfully added  " + numUsers + " users, "
                + numPersons + " persons, and " + numEvents + " events to the database.", true);
    }

    /**
     * Generates an error response body for load result.
     * @param message Error message
     */
    public LoadResult(String message) {
        super(message);
    }
}