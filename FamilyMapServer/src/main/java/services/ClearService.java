package services;

import daos.DataAccessException;
import daos.Database;
import results.Result;

/**
 * Implements the clear functionality of the server's web API.
 */
public class ClearService {
    /**
     * Deletes ALL data from the database, including user accounts, auth tokens, and generated person and event data.
     * @return Clear response data
     */
    public Result clear() throws DataAccessException {
        Database db = new Database();

        try {
            db.openConnection();

            db.clearTables();

            db.closeConnection(true);

            return new Result("Clear succeeded.", true);
        } catch (DataAccessException e) {
            db.closeConnection(false);

            return new Result("Internal server error");
        }
    }
}
