package daos;

import models.AuthToken;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Interfaces with AuthToken database to provide specific operations.
 */
public class AuthTokenDAO {
    private final Connection conn;

    /**
     * Constructor used to initialize database connection
     * @param conn Connection to database
     */
    public AuthTokenDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Adds new authToken to the database.
     * @param authToken Auth token to add
     * @throws DataAccessException Exception if auth token couldn't be inserted.
     */
    public void insert(AuthToken authToken) throws DataAccessException {
    }

    /**
     * Finds authToken within the database.
     * @param token Auth token to find
     * @return AuthToken AuthToken which has the unique identifier.
     * @throws DataAccessException Exception if auth token couldn't be found.
     */
    public AuthToken find(String token) throws DataAccessException {
        return null;
    }

    /**
     * Clears all tokens from the database.
     * @throws DataAccessException Exception if auth token(s) couldn't be cleared.
     */
    public void clear() throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Event";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
